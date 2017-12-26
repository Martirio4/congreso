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
    private Button logout;
    private Button borrar;
    private Button salir;

    private ImageView lin1;

    public FragmentSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_settings, container, false);
        lin1=view.findViewById(R.id.targetnum);
        areas=(Button)view.findViewById(R.id.botonManageAreas);
        logout=(Button)view.findViewById(R.id.botonLogOut);
        borrar=(Button)view.findViewById(R.id.botonBorrarTodo);
        salir=(Button)view.findViewById(R.id.botonVolver);
        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
        areas.setTypeface(roboto);
        logout.setTypeface(roboto);
        borrar.setTypeface(roboto);
        salir.setTypeface(roboto);

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

        ejecutarTutorial();
        return view;
    }

    public void ejecutarTutorial() {
        SharedPreferences settings = getActivity().getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRunSetting", false);
        if (!firstRun)//if running for first time
        {
            seguirConTutorial();

        }

    }

    private void seguirConTutorial() {
        Typeface roboto = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

        TapTargetView.showFor(getActivity(),                 // `this` is an Activity

                TapTarget.forView(lin1, getResources().getString(R.string.tutorial_tit_area), getResources().getString(R.string.tutorial_desc_area))
                        .outerCircleColor(R.color.tutorial2)      // Specify a color for the outer circle
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
                        .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                //.icon(Drawable)                     // Specify a custom drawable to draw as the target
                       .targetRadius(70)                  // Specify the target radius (in dp)
                ,
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        areas.performClick();
                    }
                });
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


}
