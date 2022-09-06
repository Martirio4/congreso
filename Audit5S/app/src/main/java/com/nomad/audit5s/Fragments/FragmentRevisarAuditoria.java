package com.nomad.audit5s.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nomad.audit5s.activities.ActivityVerAuditorias;
import com.nomad.audit5s.adapter.AdapterVerAudit;
import com.nomad.audit5s.model.Auditoria;
import com.nomad.audit5s.model.SubItem;
import com.nomad.audit5s.R;

import java.text.NumberFormat;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRevisarAuditoria extends Fragment {
    
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
    
    private AdapterVerAudit adapter1;
    private AdapterVerAudit adapter2;
    private AdapterVerAudit adapter3;
    private AdapterVerAudit adapter4;

    private Auditoria mAudit;
    


    public FragmentRevisarAuditoria() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revisar_auditoria, container, false);
        
        Bundle bundle=getArguments();
        idNumero=bundle.getString(NUMERO);

        Typeface robotoL = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");

        subTit=view.findViewById(R.id.tituloVerAudit);
        subTit.setTypeface(robotoL);
        fecha=view.findViewById(R.id.fechaAuditVerAudit);
        fecha.setTypeface(robotoL);
        puntaje=view.findViewById(R.id.puntajeVerAudit);
        puntaje.setTypeface(robotoL);

        sub1=view.findViewById(R.id.subitem1VerAudit);
        sub1.setTypeface(robotoL);
        sub2=view.findViewById(R.id.subitem2VerAudit);
        sub2.setTypeface(robotoL);
        sub3=view.findViewById(R.id.subitem3VerAudit);
        sub3.setTypeface(robotoL);
        sub4=view.findViewById(R.id.subitem4VerAudit);
        sub4.setTypeface(robotoL);
        
        punt1=view.findViewById(R.id.score1sVerAudit);
        punt1.setTypeface(robotoL);
        punt2=view.findViewById(R.id.score2sVerAudit);
        punt2.setTypeface(robotoL);
        punt3=view.findViewById(R.id.score3sVerAudit);
        punt3.setTypeface(robotoL);
        punt4=view.findViewById(R.id.score4sVerAudit);
        punt4.setTypeface(robotoL);

        recycler1= view.findViewById(R.id.recycler1sVerAudit);
        recycler2= view.findViewById(R.id.recycler2VerAudit);
        recycler3= view.findViewById(R.id.recycler3VerAudit);
        recycler4= view.findViewById(R.id.recycler4VerAudit);
        
        adapter1=new AdapterVerAudit();
        adapter2=new AdapterVerAudit();
        adapter3=new AdapterVerAudit();
        adapter4=new AdapterVerAudit();
        
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

        Double puntajeAuditoria=mAudit.getPuntajeFinal();
        Locale locale = new Locale("en","US");
        NumberFormat format = NumberFormat.getPercentInstance(locale);
        String puntajeTexto = format.format(puntajeAuditoria);


        puntaje.setText(puntajeTexto);

        if (mAudit.getPuntajeFinal()<=0.5f){
            puntaje.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.semaRojo));
        }
        else{
            if (mAudit.getPuntajeFinal() <0.8f){
                puntaje.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.semaAmarillo));
            }
            else{
                puntaje.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.semaVerde));
            }
        }

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
        sub2.setText(lista.get(17).getEnunciado());
        sub3.setText(lista.get(18).getEnunciado());
        sub4.setText(lista.get(19).getEnunciado());

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

        FragmentRevisarAuditoria fragment= new FragmentRevisarAuditoria();
        Bundle bundle=new Bundle();
        bundle.putString(FragmentRevisarAuditoria.NUMERO,unString);
        fragment.setArguments(bundle);
        return fragment;
    }
}
