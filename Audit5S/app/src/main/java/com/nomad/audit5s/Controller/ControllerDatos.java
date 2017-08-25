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
        return unaLista;
    }

    public List<String> traerListaViewPager(){
        List<String>unaLista=new ArrayList<>();
        unaLista.add("My Audits");
        unaLista.add("Ranking");
        return unaLista;
    }
}
