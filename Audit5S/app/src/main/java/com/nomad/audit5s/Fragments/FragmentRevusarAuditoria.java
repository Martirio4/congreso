package com.nomad.audit5s.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nomad.audit5s.Activities.ActivityVerAuditorias;
import com.nomad.audit5s.Adapter.AdapterFotos;
import com.nomad.audit5s.Model.Auditoria;
import com.nomad.audit5s.Model.SubItem;
import com.nomad.audit5s.R;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRevusarAuditoria extends Fragment {
    
    private static final String NUMERO="NUMERO";
    private String idNumero;
    
    private TextView sub1;
    private TextView punt1;
    private TextView sub2;
    private TextView punt2;
    private TextView sub3;
    private TextView punt3;
    private TextView sub4;
    private TextView punt4;

    private TextView subTit;
    private TextView fecha;
    private TextView puntaje;
    
    private RecyclerView recycler1;
    private RecyclerView recycler2;
    private RecyclerView recycler3;
    private RecyclerView recycler4;
    
    private AdapterFotos adapter1;
    private AdapterFotos adapter2;
    private AdapterFotos adapter3;
    private AdapterFotos adapter4;

    private Auditoria mAudit;
    


    public FragmentRevusarAuditoria() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revusar_auditoria, container, false);
        
        Bundle bundle=getArguments();
        idNumero=bundle.getString(NUMERO);

        subTit=view.findViewById(R.id.tituloVerAudit);
        fecha=view.findViewById(R.id.fechaAuditVerAudit);
        puntaje=view.findViewById(R.id.puntajeVerAudit);

        sub1=view.findViewById(R.id.subitem1VerAudit);
        sub2=view.findViewById(R.id.subitem2VerAudit);
        sub3=view.findViewById(R.id.subitem3VerAudit);
        sub4=view.findViewById(R.id.subitem4VerAudit);
        
        punt1=view.findViewById(R.id.score1sVerAudit);
        punt2=view.findViewById(R.id.score2VerAudit);
        punt3=view.findViewById(R.id.score3VerAudit);
        punt4=view.findViewById(R.id.score4VerAudit);

        recycler1= view.findViewById(R.id.recycler1sVerAudit);
        recycler2= view.findViewById(R.id.recycler2VerAudit);
        recycler3= view.findViewById(R.id.recycler3VerAudit);
        recycler4= view.findViewById(R.id.recycler4VerAudit);
        
        adapter1=new AdapterFotos();
        adapter2=new AdapterFotos();
        adapter3=new AdapterFotos();
        adapter4=new AdapterFotos();
        
        adapter1.setContext(getContext());
        adapter2.setContext(getContext());
        adapter3.setContext(getContext());
        adapter4.setContext(getContext());
        
        recycler1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recycler1.setAdapter(adapter1);
        recycler1.setHasFixedSize(true);

        recycler2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recycler2.setAdapter(adapter2);
        recycler2.setHasFixedSize(true);

        recycler3.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recycler3.setAdapter(adapter3);
        recycler3.setHasFixedSize(true);

        recycler4.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recycler4.setAdapter(adapter4);
        recycler4.setHasFixedSize(true);

        Realm realm = Realm.getDefaultInstance();
        mAudit=realm.where(Auditoria.class)
                .equalTo("idAuditoria", ActivityVerAuditorias.idAuditoria)
                .findFirst();

        subTit.setText(mAudit.getAreaAuditada().getNombreArea());
        fecha.setText(mAudit.getFechaAuditoria());
        String puntajeTexto=mAudit.getPuntajeFinal().toString();
        puntaje.setText(puntajeTexto);

        switch(idNumero){
            case "1":
                cargarSeiri();
                break;
            case "2":
                cargarSeiton();
                break;
            case "3":
                cargarSeiso();
                break;
            case "4":
                cargarSeiketsu();
                break;
            case "5":
                cargarShitsuke();
                break;
        }

        return view;
    }

    public void cargarSeiri(){
        RealmList<SubItem>lista=mAudit.getSubItems();

        sub1.setText(lista.get(0).getEnunciado());
        sub2.setText(lista.get(1).getEnunciado());
        sub3.setText(lista.get(2).getEnunciado());
        sub4.setText(lista.get(3).getEnunciado());

        String aux0=lista.get(0).getPuntuacion1().toString();
        String aux1=lista.get(1).getPuntuacion1().toString();
        String aux2=lista.get(2).getPuntuacion1().toString();
        String aux3=lista.get(3).getPuntuacion1().toString();

        punt1.setText(aux0);
        punt2.setText(aux1);
        punt3.setText(aux2);
        punt4.setText(aux3);

        adapter1.setListaFotosOriginales(lista.get(0).getListaFotos());
        adapter1.notifyDataSetChanged();
        adapter2.setListaFotosOriginales(lista.get(1).getListaFotos());
        adapter2.notifyDataSetChanged();
        adapter3.setListaFotosOriginales(lista.get(2).getListaFotos());
        adapter3.notifyDataSetChanged();
        adapter4.setListaFotosOriginales(lista.get(3).getListaFotos());
        adapter4.notifyDataSetChanged();

    }
    public void cargarSeiton(){
        RealmList<SubItem>lista=mAudit.getSubItems();

        sub1.setText(lista.get(4).getEnunciado());
        sub2.setText(lista.get(5).getEnunciado());
        sub3.setText(lista.get(6).getEnunciado());
        sub4.setText(lista.get(7).getEnunciado());

        String aux0=lista.get(4).getPuntuacion1().toString();
        String aux1=lista.get(5).getPuntuacion1().toString();
        String aux2=lista.get(6).getPuntuacion1().toString();
        String aux3=lista.get(7).getPuntuacion1().toString();

        punt1.setText(aux0);
        punt2.setText(aux1);
        punt3.setText(aux2);
        punt4.setText(aux3);

        adapter1.setListaFotosOriginales(lista.get(4).getListaFotos());
        adapter1.notifyDataSetChanged();
        adapter2.setListaFotosOriginales(lista.get(5).getListaFotos());
        adapter2.notifyDataSetChanged();
        adapter3.setListaFotosOriginales(lista.get(6).getListaFotos());
        adapter3.notifyDataSetChanged();
        adapter4.setListaFotosOriginales(lista.get(7).getListaFotos());
        adapter4.notifyDataSetChanged();

    }
    public void cargarSeiso(){
        RealmList<SubItem>lista=mAudit.getSubItems();

        sub1.setText(lista.get(8).getEnunciado());
        sub2.setText(lista.get(9).getEnunciado());
        sub3.setText(lista.get(10).getEnunciado());
        sub4.setText(lista.get(11).getEnunciado());

        String aux0=lista.get(8).getPuntuacion1().toString();
        String aux1=lista.get(9).getPuntuacion1().toString();
        String aux2=lista.get(10).getPuntuacion1().toString();
        String aux3=lista.get(11).getPuntuacion1().toString();

        punt1.setText(aux0);
        punt2.setText(aux1);
        punt3.setText(aux2);
        punt4.setText(aux3);

        adapter1.setListaFotosOriginales(lista.get(8).getListaFotos());
        adapter1.notifyDataSetChanged();
        adapter2.setListaFotosOriginales(lista.get(9).getListaFotos());
        adapter2.notifyDataSetChanged();
        adapter3.setListaFotosOriginales(lista.get(10).getListaFotos());
        adapter3.notifyDataSetChanged();
        adapter4.setListaFotosOriginales(lista.get(11).getListaFotos());
        adapter4.notifyDataSetChanged();

    }
    public void cargarSeiketsu(){
        RealmList<SubItem>lista=mAudit.getSubItems();

        sub1.setText(lista.get(12).getEnunciado());
        sub2.setText(lista.get(13).getEnunciado());
        sub3.setText(lista.get(14).getEnunciado());
        sub4.setText(lista.get(15).getEnunciado());

        String aux0=lista.get(12).getPuntuacion1().toString();
        String aux1=lista.get(13).getPuntuacion1().toString();
        String aux2=lista.get(14).getPuntuacion1().toString();
        String aux3=lista.get(15).getPuntuacion1().toString();

        punt1.setText(aux0);
        punt2.setText(aux1);
        punt3.setText(aux2);
        punt4.setText(aux3);

        adapter1.setListaFotosOriginales(lista.get(12).getListaFotos());
        adapter1.notifyDataSetChanged();
        adapter2.setListaFotosOriginales(lista.get(13).getListaFotos());
        adapter2.notifyDataSetChanged();
        adapter3.setListaFotosOriginales(lista.get(14).getListaFotos());
        adapter3.notifyDataSetChanged();
        adapter4.setListaFotosOriginales(lista.get(15).getListaFotos());
        adapter4.notifyDataSetChanged();

    }
    public void cargarShitsuke(){
        RealmList<SubItem>lista=mAudit.getSubItems();

        sub1.setText(lista.get(16).getEnunciado());
        sub1.setText(lista.get(17).getEnunciado());
        sub1.setText(lista.get(18).getEnunciado());
        sub1.setText(lista.get(19).getEnunciado());

        String aux0=lista.get(16).getPuntuacion1().toString();
        String aux1=lista.get(17).getPuntuacion1().toString();
        String aux2=lista.get(18).getPuntuacion1().toString();
        String aux3=lista.get(19).getPuntuacion1().toString();

        punt1.setText(aux0);
        punt2.setText(aux1);
        punt3.setText(aux2);
        punt4.setText(aux3);

        adapter1.setListaFotosOriginales(lista.get(16).getListaFotos());
        adapter1.notifyDataSetChanged();
        adapter2.setListaFotosOriginales(lista.get(17).getListaFotos());
        adapter2.notifyDataSetChanged();
        adapter3.setListaFotosOriginales(lista.get(18).getListaFotos());
        adapter3.notifyDataSetChanged();
        adapter4.setListaFotosOriginales(lista.get(19).getListaFotos());
        adapter4.notifyDataSetChanged();

    }


    public static Fragment crearFragment(String unString) {

        FragmentRevusarAuditoria fragment= new FragmentRevusarAuditoria();
        Bundle bundle=new Bundle();
        bundle.putString(FragmentRevusarAuditoria.NUMERO,unString);
        fragment.setArguments(bundle);
        return fragment;
    }
}
