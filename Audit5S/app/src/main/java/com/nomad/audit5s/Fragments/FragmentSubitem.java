package com.nomad.audit5s.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nomad.audit5s.Model.SubItem;
import com.nomad.audit5s.R;

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

    private String enunciado;
    private String criterio1;
    private String criterio2;
    private String criterio3;
    private String criterio4;
    private String criterio5;
    private String pertenencia;

    private TextView textViewEnunciado;
    private TextView textViewcriterio1;
    private TextView textViewcriterio2;
    private TextView textViewcriterio3;
    private TextView textViewcriterio4;
    private TextView textViewcriterio5;
    private TextView textviewPertenencia;


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

        textViewcriterio1=(TextView) view.findViewById(R.id.textoopcion1);
        textViewcriterio2=(TextView) view.findViewById(R.id.textoopcion2);
        textViewcriterio3=(TextView) view.findViewById(R.id.textoopcion3);
        textViewcriterio4=(TextView) view.findViewById(R.id.textoopcion4);
        textViewcriterio5=(TextView) view.findViewById(R.id.textoopcion5);
        textViewEnunciado=(TextView) view.findViewById(R.id.textoEnunciado);

        textViewcriterio1.setText(criterio1);
        textViewcriterio2.setText(criterio2);
        textViewcriterio3.setText(criterio3);
        textViewcriterio4.setText(criterio4);
        textViewcriterio5.setText(criterio5);
        textViewEnunciado.setText(enunciado);

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

}
