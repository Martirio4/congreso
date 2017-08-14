package com.nomad.audit5s.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nomad.audit5s.Fragments.FragmentSeleccionAerea;
import com.nomad.audit5s.Model.Area;
import com.nomad.audit5s.R;

import java.util.UUID;

public class ActivityLanding extends AppCompatActivity implements FragmentSeleccionAerea.Notificable {

    private ImageButton botonStart;
    private ImageButton botonIssue;
    private ImageButton botonaudits;
    private ImageButton botonSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        botonIssue=(ImageButton) findViewById(R.id.btn_issue);
        botonaudits=(ImageButton) findViewById(R.id.btn_search);
        botonSettings=(ImageButton) findViewById(R.id.btn_setting);
        botonStart=(ImageButton) findViewById(R.id.btn_start);

        botonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            cargarFragmentSeleccionArea();
            }
        });

        botonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        botonaudits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        botonIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void cargarFragmentSeleccionArea(){
        FragmentSeleccionAerea fragmentSeleccionAerea= new FragmentSeleccionAerea();
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_landing,fragmentSeleccionAerea);
        fragmentTransaction.commit();
    }

    @Override
    public void comenzarAuditoria(Area unArea) {
        String idAuditoría = "Audit_"+ UUID.randomUUID();
        Intent intent=new Intent(this, ActivityAuditoria.class);
        Bundle bundle= new Bundle();
        bundle.putString(ActivityAuditoria.AREA, unArea.getNombreArea());
        bundle.putString(ActivityAuditoria.IDAUDITORIA, idAuditoría);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
