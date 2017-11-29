package com.nomad.audit5s.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.nomad.audit5s.Activities.LoginActivity;
import com.nomad.audit5s.Model.Area;
import com.nomad.audit5s.Model.Auditoria;
import com.nomad.audit5s.Model.Foto;
import com.nomad.audit5s.Model.SubItem;
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

    public FragmentSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment_settings, container, false);

        areas=(Button)view.findViewById(R.id.botonManageAreas);
        logout=(Button)view.findViewById(R.id.botonLogOut);
        borrar=(Button)view.findViewById(R.id.botonBorrarTodo);
        salir=(Button)view.findViewById(R.id.botonVolver);

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
                        .title("Sign out and quit")
                        .contentColor(ContextCompat.getColor(v.getContext(), R.color.primary_text))
                        .titleColor(ContextCompat.getColor(v.getContext(), R.color.tile4))
                        .backgroundColor(ContextCompat.getColor(v.getContext(), R.color.tile1))
                        .content("You are about to log out your account and quit the application."+"\n"+"Continue")
                        .positiveText("Log out & Quit")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent= new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();

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
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialDialog.Builder(v.getContext())
                        .title("Delete Database")
                        .contentColor(ContextCompat.getColor(v.getContext(), R.color.primary_text))
                        .titleColor(ContextCompat.getColor(v.getContext(), R.color.tile4))
                        .backgroundColor(ContextCompat.getColor(v.getContext(), R.color.tile1))
                        .content("All your Audits will be permanently deleted"+"\n"+"Continue?")
                        .positiveText("Delete")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            new BorrarTodo().execute();

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
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                getActivity().finish();
            }
        });
        return view;
    }

    public void borrarBaseDeDatos(){
        Realm realm = Realm.getDefaultInstance();
                                    realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                //borrar base de datos
                RealmResults<Foto>fotis=realm.where(Foto.class)
                        .findAll();
                fotis.deleteAllFromRealm();

                RealmResults<SubItem>Subitems=realm.where(SubItem.class)
                        .findAll();
                Subitems.deleteAllFromRealm();


                RealmResults<Area>Areas=realm.where(Area.class)
                        .findAll();
                Areas.deleteAllFromRealm();

                RealmResults<Auditoria>audits=realm.where(Auditoria.class)
                        .findAll();
                audits.deleteAllFromRealm();

                //borrar directorios
                File path = new File(Environment.getExternalStorageDirectory() + File.separator + "nomad" + File.separator + "audit5s");
               if (deleteDirectory(path)){
                   Snackbar.make(areas,"All data was succesfully deleted", Snackbar.LENGTH_SHORT)
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
            Snackbar.make(borrar, "Audit Database has been deleted", Snackbar.LENGTH_SHORT)
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
