package com.nomad.audit5s.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
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
import com.nomad.audit5s.Activities.ActivityAuditoria;
import com.nomad.audit5s.Adapter.AdapterFotos;
import com.nomad.audit5s.Model.Area;
import com.nomad.audit5s.Model.Auditoria;
import com.nomad.audit5s.Model.Foto;
import com.nomad.audit5s.Model.SubItem;
import com.nomad.audit5s.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
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
    public static final String ID="ID";

    private File fotoOriginal;
    private File fotoComprimida;
    private RecyclerView recyclerFotos;
    private AdapterFotos adapterFotos;
    private LinearLayoutManager layoutManager;
    private Integer puntuacion;

    private Avisable avisable;

    private Auditoria unaAuditoria;
    private RealmList<SubItem> unaListaSubitems;




    RealmList<Foto>listaFotos;

    private String enunciado;
    private String criterio1;
    private String criterio2;
    private String criterio3;
    private String criterio4;
    private String criterio5;
    private String pertenencia;
    private String id;

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

    public interface Avisable{
        public void cerrarAuditoria();
        public void salirDeAca();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_subitem, container, false);

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
            id=bundle.getString(ID);
            enunciado=bundle.getString(ENUNCIADO);
            pertenencia=ActivityAuditoria.idAuditoria+id;
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


//        HANDLE RADIOGROUP

        //In Activity

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                puntuacion =rg1.indexOfChild(view.findViewById(rg1.getCheckedRadioButtonId()))+1;

                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        SubItem sub = realm.where(SubItem.class)
                                .equalTo("pertenencia",pertenencia)
                                .findFirst();
                        sub.setPuntuacion1(puntuacion);
                    }
                });
            }
        });


        verCriterio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(v.getContext())
                        .title(R.string.criteriaTitulo)
                        .contentColor(ContextCompat.getColor(v.getContext(), R.color.primary_text))
                        .titleColor(ContextCompat.getColor(v.getContext(), R.color.tile4))
                        .backgroundColor(ContextCompat.getColor(v.getContext(), R.color.tile1))
                        .content("1- "+criterio1+"\n"+"\n"+"2- "+criterio2+"\n"+"\n"+"3- "+criterio3+"\n"+"\n"+"4- "+criterio5+"\n"+"\n"+"5- "+criterio5)
                        .positiveText("Dismiss")
                        .show();
            }
        });




        //traigo la lista de fotos de la base


