package com.nomad.audit5s.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nomad.audit5s.Adapter.AdapterArea;
import com.nomad.audit5s.Fragments.FragmentManageAreas;
import com.nomad.audit5s.Fragments.FragmentSeleccionAerea;
import com.nomad.audit5s.Model.Area;
import com.nomad.audit5s.R;

import pl.tajchert.nammu.Nammu;

public class ActivityLanding extends AppCompatActivity implements FragmentSeleccionAerea.Notificable {

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Nammu.init(getApplicationContext());

        setContentView(R.layout.activity_landing);

        botonIssue=(ImageButton) findViewById(R.id.btn_issue);
        botonaudits=(ImageButton) findViewById(R.id.btn_search);
        botonSettings=(ImageButton) findViewById(R.id.btn_setting);
        botonStart=(ImageButton) findViewById(R.id.btn_start);

        texto1=findViewById(R.id.primeraOpcion);
        texto2=findViewById(R.id.segundaOpcion);
        texto3=findViewById(R.id.terceraOpcion);
        texto31=findViewById(R.id.firstTime);
        texto4=findViewById(R.id.cuartaOpcion);

        lin1=findViewById(R.id.lin1);
        lin2=findViewById(R.id.lin2);
        lin3=findViewById(R.id.lin3);
        lin4=findViewById(R.id.line4);

        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        texto1.setTypeface(roboto);
        texto2.setTypeface(roboto);
        texto3.setTypeface(roboto);
        texto31.setTypeface(roboto);
        texto4.setTypeface(roboto);



        botonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            cargarFragmentSeleccionArea();
            }
        });
        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarFragmentSeleccionArea();
            }
        });
        texto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        texto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        lin3.setOnClickListener(new View.OnClickListener() {
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
        texto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), ActivityMyAudits.class);
                startActivity(intent);
            }
        });
        lin2.setOnClickListener(new View.OnClickListener() {
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
        lin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("risomartin@gmail.com") +
                        "?subject=" + Uri.encode("I want to report an issue") +
                        "&body=" + Uri.encode("Please, tell us if you had any problem using our App. Suggestions are also welcome!");
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));
            }
        });
        texto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        fragmentTransaction.add(R.id.contenedor_landing,fragmentSeleccionAerea,"SeleccionArea").addToBackStack("SeleccionArea");
        fragmentTransaction.commit();


    }

    @Override
    public void comenzarAuditoria(Area unArea) {
        Intent intent=new Intent(this, ActivityAuditoria.class);
        Bundle bundle= new Bundle();
        bundle.putString(ActivityAuditoria.IDAREA, unArea.getIdArea());
        intent.putExtras(bundle);
        startActivity(intent);
        FragmentManager fragmentManager=(FragmentManager) this.getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = (FragmentManager) this.getSupportFragmentManager();
        FragmentSeleccionAerea seleccionAreas = (FragmentSeleccionAerea) fragmentManager.findFragmentByTag("SeleccionArea");


        if (seleccionAreas != null && seleccionAreas.isVisible()) {
           fragmentManager.popBackStackImmediate();

        } else {
            super.onBackPressed();
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentManager fragmentManager1=this.getSupportFragmentManager();
        FragmentSeleccionAerea fragmentSeleccionAerea=(FragmentSeleccionAerea) fragmentManager1.findFragmentByTag("SeleccionArea");

        if (fragmentSeleccionAerea != null && fragmentSeleccionAerea.isVisible()) {

            if (id == android.R.id.home){
                onBackPressed();
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }
}
