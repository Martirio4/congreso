package com.nomad.audit5s.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.nomad.audit5s.Model.Foto;
import com.nomad.audit5s.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import io.realm.RealmList;

/**
 * Created by elmar on 18/5/2017.
 */

public class AdapterFotos extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private RealmList<Foto> listaFotosOriginales;
    private RealmList<Foto> listaFotosFavoritos;
    private View.OnClickListener listener;
    private AdapterView.OnLongClickListener listenerLong;
    private Favoritable favoritable;

    public void setLongListener(View.OnLongClickListener unLongListener) {
        this.listenerLong = unLongListener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setListaFotosOriginales(RealmList<Foto> listaFotosOriginales) {
        this.listaFotosOriginales = listaFotosOriginales;
    }

    public void addListaFotosOriginales(RealmList<Foto> listaFotosOriginales) {
        this.listaFotosOriginales.addAll(listaFotosOriginales);
    }


    public RealmList<Foto> getListaFotosOriginales() {
        return listaFotosOriginales;
    }

    //crear vista y viewholder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewCelda;
        FragmentActivity unaActivity = (FragmentActivity) context;
        FragmentManager fragmentManager = (FragmentManager) unaActivity.getSupportFragmentManager();
        viewCelda = layoutInflater.inflate(R.layout.detalle_celda_recycler_fotos, parent, false);

       final FotoViewHolder fotosViewHolder = new FotoViewHolder(viewCelda);
        viewCelda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (fotosViewHolder.editText.getVisibility()==View.VISIBLE) {
                    if(fotosViewHolder.textView.getText()==null||fotosViewHolder.editText.getText().toString().isEmpty()){
                        fotosViewHolder.editText.setVisibility(View.GONE);
                    }
                    else{
                        fotosViewHolder.textView.setText(fotosViewHolder.editText.getText().toString());
                        fotosViewHolder.editText.setText("");
                        imm.hideSoftInputFromWindow(fotosViewHolder.editText.getWindowToken(), 0);
                        fotosViewHolder.editText.setVisibility(View.GONE);
                    }
                }
                else{
                    fotosViewHolder.editText.setVisibility(View.VISIBLE);
                    fotosViewHolder.editText.requestFocus();
                    imm.showSoftInput(fotosViewHolder.editText, InputMethodManager.SHOW_IMPLICIT);
                }
                fotosViewHolder.textView.setVisibility(View.VISIBLE);

            }
        });

        return fotosViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Foto unFoto = listaFotosOriginales.get(position);
        FotoViewHolder FotoViewHolder = (FotoViewHolder) holder;
        FotoViewHolder.cargarFoto(unFoto);


    }

    @Override
    public int getItemCount() {
        return listaFotosOriginales.size();
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }

    @Override
    public boolean onLongClick(View v) {
        listenerLong.onLongClick(v);
        return true;
    }

    //creo el viewholder que mantiene las referencias
    //de los elementos de la celda

    private class FotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private EditText editText;
        //private TextView textViewTitulo;


        public FotoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imagenCamara);
            textView =(TextView) itemView.findViewById(R.id.nombreNuevaArea);
            editText=(EditText)itemView.findViewById(R.id.editFoto);
        }

        public void cargarFoto(Foto unFoto) {

            File f =new File(unFoto.getRutaFoto());


            Picasso.with(imageView.getContext())
                    .load(f)
                    .into(imageView);
        }


    }

    public interface Favoritable {
        public void recibirFotoFavorito(Foto unFoto);
    }

    // Decodes image and scales it to reduce memory consumption
    

}