//        RecyclerView FOTOS
        recyclerFotos= (RecyclerView)view.findViewById(R.id.recyclerFotos);
        layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerFotos.setLayoutManager(layoutManager);
        adapterFotos= new AdapterFotos();
        adapterFotos.setContext(getContext());
        listaFotos=cargarFotos();
        adapterFotos.setListaFotosOriginales(listaFotos);
        recyclerFotos.setAdapter(adapterFotos);

        View.OnClickListener listenerComentario=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer posicion=recyclerFotos.getChildAdapterPosition(v);
                RealmList<Foto> unaLista=adapterFotos.getListaFotosOriginales();
                Foto unaFoto=unaLista.get(posicion);
                crearDialogoParaModificarComentario(unaFoto);
                adapterFotos.notifyDataSetChanged();

            }
        };
        adapterFotos.setListener(listenerComentario);
        adapterFotos.notifyDataSetChanged();


        //agregar los fabs al menu
        fabMenu=(FloatingActionMenu)view.findViewById(R.id.fab_menu);
        fabMenu.setMenuButtonColorNormal(ContextCompat.getColor(getContext(),R.color.colorAccent));



        fabCamara = new FloatingActionButton(getActivity());
        fabCamara.setColorNormal(ContextCompat.getColor(getContext(), R.color.tile3));
        fabCamara.setButtonSize(FloatingActionButton.SIZE_MINI);
        fabCamara.setLabelText(getString(R.string.sacarFoto));
        fabCamara.setImageResource(R.drawable.ic_camera_alt_black_24dp);
        fabMenu.addMenuButton(fabCamara);

            fabCamara.setLabelColors(ContextCompat.getColor(getActivity(), R.color.tile3),
                    ContextCompat.getColor(getActivity(), R.color.light_grey),
                    ContextCompat.getColor(getActivity(), R.color.white_transparent));
            fabCamara.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        fabCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                EasyImage.openCamera(FragmentSubitem.this, 1);
            }
        });


        fabGuardar = new FloatingActionButton(getActivity());
        fabGuardar.setButtonSize(FloatingActionButton.SIZE_MINI);
        fabGuardar.setColorNormal(ContextCompat.getColor(getContext(), R.color.tile3));
        fabGuardar.setLabelText(getString(R.string.guardarAuditoria));
        fabGuardar.setImageResource(R.drawable.ic_save_black_24dp);
        fabMenu.addMenuButton(fabGuardar);

            fabGuardar.setLabelColors(ContextCompat.getColor(getActivity(), R.color.tile3),
                    ContextCompat.getColor(getActivity(), R.color.light_grey),
                    ContextCompat.getColor(getActivity(), R.color.white_transparent));
            fabGuardar.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
               if(completoTodosLosPuntos()){
                avisable.cerrarAuditoria();
               }

            }
        });

        fabSalir = new FloatingActionButton(getActivity());
        fabSalir.setButtonSize(FloatingActionButton.SIZE_MINI);
        fabSalir.setColorNormal(ContextCompat.getColor(getContext(), R.color.tile3));
        fabSalir.setLabelText(getString(R.string.salir));
        fabSalir.setImageResource(R.drawable.ic_exit_to_app_black_24dp);
        fabMenu.addMenuButton(fabSalir);

            fabSalir.setLabelColors(ContextCompat.getColor(getActivity(), R.color.tile3),
                    ContextCompat.getColor(getActivity(), R.color.light_grey),
                    ContextCompat.getColor(getActivity(), R.color.white_transparent));
            fabSalir.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));

        fabSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                avisable.salirDeAca();

            }
        });


        return view;
    }

    private Boolean completoTodosLosPuntos() {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<SubItem> result2 = realm.where(SubItem.class)
                .equalTo("auditoria", ActivityAuditoria.idAuditoria)
                .findAll();

        List<String> unaLista=new ArrayList<>();

        for (SubItem unSub:result2
             ) {
            if (unSub.getPuntuacion1()==null){
                unaLista.add(unSub.getId());
            }
        };
        if (unaLista.size()>0){
            Toast.makeText(getContext(), "You must complete items: "+unaLista.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
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
        unBundle.putString(ID, unSubItem.getId());
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


                    Realm realm = Realm.getDefaultInstance();

                    //In Activity
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            SubItem elsub = realm.where(SubItem.class)
                                    .equalTo("pertenencia",pertenencia)
                                    .findFirst();
                            if(elsub != null) {
                                updateFotoSub(elsub, realm);
                            }
                        }
                    });

                    adapterFotos.notifyDataSetChanged();
                    Boolean seBorro=imageFile.delete();
                    if (seBorro){
//                        Toast.makeText(getContext(), "borrada con exito", Toast.LENGTH_SHORT).show();
                    }
                    else{
//                        Toast.makeText(getContext(), "No se pudo borrar", Toast.LENGTH_SHORT).show();
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

    private void updateFotoSub(SubItem elsub, Realm realm) {

        final Foto unaFoto=new Foto();
        unaFoto.setRutaFoto(fotoComprimida.getAbsolutePath());
        unaFoto.setAuditoria(ActivityAuditoria.idAuditoria);
        unaFoto.setSubItem(id);
        crearDialogoComentarioParaFoto(unaFoto);
        Foto fotoSubidaARealm = realm.copyToRealmOrUpdate(unaFoto);
        elsub.getListaFotos().add(fotoSubidaARealm);
        listaFotos.add(unaFoto);


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

    public RealmList<Foto> cargarFotos(){
        RealmList<Foto>unaLista= new RealmList<>();

        Realm realm = Realm.getDefaultInstance();
        SubItem subIt=realm.where(SubItem.class)
                .equalTo("pertenencia", pertenencia)
                .findFirst();

        if (subIt.getListaFotos()==null||subIt.getListaFotos().size()<1){

            return new RealmList<>();
        }
        else{

            return subIt.getListaFotos();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.avisable = (Avisable)context;
    }



        //helper method
    public void update(Auditoria unaAuditoriaBuscada, Realm realm) {
        //do update stuff

        SubItem subItemAgregar= new SubItem();
        subItemAgregar.setId(id);
        subItemAgregar.setEnunciado(enunciado);
        subItemAgregar.setPertenencia(pertenencia);
        subItemAgregar.setAuditoria(ActivityAuditoria.idAuditoria);
        subItemAgregar.setPunto1(criterio1);
        subItemAgregar.setPunto2(criterio2);
        subItemAgregar.setPunto3(criterio3);
        subItemAgregar.setPunto4(criterio4);
        subItemAgregar.setPunto5(criterio5);
        subItemAgregar.setPuntuacion1(puntuacion);

        SubItem elSubitem = realm.copyToRealmOrUpdate(subItemAgregar);


    }

    public void crearDialogoComentarioParaFoto(final Foto unaFoto){

                new MaterialDialog.Builder(getContext())
                        .title("Add a comment")
                        .contentColor(ContextCompat.getColor(getContext(), R.color.primary_text))
                        .backgroundColor(ContextCompat.getColor(getContext(), R.color.tile1))
                        .titleColor(ContextCompat.getColor(getContext(), R.color.tile4))
                        .content("Please, add a comment for this photo")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("Comment","", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                unaFoto.setComentario(input.toString());

                                actualizarFoto(unaFoto);
                                adapterFotos.notifyDataSetChanged();


                            }
                        }).show();

    }
    public void crearDialogoParaModificarComentario(final Foto unaFoto){

        new MaterialDialog.Builder(getContext())
                .title("Add a comment")
                .contentColor(ContextCompat.getColor(getContext(), R.color.primary_text))
                .backgroundColor(ContextCompat.getColor(getContext(), R.color.tile1))
                .titleColor(ContextCompat.getColor(getContext(), R.color.tile4))
                .content("Please, add a comment for this photo")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Comment","", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, final CharSequence input) {

                        Realm realm=Realm.getDefaultInstance();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                               unaFoto.setComentario(input.toString());
                                adapterFotos.notifyDataSetChanged();
                            }
                        });



                    }
                }).show();

    }

    private void actualizarFoto(final Foto unaFoto) {
        Realm realm=Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                    Foto lafoto = realm.copyToRealmOrUpdate(unaFoto);


            }
        });


    }

}



