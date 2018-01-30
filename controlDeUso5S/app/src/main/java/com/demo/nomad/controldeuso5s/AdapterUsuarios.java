package com.demo.nomad.controldeuso5s;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by elmar on 18/5/2017.
 */

public class AdapterUsuarios extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private ArrayList<Usuario> listaAuditsOriginales;
    private View.OnClickListener listener;
    private AdapterView.OnLongClickListener listenerLong;


    public void setLongListener(View.OnLongClickListener unLongListener) {
        this.listenerLong = unLongListener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setListaAuditsOriginales(ArrayList<Usuario> listaAuditsOriginales) {
        this.listaAuditsOriginales = listaAuditsOriginales;
    }

    public void addListaUsuariosOriginales(List<Usuario> listaUsuariosOriginales) {
        this.listaAuditsOriginales.addAll(listaUsuariosOriginales);
    }


    public List<Usuario> getListaAuditsOriginales() {
        return listaAuditsOriginales;
    }

    //crear vista y viewholder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewCelda;
        FragmentActivity unaActivity = (FragmentActivity) context;
        FragmentManager fragmentManager = (FragmentManager) unaActivity.getSupportFragmentManager();
        viewCelda = layoutInflater.inflate(R.layout.detalle_celda_ver_usuarios, parent, false);
        viewCelda.setOnClickListener(this);

        return new UsuarioViewHolder(viewCelda);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Usuario unUsuario = listaAuditsOriginales.get(position);
        UsuarioViewHolder UsuariosViewHolder = (UsuarioViewHolder) holder;
        UsuariosViewHolder.cargarUsuario(unUsuario);
    }

    @Override
    public int getItemCount() {
        return listaAuditsOriginales.size();
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

    private class UsuarioViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreUsuario;
        private TextView auditorias;
        private TextView areas;
        private TextView fotos;
        private TextView reportes;

        //private TextView textViewTitulo;

        public UsuarioViewHolder(View itemView) {
            super(itemView);


            auditorias = (TextView) itemView.findViewById(R.id.cantAuditorias);
            areas = (TextView) itemView.findViewById(R.id.cantAreas);
            fotos = (TextView) itemView.findViewById(R.id.cantfotos);
            reportes = (TextView) itemView.findViewById(R.id.cantReportes);
            nombreUsuario = (TextView) itemView.findViewById(R.id.cantApertura);

        }

        public void cargarUsuario(Usuario unUsuario) {

            if (unUsuario.getEstadisticas()==null){
                nombreUsuario.setText(unUsuario.getDatos().getEmail());

            }
            else {

                if (unUsuario.getEstadisticas().getCantidadAuditorias() != null && !unUsuario.getEstadisticas().getCantidadAuditorias().isEmpty()) {
                    auditorias.setText(unUsuario.getEstadisticas().getCantidadAuditorias().toString());
                } else {
                    auditorias.setText("sin datos");
                }
                if (unUsuario.getEstadisticas().getCantidadAreasCreadas() != null && !unUsuario.getEstadisticas().getCantidadAreasCreadas().isEmpty()) {
                    areas.setText(unUsuario.getEstadisticas().getCantidadAreasCreadas().toString());
                } else {
                    areas.setText("sin datos");
                }
                if (unUsuario.getEstadisticas().getCantidadFotosTomadas() != null && !unUsuario.getEstadisticas().getCantidadFotosTomadas().isEmpty()) {
                    fotos.setText(unUsuario.getEstadisticas().getCantidadFotosTomadas().toString());
                } else {
                    fotos.setText("sin datos");
                }
                if (unUsuario.getEstadisticas().getReportesEnviados() != null && !unUsuario.getEstadisticas().getReportesEnviados().isEmpty()) {
                    reportes.setText(unUsuario.getEstadisticas().getReportesEnviados().toString());
                } else {
                    reportes.setText("sin datos");
                }
                if (unUsuario.getDatos().getEmail() != null && !unUsuario.getDatos().getEmail().isEmpty()) {
                    nombreUsuario.setText(unUsuario.getDatos().getEmail().toString());
                } else {
                    nombreUsuario.setText("sin datos");
                }
            }

        }
    }



}


    // Decodes image and scales it to reduce memory consumption