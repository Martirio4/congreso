package com.nomad.audit5s.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.auth.FirebaseAuth;
import com.nomad.audit5s.fragments.FragmentSeleccionAerea;
import com.nomad.audit5s.model.Area;
import com.nomad.audit5s.R;
import com.nomad.audit5s.model.Foto;

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

    private Typeface roboto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Nammu.init(getApplicationContext());

        setContentView(R.layout.activity_landing);

        botonIssue = (ImageButton) findViewById(R.id.btn_issue);
        botonaudits = (ImageButton) findViewById(R.id.btn_search);
        botonSettings = (ImageButton) findViewById(R.id.btn_setting);
        botonStart = (ImageButton) findViewById(R.id.btn_start);

        texto1 = findViewById(R.id.primeraOpcion);
        texto2 = findViewById(R.id.segundaOpcion);
        texto3 = findViewById(R.id.terceraOpcion);
        texto31 = findViewById(R.id.firstTime);
        texto4 = findViewById(R.id.cuartaOpcion);

        lin1 = findViewById(R.id.lin1);
        lin2 = findViewById(R.id.lin2);
        lin3 = findViewById(R.id.lin3);
        lin4 = findViewById(R.id.line4);

        roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
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
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        texto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });


        botonaudits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityMyAudits.class);
                startActivity(intent);
            }
        });
        texto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityMyAudits.class);
                startActivity(intent);
            }
        });
        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityMyAudits.class);
                startActivity(intent);
            }
        });


        botonIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("risomartin@gmail.com") +
                        "?subject=" + Uri.encode(getResources().getString(R.string.quieroReportar)) +
                        "&body=" + Uri.encode(getResources().getString(R.string.textoIssue));
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, getResources().getString(R.string.enviarMail)));

            }
        });
        lin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("risomartin@gmail.com") +
                        "?subject=" + Uri.encode(getResources().getString(R.string.quieroReportar)) +
                        "&body=" + Uri.encode(getResources().getString(R.string.textoIssue));
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, getResources().getString(R.string.enviarMail)));
            }
        });
        texto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("risomartin@gmail.com") +
                        "?subject=" + Uri.encode(getResources().getString(R.string.quieroReportar)) +
                        "&body=" + Uri.encode(getResources().getString(R.string.textoIssue));
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, getResources().getString(R.string.enviarMail)));
            }
        });

        ejecutarTutorial();
    }

    public void cargarFragmentSeleccionArea() {
        FragmentSeleccionAerea fragmentSeleccionAerea = new FragmentSeleccionAerea();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedor_landing, fragmentSeleccionAerea, "SeleccionArea").addToBackStack("SeleccionArea");
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
        FragmentSeleccionAerea seleccionAreas = (FragmentSeleccionAerea) fragmentManager.findFragmentByTag("SeleccionArea");


        if (seleccionAreas != null && seleccionAreas.isVisible()) {
            fragmentManager.popBackStackImmediate();

        } else {
            finishAffinity();
            super.onBackPressed();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentManager fragmentManager1 = this.getSupportFragmentManager();
        FragmentSeleccionAerea fragmentSeleccionAerea = (FragmentSeleccionAerea) fragmentManager1.findFragmentByTag("SeleccionArea");

        if (fragmentSeleccionAerea != null && fragmentSeleccionAerea.isVisible()) {

            if (id == android.R.id.home) {
                onBackPressed();
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    public void lanzarTuto() {

        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.btn_setting), getResources().getString(R.string.tutorial_tit_setting), getResources().getString(R.string.tutorial_desc_setting))
                        .outerCircleColor(R.color.tutorial1)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.75f)            // Specify the alpha amount for the outer circle
                        //.targetCircleColor(R.color.white)   // Specify a color for the target circle
                        //.titleTextSize(20)                  // Specify the size (in sp) of the title text
                        //.titleTextColor(R.color.white)      // Specify the color of the title text
                        //.descriptionTextSize(10)            // Specify the size (in sp) of the description text
                       // .descriptionTextColor(R.color.red)  // Specify the color of the description text
                        //.textColor(R.color.blue)            // Specify a color for both the title and description text
                        .textTypeface(roboto)  // Specify a typeface for the text
                        //.dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(false)                   // Whether to tint the target view's color
                        //.transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        //.icon(Drawable)                     // Specify a custom drawable to draw as the target
                        //.targetRadius(60)                  // Specify the target radius (in dp)
                ,
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        botonSettings.performClick();
                    }
                });

    }

    public void ejecutarTutorial() {
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", false);
        if (!firstRun)//if running for first time
        {   
            crearDialogoBienvenida();

        } else {
            crearDialogoBienvenida();


        }

    }

    private void crearDialogoBienvenida() {

            new MaterialDialog.Builder(this)
                    .title(getResources().getString(R.string.bienvenido)+" "+ FirebaseAuth.getInstance().getCurrentUser().getEmail())
                    .buttonsGravity(GravityEnum.CENTER)
                    .contentColor(ContextCompat.getColor(this, R.color.primary_text))
                    .backgroundColor(ContextCompat.getColor(this, R.color.tile1))
                    .titleColor(ContextCompat.getColor(this, R.color.tile4))
                    .content(getResources().getString(R.string.verTutorial))
                    .positiveText(R.string.si)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                            lanzarTuto();
                        }
                    })
                    .negativeText(getResources().getString(R.string.no))
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {

                            //escribir las sharedPreferences para todos los fragments
                        }
                    })
                    .show();

    }

}