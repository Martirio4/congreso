package com.nomad.audit5s.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nomad.audit5s.Fragments.FragmentSubitem;
import com.nomad.audit5s.Model.SubItem;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Pablo on 31/5/2017.
 */

public class AdapterPagerSubItems extends FragmentStatePagerAdapter {

    //EL ADAPTER NECESITA SIEMPRE UNA LISTA DE FRAGMENTS PARA MOSTRAR
    private List<Fragment> listaFragments;
    private RealmList<SubItem> listaSubItems = new RealmList<>();


    public AdapterPagerSubItems(FragmentManager fm) {
        super(fm);


        //INICIALIZO LA LISTA DE FRAGMENT
        listaFragments = new ArrayList<>();

        //LE CARGO LOS FRAGMENTS QUE QUIERO. UTILIZO LA LISTA DE PELICULAS Y SERIES PARA CREAR LOS FRAGMENTS.

        for (SubItem unSubItem : listaSubItems) {
            listaFragments.add(FragmentSubitem.fragmentSubItem(unSubItem));
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

    public void setListaSubItems(RealmList<SubItem> listaSubItems) {
        this.listaSubItems = listaSubItems;
        for (SubItem unSubItem : listaSubItems) {
            listaFragments.add(FragmentSubitem.fragmentSubItem(unSubItem));
        }
        notifyDataSetChanged();
    }

    public void addListaSubItems(List<SubItem> listaSubItems) {
        this.listaSubItems.addAll(listaSubItems);
        for (SubItem unSubItem : listaSubItems) {
            listaFragments.add(FragmentSubitem.fragmentSubItem(unSubItem));
        }
        notifyDataSetChanged();
    }


}
