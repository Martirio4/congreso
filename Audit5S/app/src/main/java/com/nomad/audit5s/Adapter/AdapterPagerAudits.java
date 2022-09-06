package com.nomad.audit5s.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nomad.audit5s.fragments.FragmentMyAudits;
import com.nomad.audit5s.fragments.FragmentRanking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 31/5/2017.
 */

public class AdapterPagerAudits extends FragmentStatePagerAdapter {

    //EL ADAPTER NECESITA SIEMPRE UNA LISTA DE FRAGMENTS PARA MOSTRAR
    private List<Fragment> listaFragments;

    private List<String> unaListaTitulos;

    public AdapterPagerAudits(FragmentManager fm, List<String>listaTitulos) {
        super(fm);

        //INICIALIZO LA LISTA DE FRAGMENT
        unaListaTitulos=listaTitulos;
        listaFragments = new ArrayList<>();


        //LE CARGO LOS FRAGMENTS QUE QUIERO. UTILIZO LA LISTA DE PELICULAS Y SERIES PARA CREAR LOS FRAGMENTS.

        for (String unString : unaListaTitulos) {
            if (unString.equals("auditoria")){
                listaFragments.add(FragmentMyAudits.crearFragmentMyAudit());
            }
            if (unString.equals("ranking")){
                listaFragments.add(FragmentRanking.crearFragmentRanking());
            }
        }

        //LE AVISO AL ADAPTER QUE CAMBIO SU LISTA DE FRAGMENTS.
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return listaFragments.get(position);
    }

    @Override
    public int getCount() {
        return listaFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return unaListaTitulos.get(position);
    }

    public void setUnaListaTitulos(List<String> unaListaTitulos) {
        this.unaListaTitulos = unaListaTitulos;
    }
}
