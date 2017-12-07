package com.nomad.audit5s.Fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nomad.audit5s.Adapter.AdapterArea;
import com.nomad.audit5s.Model.Area;
import com.nomad.audit5s.R;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSeleccionAerea extends Fragment {

    private RealmList<Area> listaAreas;
    private RecyclerView recyclerAreas;
    private AdapterArea adapterArea;
    private LinearLayoutManager layoutManager;

    private Notificable notificable;


    public FragmentSeleccionAerea() {
        // Required empty public constructor
    }

    public interface Notificable{
        public void comenzarAuditoria(Area unArea);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_seleccion_aerea, container, false);
        TextView textoSinAreas=(TextView)view.findViewById(R.id.textoSinAreas);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Area> result2 = realm.where(Area.class)
                .findAll();

        if  (result2.size()<1){
            textoSinAreas.setVisibility(View.VISIBLE);
        }
        else{
            textoSinAreas.setVisibility(View.GONE);
        }

        listaAreas=new RealmList<>();
        listaAreas.addAll(result2);
        recyclerAreas= (RecyclerView)view.findViewById(R.id.recyclerArea);
        adapterArea= new AdapterArea();
        adapterArea.setContext(getContext());
        layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerAreas.setLayoutManager(layoutManager);
        adapterArea.setListaAreasOriginales(listaAreas);
        recyclerAreas.setAdapter(adapterArea);

        View.OnClickListener listenerArea = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer posicion = recyclerAreas.getChildAdapterPosition(v);
                RealmList<Area> listaAreas = adapterArea.getListaAreasOriginales();
                Area areaClickeada = listaAreas.get(posicion);
                notificable.comenzarAuditoria(areaClickeada);
            }
        };
        adapterArea.setListener(listenerArea);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        Typeface robotoR = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        TextView unText=toolbar.findViewById(R.id.textoToolbar);
        unText.setTypeface(robotoR);
        unText.setTextColor(getResources().getColor(R.color.tile5));
        unText.setText(getResources().getString(R.string.selectArea));

        if (((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificable=(Notificable) context;
    }
}
