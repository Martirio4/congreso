package com.nomad.audit5s.Controller;

import android.content.Context;

import com.nomad.audit5s.Activities.ActivityAuditoria;
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

    public List<String>traerSeiri(){
        List<String> unaLista= new ArrayList<>();
        unaLista.add("1S 1");
        unaLista.add("1S 2");
        unaLista.add("1S 3");
        unaLista.add("1S 4");

        return  unaLista;
    }
    public List<String>traerSeiton(){
        List<String> unaLista= new ArrayList<>();
        unaLista.add("2S 1");
        unaLista.add("2S 2");
        unaLista.add("2S 3");
        unaLista.add("2S 4");

        return  unaLista;
    }

    public List<String>traerSeiso(){
        List<String> unaLista= new ArrayList<>();
        unaLista.add("3S 1");
        unaLista.add("3S 2");
        unaLista.add("3S 3");
        unaLista.add("3S 4");

        return  unaLista;
    }

    public List<String>traerSeiketsu(){
        List<String> unaLista= new ArrayList<>();
        unaLista.add("4S 1");
        unaLista.add("4S 2");
        unaLista.add("4S 3");
        unaLista.add("4S 4");

        return  unaLista;
    }

    public List<String>traerShitsuke(){
        List<String> unaLista= new ArrayList<>();
        unaLista.add("5S 1");
        unaLista.add("5S 2");
        unaLista.add("5S 3");
        unaLista.add("5S 4");

        return  unaLista;
    }


    public RealmList<SubItem>traerSubItems(){
        RealmList<SubItem>unaLista=new RealmList<>();
        
        SubItem subItem1=new SubItem();
        subItem1.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem1.setId("1S 1");
        subItem1.setEnunciado(context.getString(R.string.enunciado11));
        subItem1.setPunto1(context.getString(R.string.punto111));
        subItem1.setPunto2(context.getString(R.string.punto112));
        subItem1.setPunto3(context.getString(R.string.punto113));
        subItem1.setPunto4(context.getString(R.string.punto114));
        subItem1.setPunto5(context.getString(R.string.punto115));
        unaLista.add(subItem1);

        SubItem subItem12=new SubItem();
        subItem12.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem12.setId("1S 2");
        subItem12.setEnunciado(context.getString(R.string.enunciado12));
        subItem12.setPunto1(context.getString(R.string.punto121));
        subItem12.setPunto2(context.getString(R.string.punto122));
        subItem12.setPunto3(context.getString(R.string.punto123));
        subItem12.setPunto4(context.getString(R.string.punto124));
        subItem12.setPunto5(context.getString(R.string.punto125));
        unaLista.add(subItem12);

        SubItem subItem13=new SubItem();
        subItem13.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem13.setId("1S 3");
        subItem13.setEnunciado(context.getString(R.string.enunciado13));
        subItem13.setPunto1(context.getString(R.string.punto131));
        subItem13.setPunto2(context.getString(R.string.punto132));
        subItem13.setPunto3(context.getString(R.string.punto133));
        subItem13.setPunto4(context.getString(R.string.punto134));
        subItem13.setPunto5(context.getString(R.string.punto135));
        unaLista.add(subItem13);

        SubItem subItem14=new SubItem();
        subItem14.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem14.setId("1S 4");
        subItem14.setEnunciado(context.getString(R.string.enunciado14));
        subItem14.setPunto1(context.getString(R.string.punto141));
        subItem14.setPunto2(context.getString(R.string.punto142));
        subItem14.setPunto3(context.getString(R.string.punto143));
        subItem14.setPunto4(context.getString(R.string.punto144));
        subItem14.setPunto5(context.getString(R.string.punto145));
        unaLista.add(subItem14);

        SubItem subItem21=new SubItem();
        subItem21.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem21.setId("2S 1");
        subItem21.setEnunciado(context.getString(R.string.enunciado21));
        subItem21.setPunto1(context.getString(R.string.punto211));
        subItem21.setPunto2(context.getString(R.string.punto212));
        subItem21.setPunto3(context.getString(R.string.punto213));
        subItem21.setPunto4(context.getString(R.string.punto214));
        subItem21.setPunto5(context.getString(R.string.punto215));
        unaLista.add(subItem21);

        SubItem subItem22=new SubItem();
        subItem22.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem22.setId("2S 2");
        subItem22.setEnunciado(context.getString(R.string.enunciado22));
        subItem22.setPunto1(context.getString(R.string.punto221));
        subItem22.setPunto2(context.getString(R.string.punto222));
        subItem22.setPunto3(context.getString(R.string.punto223));
        subItem22.setPunto4(context.getString(R.string.punto224));
        subItem22.setPunto5(context.getString(R.string.punto225));
        unaLista.add(subItem22);

        SubItem subItem23=new SubItem();
        subItem23.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem23.setId("2S 3");
        subItem23.setEnunciado(context.getString(R.string.enunciado23));
        subItem23.setPunto1(context.getString(R.string.punto231));
        subItem23.setPunto2(context.getString(R.string.punto232));
        subItem23.setPunto3(context.getString(R.string.punto233));
        subItem23.setPunto4(context.getString(R.string.punto234));
        subItem23.setPunto5(context.getString(R.string.punto235));
        unaLista.add(subItem23);

        SubItem subItem24=new SubItem();
        subItem24.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem24.setId("2S 4");
        subItem24.setEnunciado(context.getString(R.string.enunciado24));
        subItem24.setPunto1(context.getString(R.string.punto241));
        subItem24.setPunto2(context.getString(R.string.punto242));
        subItem24.setPunto3(context.getString(R.string.punto243));
        subItem24.setPunto4(context.getString(R.string.punto244));
        subItem24.setPunto5(context.getString(R.string.punto245));
        unaLista.add(subItem24);

        SubItem subItem31=new SubItem();
        subItem31.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem31.setId("3S 1");
        subItem31.setEnunciado(context.getString(R.string.enunciado31));
        subItem31.setPunto1(context.getString(R.string.punto311));
        subItem31.setPunto2(context.getString(R.string.punto312));
        subItem31.setPunto3(context.getString(R.string.punto313));
        subItem31.setPunto4(context.getString(R.string.punto314));
        subItem31.setPunto5(context.getString(R.string.punto315));
        unaLista.add(subItem31);

        SubItem subItem32=new SubItem();
        subItem32.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem32.setId("3S 2");
        subItem32.setEnunciado(context.getString(R.string.enunciado32));
        subItem32.setPunto1(context.getString(R.string.punto321));
        subItem32.setPunto2(context.getString(R.string.punto322));
        subItem32.setPunto3(context.getString(R.string.punto323));
        subItem32.setPunto4(context.getString(R.string.punto324));
        subItem32.setPunto5(context.getString(R.string.punto325));
        unaLista.add(subItem32);

        SubItem subItem33=new SubItem();
        subItem33.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem33.setId("3S 3");
        subItem33.setEnunciado(context.getString(R.string.enunciado33));
        subItem33.setPunto1(context.getString(R.string.punto331));
        subItem33.setPunto2(context.getString(R.string.punto332));
        subItem33.setPunto3(context.getString(R.string.punto333));
        subItem33.setPunto4(context.getString(R.string.punto334));
        subItem33.setPunto5(context.getString(R.string.punto335));
        unaLista.add(subItem33);

        SubItem subItem34=new SubItem();
        subItem34.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem34.setId("3S 4");
        subItem34.setEnunciado(context.getString(R.string.enunciado34));
        subItem34.setPunto1(context.getString(R.string.punto341));
        subItem34.setPunto2(context.getString(R.string.punto342));
        subItem34.setPunto3(context.getString(R.string.punto343));
        subItem34.setPunto4(context.getString(R.string.punto344));
        subItem34.setPunto5(context.getString(R.string.punto345));
        unaLista.add(subItem34);


        SubItem subItem41=new SubItem();
        subItem41.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem41.setId("4S 1");
        subItem41.setEnunciado(context.getString(R.string.enunciado41));
        subItem41.setPunto1(context.getString(R.string.punto411));
        subItem41.setPunto2(context.getString(R.string.punto412));
        subItem41.setPunto3(context.getString(R.string.punto413));
        subItem41.setPunto4(context.getString(R.string.punto414));
        subItem41.setPunto5(context.getString(R.string.punto415));
        unaLista.add(subItem41);


        SubItem subItem42=new SubItem();
        subItem42.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem42.setId("4S 2");
        subItem42.setEnunciado(context.getString(R.string.enunciado42));
        subItem42.setPunto1(context.getString(R.string.punto421));
        subItem42.setPunto2(context.getString(R.string.punto422));
        subItem42.setPunto3(context.getString(R.string.punto423));
        subItem42.setPunto4(context.getString(R.string.punto424));
        subItem42.setPunto5(context.getString(R.string.punto425));
        unaLista.add(subItem42);

        SubItem subItem43=new SubItem();
        subItem43.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem43.setId("4S 3");
        subItem43.setEnunciado(context.getString(R.string.enunciado43));
        subItem43.setPunto1(context.getString(R.string.punto431));
        subItem43.setPunto2(context.getString(R.string.punto432));
        subItem43.setPunto3(context.getString(R.string.punto433));
        subItem43.setPunto4(context.getString(R.string.punto434));
        subItem43.setPunto5(context.getString(R.string.punto435));
        unaLista.add(subItem43);

        SubItem subItem44=new SubItem();
        subItem44.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem44.setId("4S 4");
        subItem44.setEnunciado(context.getString(R.string.enunciado44));
        subItem44.setPunto1(context.getString(R.string.punto441));
        subItem44.setPunto2(context.getString(R.string.punto442));
        subItem44.setPunto3(context.getString(R.string.punto443));
        subItem44.setPunto4(context.getString(R.string.punto444));
        subItem44.setPunto5(context.getString(R.string.punto445));
        unaLista.add(subItem44);

        SubItem subItem51=new SubItem();
        subItem51.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem51.setId("5S 1");
        subItem51.setEnunciado(context.getString(R.string.enunciado51));
        subItem51.setPunto1(context.getString(R.string.punto511));
        subItem51.setPunto2(context.getString(R.string.punto512));
        subItem51.setPunto3(context.getString(R.string.punto513));
        subItem51.setPunto4(context.getString(R.string.punto514));
        subItem51.setPunto5(context.getString(R.string.punto515));
        unaLista.add(subItem51);

        SubItem subItem52=new SubItem();
        subItem52.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem52.setId("5S 2");
        subItem52.setEnunciado(context.getString(R.string.enunciado52));
        subItem52.setPunto1(context.getString(R.string.punto521));
        subItem52.setPunto2(context.getString(R.string.punto522));
        subItem52.setPunto3(context.getString(R.string.punto523));
        subItem52.setPunto4(context.getString(R.string.punto524));
        subItem52.setPunto5(context.getString(R.string.punto525));
        unaLista.add(subItem52);

        SubItem subItem53=new SubItem();
        subItem53.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem53.setId("5S 3");
        subItem53.setEnunciado(context.getString(R.string.enunciado53));
        subItem53.setPunto1(context.getString(R.string.punto531));
        subItem53.setPunto2(context.getString(R.string.punto532));
        subItem53.setPunto3(context.getString(R.string.punto533));
        subItem53.setPunto4(context.getString(R.string.punto534));
        subItem53.setPunto5(context.getString(R.string.punto535));
        unaLista.add(subItem53);

        SubItem subItem54=new SubItem();
        subItem54.setAuditoria(ActivityAuditoria.idAuditoria);
        subItem54.setId("5S 4");
        subItem54.setEnunciado(context.getString(R.string.enunciado54));
        subItem54.setPunto1(context.getString(R.string.punto541));
        subItem54.setPunto2(context.getString(R.string.punto542));
        subItem54.setPunto3(context.getString(R.string.punto543));
        subItem54.setPunto4(context.getString(R.string.punto544));
        subItem54.setPunto5(context.getString(R.string.punto545));
        unaLista.add(subItem54);

        return unaLista;
    }

    public List<String>traerTitulos(){
        List<String>unaLista= new ArrayList<>();
        unaLista.add("Seiri 1");
        unaLista.add("Seiri 2");
        unaLista.add("Seiri 3");
        unaLista.add("Seiri 4");
        unaLista.add("Seiton 1");
        unaLista.add("Seiton 2");
        unaLista.add("Seiton 3");
        unaLista.add("Seiton 4");
        unaLista.add("Seiso 1");
        unaLista.add("Seiso 2");
        unaLista.add("Seiso 3");
        unaLista.add("Seiso 4");
        unaLista.add("Seiketsu 1");
        unaLista.add("Seiketsu 2");
        unaLista.add("Seiketsu 3");
        unaLista.add("Seiketsu 4");
        unaLista.add("Shitsuke 1");
        unaLista.add("Shitsuke 2");
        unaLista.add("Shitsuke 3");
        unaLista.add("Shitsuke 4");
        return unaLista;
    }

    public List<String> traerListaViewPager(){
        List<String>unaLista=new ArrayList<>();
        unaLista.add("My Audits");
        unaLista.add("Ranking");
        return unaLista;
    }
}
