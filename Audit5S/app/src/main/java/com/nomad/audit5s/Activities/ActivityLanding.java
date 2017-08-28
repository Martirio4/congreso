package com.nomad.audit5s.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.nomad.audit5s.Adapter.AdapterArea;
import com.nomad.audit5s.Fragments.FragmentManageAreas;
import com.nomad.audit5s.Fragments.FragmentSeleccionAerea;
import com.nomad.audit5s.Model.Area;
import com.nomad.audit5s.R;

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
                Intent intent= new Intent(v.getContext(), ActivityMyAudits.class);
                startActivity(intent);
            }
        });

        botonIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("risomartin@gmail.com") +
                        "?subject=" + Uri.encode("I want to report an issue") +
                        "&body=" + Uri.encode("Please, tell us if you had any problem using our App. Suggestions are also welcome!");
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));

            }
        });
    }

    public void cargarFragmentSeleccionArea(){
        FragmentSeleccionAerea fragmentSeleccionAerea= new FragmentSeleccionAerea();
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedor_landing,fragmentSeleccionAerea,"SeleccionArea").addToBackStack("SeleccionArea").commit();

    }

    @Override
    public void comenzarAuditoria(Area unArea) {
        Intent intent=new Intent(this, ActivityAuditoria.class);
        Bundle bundle= new Bundle();
        bundle.putString(ActivityAuditoria.IDAREA, unArea.getIdArea());
        intent.putExtras(bundle);
        FragmentManager fragmentManager=(FragmentManager) this.getSupportFragmentManager();
        startActivity(intent);
        fragmentManager.popBackStack();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        FragmentActivity unaActivity = (FragmentActivity) this;
        FragmentManager fragmentManager = (FragmentManager) this.getSupportFragmentManager();
        FragmentSeleccionAerea seleccionAreas = (FragmentSeleccionAerea) fragmentManager.findFragmentByTag("SeleccionArea");


        if (seleccionAreas != null && seleccionAreas.isVisible()) {
           fragmentManager.popBackStackImmediate();

        } else {
            super.onBackPressed();
        }




    }

}
