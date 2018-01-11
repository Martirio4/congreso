package com.nomad.audit5s.fragments;


import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.auth.FirebaseAuth;
import com.nomad.audit5s.activities.LoginActivity;
import com.nomad.audit5s.model.Area;
import com.nomad.audit5s.model.Auditoria;
import com.nomad.audit5s.model.Foto;
import com.nomad.audit5s.model.SubItem;
import com.nomad.audit5s.R;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;



/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSettings extends Fragment {

    private Button areas;
    private Button borrar;
    private Button tuto;


    private ImageView lin1;
    private ImageView lin2;
    private ImageView lin3;
    private ImageView lin4;


    public FragmentSettings() {
        // Required empty public constructor
    }

    private SharedPreferences config;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        config = getActivity().getSharedPreferences("prefs",0);

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_settings, container, false);
        lin1=view.findViewById(R.id.targetnum);
        lin2=view.findViewById(R.id.targetnum2);
        lin3=view.findViewById(R.id.targetnum3);
        lin4=view.findViewById(R.id.targetnum4);
        areas=view.findViewById(R.id.botonManageAreas);
        Button logout=view.findViewById(R.id.botonLogOut);
        borrar=view.findViewById(R.id.botonBorrarTodo);
        tuto=view.findViewById(R.id.botonTuto);
        Button salir=view.findViewById(R.id.botonVolver);
        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        areas.setTypeface(roboto);
        logout.setTypeface(roboto);
        borrar.setTypeface(roboto);
        salir.setTypeface(roboto);
        tuto.setTypeface(roboto);

        Boolean textoParaBotonTuto=config.getBoolean("estadoTuto",false);
        if (textoParaBotonTuto){
            tuto.setText(R.string.activarTuto);
        }
        else{
            tuto.setText(R.string.desactivarTuto);
        }

        areas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManageAreas fragmentManageAreas = new FragmentManageAreas();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedorSettings, fragmentManageAreas,"fragmentManageAreas");
                fragmentTransaction.commit();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(v.getContext())
                        .title(getResources().getString(R.string.signOut))
                        .contentColor(ContextCompat.getColor(v.getContext(), R.color.primary_text))
                        .titleColor(ContextCompat.getColor(v.getContext(), R.color.tile4))
                        .backgroundColor(ContextCompat.getColor(v.getContext(), R.color.tile1))
                        .content(getResources().getString(R.string.avisoSignout)+"\n"+getResources().getString(R.string.continuar))
                        .positiveText(getResources().getString(R.string.signOut))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getContext().getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finishAffinity();
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
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialDialog.Builder(v.getContext())
                        .title(getResources().getString(R.string.databaseDelete))
                        .contentColor(ContextCompat.getColor(v.getContext(), R.color.primary_text))
                        .titleColor(ContextCompat.getColor(v.getContext(), R.color.tile4))
                        .backgroundColor(ContextCompat.getColor(v.getContext(), R.color.tile1))
                        .content(getResources().getString(R.string.auditsWillBeDeleted)+"\n"+getResources().getString(R.string.continuar))
                        .positiveText(getResources().getString(R.string.delete))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            new BorrarTodo().execute();

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
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                getActivity().finish();
            }
        });

        tuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=config.edit();
                Boolean estadoTuto=config.getBoolean("estadoTuto",false);
                //SI ESTADOTUTO ES VERDADERO EL TUTO NO CORRE
                if (estadoTuto){
                    editor.putBoolean("estadoTuto",false);
                    editor.putBoolean("quiereVerTuto", true);
                    //SI SON FALSOS, EL TUTORIAL CORRE SI SON VERDADEROS EL TUTO NO CORRE
                    editor.putBoolean("primeraVezFragmentSetting", false);
                    editor.putBoolean("primeraVezFragmentSubitem", false);
                    editor.putBoolean("primeraVezFragmentRadar", false);
                    editor.putBoolean("primeraVezFragmentManage", false);
                    editor.putBoolean("primeraVezFragmentSeleccion", false);
                    editor.putBoolean("primeraVezFragmentLanding",false);
                    editor.commit();
                    tuto.setText(R.string.desactivarTuto);

                }
                else{
                    editor.putBoolean("estadoTuto",true);
                    editor.putBoolean("quiereVerTuto", false);
                    //SI SON FALSOS, EL TUTORIAL CORRE SI SON VERDADEROS EL TUTO NO CORRE
                    editor.putBoolean("primeraVezFragmentSetting", true);
                    editor.putBoolean("primeraVezFragmentSubitem", true);
                    editor.putBoolean("primeraVezFragmentRadar", true);
                    editor.putBoolean("primeraVezFragmentManage", true);
                    editor.putBoolean("primeraVezFragmentSeleccion", true);
                    editor.putBoolean("primeraVezFragmentLanding",true);

                    editor.commit();
                    tuto.setText(R.string.activarTuto);
                }
            }
        });



        boolean quiereVerTuto = config.getBoolean("quiereVerTuto",false);
        boolean primeraVezFragmentSetting=config.getBoolean("primeraVezFragmentSetting",false);

        //SI EL USUARIO ELIGIO VER TUTORIALES ME FIJO SI YA PASO POR ESTA PAGINA.
        if (quiereVerTuto) {
            if (!primeraVezFragmentSetting) {
                SharedPreferences.Editor editor = config.edit();
                editor.putBoolean("primeraVezFragmentSetting",true);
                editor.commit();

                seguirConTutorial();
            }
        }
        return view;
    }



    private void seguirConTutorial() {
        Typeface roboto = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
        new TapTargetSequence(getActivity())
                .targets(
                        TapTarget.forView(lin1, getResources().getString(R.string.tutorial_tit_area), getResources().getString(R.string.tutorial_desc_area))
                                .transparentTarget(false)
                                .outerCircleColor(R.color.tutorial2)      // Specify a color for the outer circle
                                .outerCircleAlpha(0.85f)            // Specify the alpha amount for the outer circle
                                .textTypeface(roboto)  // Specify a typeface for the text
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(false)
                                .targetRadius(80)
                                .id(1),                   // Whether to tint the target view's color
                        TapTarget.forView(lin2, getResources().getString(R.string.tutorial_tit_logout), getResources().getString(R.string.tutorial_desc_logout))
                                .transparentTarget(false)
                                .outerCircleColor(R.color.tutorial1)
                                .textColor(R.color.primary_text)
                                .outerCircleAlpha(0.95f)            // Specify the alpha amount for the outer circle
                                .textTypeface(roboto)  // Specify a typeface for the text
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(false)
                                .targetRadius(80)
                                .id(2),
                        TapTarget.forView(lin3, getResources().getString(R.string.tutorial_tit_tuto), getResources().getString(R.string.tutorial_desc_tuto))
                                .transparentTarget(false)
                                .outerCircleColor(R.color.tutorial2)      // Specify a color for the outer circle
                                .outerCircleAlpha(0.95f)            // Specify the alpha amount for the outer circle
                                .textTypeface(roboto)  // Specify a typeface for the text
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(false)
                                .targetRadius(80)
                                .id(4),
                        TapTarget.forView(lin4, getResources().getString(R.string.tutorial_tit_delete), getResources().getString(R.string.tutorial_desc_delete))
                                .transparentTarget(false)
                                .outerCircleColor(R.color.tutorial1)
                                .textColor(R.color.primary_text)
                                .textColor(R.color.blancoNomad)// Specify a color for the outer circle
                                .outerCircleAlpha(0.95f)            // Specify the alpha amount for the outer circle
                                .textTypeface(roboto)  // Specify a typeface for the text
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(false)
                                .targetRadius(80)
                                .id(3)
                )

                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {

                    }

                    @Override
                    public void onSequenceStep(TapTarget tapTarget, boolean b) {


                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Boo
                    }
                })
                .start();
    }

    public void borrarBaseDeDatos(){
        Realm realm = Realm.getDefaultInstance();


        realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {

            String usuario= FirebaseAuth.getInstance().getCurrentUser().getEmail();

            RealmResults<Area>lasAreas=realm.where(Area.class)
                    .equalTo("usuario",usuario)
                    .findAll();
            lasAreas.deleteAllFromRealm();

            RealmResults<Auditoria> result2 = realm.where(Auditoria.class)
                    .equalTo("usuario", usuario)
                    .findAll();

            for (Auditoria audit:result2
                 ) {
                RealmResults<SubItem>Subitems=realm.where(SubItem.class)
                        .equalTo("auditoria",audit.getIdAuditoria())
                        .findAll();
                Subitems.deleteAllFromRealm();

                RealmResults<Foto>fotos=realm.where(Foto.class)
                        .equalTo("auditoria",audit.getIdAuditoria())
                        .findAll();
                fotos.deleteAllFromRealm();
            }
            result2.deleteAllFromRealm();



                //borrar directorios
                File path = new File(getContext().getExternalFilesDir(null)+ File.separator + "nomad" + File.separator + "audit5s" +File.separator+FirebaseAuth.getInstance().getCurrentUser().getEmail());
               if (deleteDirectory(path)){
                   Snackbar.make(areas,getResources().getString(R.string.confirmaBorrarBaseDeDato), Snackbar.LENGTH_SHORT)
                           .show();
               }

            }

        });
    }

    private class BorrarTodo extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            borrarBaseDeDatos();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Snackbar.make(borrar, getResources().getString(R.string.confirmaBorrarBaseDeDato), Snackbar.LENGTH_SHORT)
                    .show();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }
    }
    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }

    @Override
    public void onResume() {
        super.onResume();
       /* Boolean estadoTuto=config.getBoolean("estadoTuto",false);
        if (estadoTuto){
            tuto.setText(R.string.activarTuto);
        }
        else{
            tuto.setText(R.string.desactivarTuto);

        }
        */
    }
}
