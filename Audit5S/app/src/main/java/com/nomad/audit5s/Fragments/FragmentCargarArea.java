package com.nomad.audit5s.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.nomad.audit5s.Model.Area;
import com.nomad.audit5s.Model.Foto;
import com.nomad.audit5s.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import id.zelory.compressor.Compressor;
import io.realm.Realm;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCargarArea extends Fragment {

    private EditText editNombre;
    private ImageView imagenAreaNueva;
    private FloatingActionButton fabAreaNueva;
    private Button botonGuardar;
    private TextInputLayout inputNombre;
    private File fotoOriginal;
    private File fotoComprimida;
    private Exitable unExitable;


    public FragmentCargarArea() {
        // Required empty public constructor
    }
public interface Exitable{
    public void cerrarFragment();
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cargar_area, container, false);

        editNombre=(EditText) view.findViewById(R.id.nombreNuevaArea);
        imagenAreaNueva=(ImageView) view.findViewById(R.id.imagenNuevaArea);
        fabAreaNueva=(FloatingActionButton) view.findViewById(R.id.fab_NuevaArea);
        botonGuardar=(Button) view.findViewById(R.id.btn_guardarAreaNueva);
        inputNombre=(TextInputLayout) view.findViewById(R.id.inputNuevaArea);

        fabAreaNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openChooserWithGallery(FragmentCargarArea.this, "Select image",1);
            }
        });

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (puedoGuardar()){
                    final Area unArea= new Area();
                    Foto unaFoto= new Foto();
                    unaFoto.setRutaFoto(fotoComprimida.getAbsolutePath());
                    unArea.setNombreArea(editNombre.getText().toString());
                    unArea.setFotoArea(unaFoto);
                    unArea.setIdArea("area"+ UUID.randomUUID());

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
                else{

                }
            }
        });

        return view;
    }

    public Boolean puedoGuardar(){
        if (editNombre.getText()==null || editNombre.getText().toString().isEmpty()){
            inputNombre.setError("Name field can´t be empty");
            return false;
        }
        else {
            if (editNombre.getText().toString().length() > 13) {

                inputNombre.setError("Name must be under 13 characters");
                return false;
            } else {
                return true;
            }
        }
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
                            Toast.makeText(getContext(), R.string.seEliminoFoto, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), R.string.noSeEliminoFoto, Toast.LENGTH_SHORT).show();
                        }

                    //cargo la imagen en el imageview
                    File f=new File(fotoComprimida.getAbsolutePath());
                    Picasso.with(getContext())
                            .load(f)
                            .into(imagenAreaNueva);
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

    public void dialogoExito(Area unArea){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Dialog);

        builder.setTitle("New area successfully created")
                .setMessage("The area: " + unArea.getNombreArea() +"\n"+ "has been succesfully added to the system")
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    limpiarCampos();
                    unExitable.cerrarFragment();
                    }
                })
                .setNegativeButton("Add new Area", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    limpiarCampos();
                    }
                });


        AlertDialog unDialogo = builder.create();
        unDialogo.show();
        final Button positiveButton = unDialogo.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button negativeButton = unDialogo.getButton(AlertDialog.BUTTON_NEGATIVE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            negativeButton.setTextColor(getContext().getColor(R.color.tile1));
            positiveButton.setTextColor(getContext().getColor(R.color.tile1));
        } else {
            negativeButton.setTextColor(getContext().getResources().getColor(R.color.tile1));
            positiveButton.setTextColor(getContext().getResources().getColor(R.color.tile1));
        }
        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
        positiveButton.setLayoutParams(positiveButtonLL);

    }

    private void limpiarCampos() {
        imagenAreaNueva.setImageResource(R.color.tile3);
        editNombre.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.unExitable=(Exitable)context;
    }
}
