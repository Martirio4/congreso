package com.nomad.audit5s.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.auth.FirebaseAuth;
import com.nomad.audit5s.adapter.AdapterArea;
import com.nomad.audit5s.fragments.FragmentManageAreas;
import com.nomad.audit5s.fragments.FragmentSettings;
import com.nomad.audit5s.model.Area;
import com.nomad.audit5s.R;
import com.nomad.audit5s.model.Auditoria;
import com.nomad.audit5s.model.Foto;
import com.nomad.audit5s.model.SubItem;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;

public class SettingsActivity extends AppCompatActivity implements AdapterArea.Eliminable, FragmentManageAreas.Avisable{

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        layout=findViewById(R.id.contenedor_landing);
        cargarFragmentSettings();


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Typeface robotoR = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        TextView unText=toolbar.findViewById(R.id.textoToolbar);
        unText.setTypeface(robotoR);
        unText.setTextColor(getResources().getColor(R.color.tile5));

        unText.setText(getResources().getText(R.string.settings));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }


    }

    private void cargarFragmentSettings() {
        FragmentSettings fragmentManageAreas = new FragmentSettings();
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorSettings, fragmentManageAreas,"fragmentManageAreas");
        fragmentTransaction.commit();
    }


    public void irALanding(){
        Intent unIntent = new Intent(this, ActivityLanding.class);
        startActivity(unIntent);
        this.finish();
    }





    @Override
    public void EliminarArea(Area unArea) {

       CrearDialogoBorrarArea(unArea);

    }

    public void borrarDefinitivamente(final Area unArea){

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                String usuario= FirebaseAuth.getInstance().getCurrentUser().getEmail();

                RealmResults<Auditoria> result2 = realm.where(Auditoria.class)
                        .equalTo("usuario", usuario)
                        .findAll();

                for (Auditoria audit:result2
                        ) {
                    if (audit.getAreaAuditada().getIdArea().equals(unArea.getIdArea())) {
                        RealmResults<SubItem>Subitems=realm.where(SubItem.class)
                                .equalTo("auditoria",audit.getIdAuditoria())
                                .findAll();
                        Subitems.deleteAllFromRealm();

                        RealmResults<Foto>fotos=realm.where(Foto.class)
                                .equalTo("auditoria",audit.getIdAuditoria())
                                .findAll();
                        for (Foto foti:fotos
                             ) {
                            File file = new File(foti.getRutaFoto());
                            boolean deleted = file.delete();
                        }
                        fotos.deleteAllFromRealm();

                        audit.deleteFromRealm();
                    }
                }


                RealmResults<Area> lasAreas=realm.where(Area.class)
                        .equalTo("idArea",unArea.getIdArea())
                        .findAll();
                    for (Area elArea:lasAreas
                         ) {
                        File file = new File(elArea.getFotoArea().getRutaFoto());
                        boolean deleted = file.delete();
                    }
                lasAreas.deleteAllFromRealm();
            }

        });




        FragmentManager fragmentManager = (FragmentManager) this.getSupportFragmentManager();
        FragmentManageAreas fragmentManageAreas = (FragmentManageAreas) fragmentManager.findFragmentByTag("fragmentManageAreas");

        if (fragmentManageAreas != null && fragmentManageAreas.isVisible()) {
            fragmentManageAreas.updateAdapter();
            Snackbar.make(layout,getResources().getString(R.string.delteAreaOk),Snackbar.LENGTH_SHORT)
                    .show();

        }
    }
    
    public void CrearDialogoBorrarArea(final Area unArea){
        new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.deleteTituloDialog))
                .contentColor(ContextCompat.getColor(this, R.color.primary_text))
                .titleColor(ContextCompat.getColor(this, R.color.tile4))
                .backgroundColor(ContextCompat.getColor(this, R.color.tile1))
                .content(getResources().getString(R.string.elArea) + unArea.getNombreArea() +"\n"+ getResources().getString(R.string.deletePermanente)+"\n"+getResources().getString(R.string.deseaContinuar))
                .positiveText(getResources().getString(R.string.delete))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        borrarDefinitivamente(unArea);
                    }
                })
                .negativeText(getResources().getString(R.string.cancel))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();
        
    }

    @Override
    public void salirDeAca() {
        Intent intent=new Intent(this, ActivityLanding.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}
