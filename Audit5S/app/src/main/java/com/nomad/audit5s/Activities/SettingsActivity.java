package com.nomad.audit5s.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nomad.audit5s.Fragments.FragmentCargarArea;
import com.nomad.audit5s.R;

public class SettingsActivity extends AppCompatActivity implements FragmentCargarArea.Exitable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        cargarFragmentNuevaArea();

    }


    public void irALanding(){
        Intent unIntent = new Intent(this, ActivityLanding.class);
        startActivity(unIntent);
        this.finish();
    }

    public void cargarFragmentNuevaArea(){
        FragmentCargarArea cArgarAreaFragment= new FragmentCargarArea();
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_settings,cArgarAreaFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void cerrarFragment() {
        irALanding();
    }


}
