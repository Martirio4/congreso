package com.nomad.audit5s.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.nomad.audit5s.Adapter.AdapterFotos;
import com.nomad.audit5s.Model.Foto;
import com.nomad.audit5s.Model.SubItem;
import com.nomad.audit5s.R;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import io.realm.RealmList;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSubitem extends Fragment {

    public static final String ENUNCIADO="ENUNCIADO";
    public static final String OPCION1="OPCION1";
    public static final String OPCION2="OPCION2";
    public static final String OPCION3="OPCION3";
    public static final String OPCION4="OPCION4";
    public static final String OPCION5="OPCION5";
    public static final String PERTENENCIA="PERTENENCIA";

    private File fotoOriginal;
    private File fotoComprimida;
    private RecyclerView recyclerFotos;
    private AdapterFotos adapterFotos;
    private LinearLayoutManager layoutManager;





    RealmList<Foto>listaFotos;

    private String enunciado;
    private String criterio1;
    private String criterio2;
    private String criterio3;
    private String criterio4;
    private String criterio5;
    private String pertenencia;

    private TextView textViewEnunciado;
    private RadioGroup rg1;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;
    private RadioButton textviewPertenencia;

    private Button verCriterio;

    private FloatingActionMenu fabMenu;
    private FloatingActionButton fabCamara;
    private FloatingActionButton fabGuardar;
    private FloatingActionButton fabSalir;


    public FragmentSubitem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_subitem, container, false);

        Bundle bundle=getArguments();
        if (bundle==null){
            Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();
        }
        else{
            criterio1=bundle.getString(OPCION1);
            criterio2=bundle.getString(OPCION2);
            criterio3=bundle.getString(OPCION3);
            criterio4=bundle.getString(OPCION4);
            criterio5=bundle.getString(OPCION5);

            enunciado=bundle.getString(ENUNCIADO);
            pertenencia=bundle.getString(PERTENENCIA);
        }
        rg1=(RadioGroup) view.findViewById(R.id.rg1);
        verCriterio=(Button)view.findViewById(R.id.btn_criterios);
        rb1 =(RadioButton) view.findViewById(R.id.item1);
        rb2 =(RadioButton) view.findViewById(R.id.item2);
        rb3 =(RadioButton) view.findViewById(R.id.item3);
        rb4 =(RadioButton) view.findViewById(R.id.item4);
        rb5 =(RadioButton) view.findViewById(R.id.item5);
        textViewEnunciado=(TextView) view.findViewById(R.id.textoEnunciado);

        rb1.setText("1");
        rb2.setText("2");
        rb3.setText("3");
        rb4.setText("4");
        rb5.setText("5");
        textViewEnunciado.setText(enunciado);


        verCriterio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(v.getContext())
                        .title(R.string.criteriaTitulo)
                        .contentColor(ContextCompat.getColor(v.getContext(), R.color.marfil))
                        .titleColor(ContextCompat.getColor(v.getContext(), R.color.colorAccent))
                        .backgroundColor(ContextCompat.getColor(v.getContext(), R.color.tile3))
                        .content("1- "+criterio1+"\n"+"\n"+"2- "+criterio2+"\n"+"\n"+"3- "+criterio3+"\n"+"\n"+"4- "+criterio5+"\n"+"\n"+"5- "+criterio5)
                        .positiveText("Dismiss")
                        .show();
            }
        });


//        RecyclerView FOTOS
        recyclerFotos= (RecyclerView)view.findViewById(R.id.recyclerFotos);
        layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerFotos.setLayoutManager(layoutManager);
        adapterFotos= new AdapterFotos();
        adapterFotos.setContext(getContext());
        listaFotos=new RealmList<>();
        adapterFotos.setListaFotosOriginales(listaFotos);
        recyclerFotos.setAdapter(adapterFotos);



        //agregar los fabs al menu
        fabMenu=(FloatingActionMenu)view.findViewById(R.id.fab_menu);

        fabCamara = new FloatingActionButton(getActivity());
        fabCamara.setButtonSize(FloatingActionButton.SIZE_MINI);
        fabCamara.setLabelText(getString(R.string.sacarFoto));
        fabCamara.setImageResource(R.drawable.ic_camera_alt_black_24dp);
        fabMenu.addMenuButton(fabCamara);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fabCamara.setLabelColors(ContextCompat.getColor(getActivity(), R.color.rojoOscuro),
                    ContextCompat.getColor(getActivity(), R.color.light_grey),
                    ContextCompat.getColor(getActivity(), R.color.white_transparent));
            fabCamara.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        }
        else {
            fabCamara.setLabelColors(getResources().getColor(R.color.rojoOscuro),
                    getResources().getColor(R.color.light_grey),
                    getResources().getColor(R.color.white_transparent));
            fabCamara.setLabelTextColor(getResources().getColor( R.color.black));
        }
        fabCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openCamera(FragmentSubitem.this, 1);
            }
        });


        return view;
    }


    public static FragmentSubitem CrearfragmentSubItem(SubItem unSubItem) {
        FragmentSubitem detalleFragment = new FragmentSubitem();
        Bundle unBundle = new Bundle();

        unBundle.putString(ENUNCIADO, unSubItem.getEnunciado());
        unBundle.putString(OPCION1, unSubItem.getPunto1());
        unBundle.putString(OPCION2, unSubItem.getPunto2());
        unBundle.putString(OPCION3, unSubItem.getPunto3());
        unBundle.putString(OPCION4, unSubItem.getPunto4());
        unBundle.putString(OPCION5, unSubItem.getPunto5());
        unBundle.putString(PERTENENCIA, unSubItem.getPertenencia());

        detalleFragment.setArguments(unBundle);
        return detalleFragment;
    }


    @Override
    public void onActivityResult( int  requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                if (type==1){
                    fotoOriginal=imageFile;
                    existeDirectorioImagenes();
                    try {
                        fotoComprimida = new Compressor(getContext())
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(fotoOriginal.getParent()+File.separator+"images")
                                .compressToFile(fotoOriginal);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Foto unaFoto=new Foto();
                    unaFoto.setRutaFoto(fotoComprimida.getAbsolutePath());
                    listaFotos.add(unaFoto);
                    adapterFotos.notifyDataSetChanged();
                    Boolean seBorro=imageFile.delete();
                    if (seBorro){
                        Toast.makeText(getContext(), "borrada con exito", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), "No se pudo borrar", Toast.LENGTH_SHORT).show();
                    }



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
    public void  existeDirectorioImagenes(){
        Boolean sePudo=true;
        File dir = new File( fotoOriginal.getParent()+File.separator+"images");
        if(!dir.exists() || !dir.isDirectory()) {
            sePudo=dir.mkdirs();
        }
        if (sePudo){

        }
        else{
            Toast.makeText(getContext(), "no se pudo crear el directorio", Toast.LENGTH_SHORT).show();
        }

    }

}
