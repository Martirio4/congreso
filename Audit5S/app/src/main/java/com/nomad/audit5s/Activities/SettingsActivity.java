package com.nomad.audit5s.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nomad.audit5s.Adapter.AdapterArea;
import com.nomad.audit5s.Fragments.FragmentCargarArea;
import com.nomad.audit5s.Fragments.FragmentManageAreas;
import com.nomad.audit5s.Fragments.FragmentSettings;
import com.nomad.audit5s.Model.Area;
import com.nomad.audit5s.R;

import io.realm.Realm;

public class SettingsActivity extends AppCompatActivity implements FragmentCargarArea.Exitable, AdapterArea.Eliminable, FragmentManageAreas.Avisable{

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        cargarFragmentSettings();
        layout=findViewById(R.id.contenedor_landing);

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
    public void cerrarFragment() {
        irALanding();
    }

    @Override
    public void EliminarArea(Area unArea) {

       CrearDialogoBorrarArea(unArea);

    }

    public void borrarDefinitivamente(Area unArea){
        Realm realm = Realm.getDefaultInstance();
        final Area mArea=realm.where(Area.class)
                .equalTo("idArea",unArea.getIdArea())
                .findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mArea.deleteFromRealm();
            }
        });

        FragmentManager fragmentManager = (FragmentManager) this.getSupportFragmentManager();
        FragmentManageAreas fragmentManageAreas = (FragmentManageAreas) fragmentManager.findFragmentByTag("fragmentManageAreas");

        if (fragmentManageAreas != null && fragmentManageAreas.isVisible()) {
            fragmentManageAreas.updateAdapter();
            Snackbar.make(layout,"Area was succesfully deleted",Snackbar.LENGTH_SHORT)
                    .show();

        } else {

        }
    }
    
    public void CrearDialogoBorrarArea(final Area unArea){
        new MaterialDialog.Builder(this)
                .title("Delete Selected Area")
                .contentColor(ContextCompat.getColor(this, R.color.primary_text))
                .titleColor(ContextCompat.getColor(this, R.color.tile4))
                .backgroundColor(ContextCompat.getColor(this, R.color.tile1))
                .content("The area: " + unArea.getNombreArea() +"\n"+ "will be permanently deleted."+"\n"+"Do you wisht to continue?")
                .positiveText("Delete")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        borrarDefinitivamente(unArea);
                    }
                })
                .negativeText("Cancel")
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
