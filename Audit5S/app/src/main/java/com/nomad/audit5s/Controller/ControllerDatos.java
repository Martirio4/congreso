package com.nomad.audit5s.Controller;

import android.content.Context;

import com.nomad.audit5s.Model.SubItem;
import com.nomad.audit5s.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by elmar on 13/8/2017.
 */

public class ControllerDatos  {

    private Context context;

    public ControllerDatos(Context context) {
        this.context = context;
    }


    public RealmList<SubItem>traerSubItems(){
        RealmList<SubItem>unaLista=new RealmList<>();
        
        SubItem subItem1=new SubItem();
        subItem1.setEnunciado(context.getString(R.string.enunciado11));
        subItem1.setPunto1(context.getString(R.string.punto111));
        subItem1.setPunto2(context.getString(R.string.punto112));
        subItem1.setPunto3(context.getString(R.string.punto113));
        subItem1.setPunto4(context.getString(R.string.punto114));
        subItem1.setPunto5(context.getString(R.string.punto115));
        subItem1.setPertenencia("seiri");
        unaLista.add(subItem1);
        SubItem subItem12=new SubItem();
        subItem12.setEnunciado(context.getString(R.string.enunciado12));
        subItem12.setPunto1(context.getString(R.string.punto121));
        subItem12.setPunto2(context.getString(R.string.punto122));
        subItem12.setPunto3(context.getString(R.string.punto123));
        subItem12.setPunto4(context.getString(R.string.punto124));
        subItem12.setPunto5(context.getString(R.string.punto125));
        subItem1.setPertenencia("seiri");
        unaLista.add(subItem12);
        SubItem subItem13=new SubItem();
        subItem13.setEnunciado(context.getString(R.string.enunciado13));
        subItem13.setPunto1(context.getString(R.string.punto131));
        subItem13.setPunto2(context.getString(R.string.punto132));
        subItem13.setPunto3(context.getString(R.string.punto133));
        subItem13.setPunto4(context.getString(R.string.punto134));
        subItem13.setPunto5(context.getString(R.string.punto135));
        subItem1.setPertenencia("seiri");
        unaLista.add(subItem13);
        SubItem subItem14=new SubItem();
        subItem14.setEnunciado(context.getString(R.string.enunciado14));
        subItem14.setPunto1(context.getString(R.string.punto141));
        subItem14.setPunto2(context.getString(R.string.punto142));
        subItem14.setPunto3(context.getString(R.string.punto143));
        subItem14.setPunto4(context.getString(R.string.punto144));
        subItem14.setPunto5(context.getString(R.string.punto145));
        subItem1.setPertenencia("Seiri");
        unaLista.add(subItem14);
        SubItem subItem21=new SubItem();
        subItem21.setEnunciado(context.getString(R.string.enunciado21));
        subItem21.setPunto1(context.getString(R.string.punto211));
        subItem21.setPunto2(context.getString(R.string.punto212));
        subItem21.setPunto3(context.getString(R.string.punto213));
        subItem21.setPunto4(context.getString(R.string.punto214));
        subItem21.setPunto5(context.getString(R.string.punto215));
        subItem1.setPertenencia("Seiton");
        unaLista.add(subItem21);
        SubItem subItem22=new SubItem();
        subItem22.setEnunciado(context.getString(R.string.enunciado22));
        subItem22.setPunto1(context.getString(R.string.punto221));
        subItem22.setPunto2(context.getString(R.string.punto222));
        subItem22.setPunto3(context.getString(R.string.punto223));
        subItem22.setPunto4(context.getString(R.string.punto224));
        subItem22.setPunto5(context.getString(R.string.punto225));
        subItem1.setPertenencia("Seiton");
        unaLista.add(subItem22);
        SubItem subItem23=new SubItem();
        subItem23.setEnunciado(context.getString(R.string.enunciado23));
        subItem23.setPunto1(context.getString(R.string.punto231));
        subItem23.setPunto2(context.getString(R.string.punto232));
        subItem23.setPunto3(context.getString(R.string.punto233));
        subItem23.setPunto4(context.getString(R.string.punto234));
        subItem23.setPunto5(context.getString(R.string.punto235));
        subItem1.setPertenencia("Seiton");
        unaLista.add(subItem23);
        SubItem subItem24=new SubItem();
        subItem24.setEnunciado(context.getString(R.string.enunciado24));
        subItem24.setPunto1(context.getString(R.string.punto241));
        subItem24.setPunto2(context.getString(R.string.punto242));
        subItem24.setPunto3(context.getString(R.string.punto243));
        subItem24.setPunto4(context.getString(R.string.punto244));
        subItem24.setPunto5(context.getString(R.string.punto245));
        subItem1.setPertenencia("Seiton");
        unaLista.add(subItem24);
        SubItem subItem31=new SubItem();
        subItem31.setEnunciado(context.getString(R.string.enunciado31));
        subItem31.setPunto1(context.getString(R.string.punto311));
        subItem31.setPunto2(context.getString(R.string.punto312));
        subItem31.setPunto3(context.getString(R.string.punto313));
        subItem31.setPunto4(context.getString(R.string.punto314));
        subItem31.setPunto5(context.getString(R.string.punto315));
        subItem1.setPertenencia("Seiso");
        unaLista.add(subItem31);
        SubItem subItem32=new SubItem();
        subItem32.setEnunciado(context.getString(R.string.enunciado32));
        subItem32.setPunto1(context.getString(R.string.punto321));
        subItem32.setPunto2(context.getString(R.string.punto322));
        subItem32.setPunto3(context.getString(R.string.punto323));
        subItem32.setPunto4(context.getString(R.string.punto324));
        subItem32.setPunto5(context.getString(R.string.punto325));
        subItem1.setPertenencia("Seiso");
        unaLista.add(subItem32);
        SubItem subItem33=new SubItem();
        subItem33.setEnunciado(context.getString(R.string.enunciado33));
        subItem33.setPunto1(context.getString(R.string.punto331));
        subItem33.setPunto2(context.getString(R.string.punto332));
        subItem33.setPunto3(context.getString(R.string.punto333));
        subItem33.setPunto4(context.getString(R.string.punto334));
        subItem33.setPunto5(context.getString(R.string.punto335));
        subItem1.setPertenencia("Seiso");
        unaLista.add(subItem33);
        SubItem subItem34=new SubItem();
        subItem34.setEnunciado(context.getString(R.string.enunciado34));
        subItem34.setPunto1(context.getString(R.string.punto341));
        subItem34.setPunto2(context.getString(R.string.punto342));
        subItem34.setPunto3(context.getString(R.string.punto343));
        subItem34.setPunto4(context.getString(R.string.punto344));
        subItem34.setPunto5(context.getString(R.string.punto345));
        subItem1.setPertenencia("Seiso");
        unaLista.add(subItem34);
        
        
        
















        return unaLista;
    }
}
