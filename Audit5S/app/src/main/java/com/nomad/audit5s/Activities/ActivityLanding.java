package com.nomad.audit5s.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nomad.audit5s.Manifest;
import com.nomad.audit5s.adapter.AdapterAuditorias;
import com.nomad.audit5s.fragments.FragmentLanding;
import com.nomad.audit5s.fragments.FragmentSeleccionAerea;
import com.nomad.audit5s.model.Area;
import com.nomad.audit5s.R;
import com.nomad.audit5s.model.Foto;
import com.nomad.audit5s.services.ServiceLoco;
import com.nomad.audit5s.utils.FuncionesPublicas;

import java.io.File;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import pl.tajchert.nammu.Nammu;

import static com.nomad.audit5s.fragments.FragmentSettings.deleteDirectory;

public class ActivityLanding extends AppCompatActivity implements FragmentLanding.Landinable, FragmentSeleccionAerea.Notificable {

    private ImageButton botonStart;
    private ImageButton botonIssue;
    private ImageButton botonaudits;
    private ImageButton botonSettings;

    private TextView texto1;
    private TextView texto2;
    private TextView texto3;
    private TextView texto31;
    private TextView texto4;

    private LinearLayout lin1;
    private LinearLayout lin2;
    private LinearLayout lin3;
    private LinearLayout lin4;

    private Typeface roboto;

    private SharedPreferences config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        startService(new Intent(getBaseContext(), ServiceLoco.class));
        FrameLayout frame = findViewById(R.id.contenedor_landing_completo);

        Nammu.init(getApplicationContext());

        //borrar cache auditorias PDF
        if (Nammu.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
        Integer permisoParaEscribir = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (FuncionesPublicas.isExternalStorageWritable()) {
                File path = new File(getExternalFilesDir(null) + File.separator + "nomad" + File.separator + "audit5s" + File.separator + FirebaseAuth.getInstance().getCurrentUser().getEmail() + File.separator + "audits");
                Boolean deleteDirectorio = deleteDirectory(path);
            }
        }

        config = getSharedPreferences("prefs", 0);
        boolean firstRun = config.getBoolean("firstRun", false);
        if (!firstRun){
            crearAuditoriaEjemplo();
        }

        lanzarLandingFragment();

        /*
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("seleccion");
        if (fragment!=null&&fragment.isVisible()){
            getSupportFragmentManager().popBackStackImmediate();
        }
        */


    }

    private void crearAuditoriaEjemplo() {
        Realm realm = Realm.getDefaultInstance();
    }

    private void lanzarLandingFragment() {
        FragmentLanding fragmentLanding=new FragmentLanding();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_landing_completo,fragmentLanding,"landing");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void comenzarAuditoria(Area unArea) {
        Intent intent = new Intent(this, ActivityAuditoria.class);
        Bundle bundle = new Bundle();

        bundle.putString(ActivityAuditoria.IDAREA, unArea.getIdArea());
        intent.putExtras(bundle);
        startActivity(intent);

        FragmentManager fragmentManager = (FragmentManager) this.getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = (FragmentManager) this.getSupportFragmentManager();
        FragmentSeleccionAerea seleccionAreas = (FragmentSeleccionAerea) fragmentManager.findFragmentByTag("seleccion");


        if (seleccionAreas != null && seleccionAreas.isVisible()) {
            fragmentManager.popBackStack();
            lanzarLandingFragment();
        } else {
            new MaterialDialog.Builder(this)
                    .contentColor(ContextCompat.getColor(this, R.color.primary_text))
                    .titleColor(ContextCompat.getColor(this, R.color.tile4))
                    .title(R.string.quit)
                    .content(R.string.des_quit)
                    .positiveText(R.string.quit)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            FuncionesPublicas.sumarUsoDeApp(ActivityLanding.this);
                            sumarVecesAbiertoFirebase();
                            ActivityLanding.super.onBackPressed();
                            finishAffinity();
                        }
                    })
                    .negativeText(R.string.cancel)
                    .show();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentManager fragmentManager1 = this.getSupportFragmentManager();
        FragmentSeleccionAerea fragmentSeleccionAerea = (FragmentSeleccionAerea) fragmentManager1.findFragmentByTag("seleccion");

        if (fragmentSeleccionAerea != null && fragmentSeleccionAerea.isVisible()) {

            if (id == android.R.id.home) {
                onBackPressed();
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void irASelecccionAreas() {
        FragmentSeleccionAerea fragmentSeleccionAerea = new FragmentSeleccionAerea();
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_landing_completo,fragmentSeleccionAerea,"seleccion");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void sumarVecesAbiertoFirebase() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        if (user!=null) {

            SharedPreferences sharedPreferences=getSharedPreferences("prefs",0);
            final Integer vecesUsuario=sharedPreferences.getInt("cantidadUsos",0);

            Calendar cal = Calendar.getInstance();
            Date date=cal.getTime();

            String monthNumber  = (String) DateFormat.format("MM",   date); // 06
            String year         = (String) DateFormat.format("yyyy", date); // 2013

            final DatabaseReference reference = mDatabase.child("usuarios").child(user.getUid()).child("estadisticas").child("abrioApp").child(monthNumber+"-"+year);

            //---leer cantidad de auditorias---//
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    reference.setValue(vecesUsuario);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }



}