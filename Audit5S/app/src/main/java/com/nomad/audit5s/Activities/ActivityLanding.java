package com.nomad.audit5s.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.auth.FirebaseAuth;
import com.nomad.audit5s.fragments.FragmentLanding;
import com.nomad.audit5s.fragments.FragmentSeleccionAerea;
import com.nomad.audit5s.model.Area;
import com.nomad.audit5s.R;
import com.nomad.audit5s.model.Foto;

import pl.tajchert.nammu.Nammu;

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

        Nammu.init(getApplicationContext());
        lanzarLandingFragment();
        /*
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("seleccion");
        if (fragment!=null&&fragment.isVisible()){
            getSupportFragmentManager().popBackStackImmediate();
        }
        */
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

            super.onBackPressed();
            finishAffinity();
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
}