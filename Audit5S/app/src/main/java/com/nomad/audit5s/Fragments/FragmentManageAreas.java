package com.nomad.audit5s.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.nomad.audit5s.Adapter.AdapterArea;
import com.nomad.audit5s.Model.Area;
import com.nomad.audit5s.Model.Foto;
import com.nomad.audit5s.R;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import id.zelory.compressor.Compressor;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentManageAreas extends Fragment {

    private RealmList<Area> listaAreas;
    private RecyclerView recyclerAreas;
    private AdapterArea adapterArea;
    private GridLayoutManager layoutManager;

    private File fotoOriginal;
    private File fotoComprimida;

    private Notificable notificable;
    private FloatingActionMenu fabMenuManage;
    private FloatingActionButton fabAgregarArea;
    private FloatingActionButton fabSalir;

    private Avisable unAvisable;


    public FragmentManageAreas() {
        // Required empty public constructor
    }

    public interface Avisable{

        public void salirDeAca();
    }

    public void updateAdapter() {
        Realm realm=Realm.getDefaultInstance();
        RealmResults<Area> result3 = realm.where(Area.class)
                .findAll();
        listaAreas=new RealmList<>();
        listaAreas.addAll(result3);
        adapterArea.setListaAreasOriginales(listaAreas);
        adapterArea.notifyDataSetChanged();
    }

    public interface Notificable{
        public void comenzarAuditoria(Area unArea);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manage_aerea, container, false);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Area> result2 = realm.where(Area.class)
                .findAll();
        listaAreas=new RealmList<>();
        listaAreas.addAll(result2);
        recyclerAreas= (RecyclerView)view.findViewById(R.id.recyclerArea);
        adapterArea= new AdapterArea();
        adapterArea.setContext(getContext());
        layoutManager= new GridLayoutManager(getContext(),2);
        recyclerAreas.setLayoutManager(layoutManager);
        adapterArea.setListaAreasOriginales(listaAreas);
        recyclerAreas.setAdapter(adapterArea);

        View.OnClickListener listenerArea = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer posicion = recyclerAreas.getChildAdapterPosition(v);
                RealmList<Area> listaAreas = adapterArea.getListaAreasOriginales();
                Area areaClickeada = listaAreas.get(posicion);
               // notificable.comenzarAuditoria(areaClickeada);
            }
        };
        adapterArea.setListener(listenerArea);

        fabMenuManage =(FloatingActionMenu) view.findViewById(R.id.agregarArea);
        fabMenuManage.setMenuButtonColorNormal(ContextCompat.getColor(getContext(), R.color.colorAccent));
        fabAgregarArea=new FloatingActionButton(getActivity());


        fabAgregarArea.setButtonSize(FloatingActionButton.SIZE_NORMAL);
        fabAgregarArea.setColorNormal(ContextCompat.getColor(getContext(), R.color.tile3));
        fabAgregarArea.setLabelText(getString(R.string.addNewArea));
        fabAgregarArea.setImageResource(R.drawable.ic_note_add_black_24dp);
        fabMenuManage.addMenuButton(fabAgregarArea);

        fabAgregarArea.setLabelColors(ContextCompat.getColor(getActivity(), R.color.tile3),
                ContextCompat.getColor(getActivity(), R.color.light_grey),
                ContextCompat.getColor(getActivity(), R.color.white_transparent));
        fabAgregarArea.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        fabAgregarArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenuManage.close(true);
                EasyImage.openChooserWithGallery(FragmentManageAreas.this, "Select image", 1);
            }
        });

        fabSalir = new FloatingActionButton(getActivity());
        fabSalir.setButtonSize(FloatingActionButton.SIZE_NORMAL);
        fabSalir.setColorNormal(ContextCompat.getColor(getContext(), R.color.tile3));
        fabSalir.setLabelText(getString(R.string.salir));
        fabSalir.setImageResource(R.drawable.ic_exit_to_app_black_24dp);
        fabMenuManage.addMenuButton(fabSalir);

        fabSalir.setLabelColors(ContextCompat.getColor(getActivity(), R.color.tile3),
                ContextCompat.getColor(getActivity(), R.color.light_grey),
                ContextCompat.getColor(getActivity(), R.color.white_transparent));
        fabSalir.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        fabSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenuManage.close(true);
                unAvisable.salirDeAca();

            }
        });



        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.unAvisable=(Avisable) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                if (type == 1) {
                    fotoOriginal = imageFile;
                    existeDirectorioImagenes();
                    try {
                        fotoComprimida = new Compressor(getContext())
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(fotoOriginal.getParent() + File.separator + "images")
                                .compressToFile(fotoOriginal);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Foto unaFoto = new Foto();
                    unaFoto.setRutaFoto(fotoComprimida.getAbsolutePath());
                    Boolean seBorro = imageFile.delete();
                    if (seBorro) {
                     //   Toast.makeText(getContext(), R.string.seEliminoFoto, Toast.LENGTH_SHORT).show();

                    } else {
                       // Toast.makeText(getContext(), R.string.noSeEliminoFoto, Toast.LENGTH_SHORT).show();
                    }
                    crearDialogoNombreArea(unaFoto);


                }

            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                super.onCanceled(source, type);
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getActivity());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }
    public void existeDirectorioImagenes() {
        Boolean sePudo = true;
        File dir = new File(fotoOriginal.getParent() + File.separator + "images");
        if (!dir.exists() || !dir.isDirectory()) {
            sePudo = dir.mkdirs();
        }
        if (sePudo) {

        } else {
            Toast.makeText(getContext(), "no se pudo crear el directorio", Toast.LENGTH_SHORT).show();
        }

    }

    public void crearDialogoNombreArea(final Foto unaFoto){

        new MaterialDialog.Builder(getContext())
                .title("New Area")
                .contentColor(ContextCompat.getColor(getContext(), R.color.primary_text))
                .backgroundColor(ContextCompat.getColor(getContext(), R.color.tile1))
                .titleColor(ContextCompat.getColor(getContext(), R.color.tile4))
                .content("Name for the new Area")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Area name","", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {

                        final Area unArea = new Area();
                        unArea.setNombreArea(input.toString());
                        unArea.setFotoArea(unaFoto);
                        unArea.setIdArea("area" + UUID.randomUUID());

                        //guardo nueva area en Realm
                        Realm realm = Realm.getDefaultInstance();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Area realmArea = realm.copyToRealm(unArea);
                            }
                        });
                        dialogoExito(unArea);


                    }
                }).show();

    }

    public void dialogoExito(Area unArea) {

        new MaterialDialog.Builder(getContext())
                .title("New area successfully created")
                .contentColor(ContextCompat.getColor(getContext(), R.color.primary_text))
                .titleColor(ContextCompat.getColor(getContext(), R.color.tile4))
                .backgroundColor(ContextCompat.getColor(getContext(), R.color.tile1))
                .content("The area: " + unArea.getNombreArea() +"\n"+ "has been succesfully added to the system")
                .positiveText("Go back")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                   updateAdapter();
                    }
                })
                .negativeText("Add new area")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        updateAdapter();
                        EasyImage.openChooserWithGallery(FragmentManageAreas.this, "Select image", 1);
                    }
                })
                .show();



    }


}