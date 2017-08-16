package com.nomad.audit5s.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nomad.audit5s.Controller.ControllerDatos;
import com.nomad.audit5s.Fragments.FragmentBarrasApiladas;
import com.nomad.audit5s.Fragments.FragmentRadar;
import com.nomad.audit5s.Model.SubItem;
import com.nomad.audit5s.R;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class GraficosActivity extends AppCompatActivity {

    public static final String AUDIT="AUDIT";
    public static final String AREA="AREA";
    private String idAudit;
    private Double promedioSeiri;
    private Double promedioSeiton;
    private Double promedioSeiso;
    private Double promedio3s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);
        promedioSeiso=0.0;
        promedioSeiton=0.0;
        promedioSeiri=0.0;
        promedio3s=0.0;
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        idAudit=bundle.getString(AUDIT);
        calcularPuntajes();

        cargarGraficoRadar();
        cargarGraficoBarras();

    }


    public void cargarGraficoRadar(){
        FragmentRadar graficoFragment= new FragmentRadar();
        Bundle bundle=new Bundle();
        bundle.putDouble(FragmentRadar.PUNJTAJE1, promedioSeiri);
        bundle.putDouble(FragmentRadar.PUNJTAJE2, promedioSeiton);
        bundle.putDouble(FragmentRadar.PUNJTAJE3, promedioSeiso);
        bundle.putDouble(FragmentRadar.PROMEDIO3S, promedio3s);
        bundle.putString(FragmentRadar.AREA,ActivityAuditoria.areaAuditada);

        graficoFragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedorGraficos,graficoFragment);
        fragmentTransaction.commit();
    }

    public void cargarGraficoBarras(){
        FragmentBarrasApiladas fragmentBarrasApiladas= new FragmentBarrasApiladas();

        Bundle bundle=new Bundle();
        bundle.putDouble(FragmentBarrasApiladas.PUNJTAJE1, promedioSeiri);
        bundle.putDouble(FragmentBarrasApiladas.PUNJTAJE2, promedioSeiton);
        bundle.putDouble(FragmentBarrasApiladas.PUNJTAJE3, promedioSeiso);
        bundle.putDouble(FragmentRadar.PROMEDIO3S, promedio3s);
        fragmentBarrasApiladas.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedorGraficos,fragmentBarrasApiladas);
       fragmentTransaction.commit();
    }

    public void calcularPuntajes(){
        ControllerDatos controllerDatos=new ControllerDatos(this);
        List<String>listaSeiri=controllerDatos.traerSeiri();
        List<String>listaSeiton=controllerDatos.traerSeiton();
        List<String>listaSeiso=controllerDatos.traerSeiso();
        Realm realm = Realm.getDefaultInstance();

        Integer sumaSeiri=0;
        Integer sumaSeiton =0;
        Integer sumaSeiso=0;

        for (String unString:listaSeiri
             ) {
            RealmResults<SubItem> resultSeiri = realm.where(SubItem.class)
                    .equalTo("pertenencia", idAudit+unString)
                    .findAll();
            sumaSeiri=sumaSeiri+resultSeiri.get(0).getPuntuacion1();
        }
        for (String unString:listaSeiton
                ) {
            RealmResults<SubItem> resultSeiton = realm.where(SubItem.class)
                    .equalTo("pertenencia", idAudit+unString)
                    .findAll();
            sumaSeiton=sumaSeiton+resultSeiton.get(0).getPuntuacion1();
        }
        for (String unString:listaSeiso
                ) {
            RealmResults<SubItem> resultSeiso = realm.where(SubItem.class)
                    .equalTo("pertenencia", idAudit+unString)
                    .findAll();
            sumaSeiso=sumaSeiso+resultSeiso.get(0).getPuntuacion1();
        }
        promedioSeiri=((sumaSeiri/4.0)/5.0);
        promedioSeiton=((sumaSeiton /4.0)/5.0);
        promedioSeiso=((sumaSeiso/4.0)/5.0);
        promedio3s=(promedioSeiso+promedioSeiri+promedioSeiton)/3.0;


    }
}
