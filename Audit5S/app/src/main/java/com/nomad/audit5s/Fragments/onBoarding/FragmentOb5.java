package com.nomad.audit5s.fragments.onBoarding;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nomad.audit5s.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOb5 extends Fragment {


    public FragmentOb5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
// Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ob1, container, false);
        ImageView imagen = (ImageView) view.findViewById(R.id.imagenVoladora);
        imagen.setImageResource(R.drawable.apaisado);
        imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return view;
    }

}
