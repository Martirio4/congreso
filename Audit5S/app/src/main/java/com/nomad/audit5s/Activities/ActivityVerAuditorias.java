package com.nomad.audit5s.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.nomad.audit5s.Adapter.AdapterPagerVerAudits;
import com.nomad.audit5s.Controller.ControllerDatos;
import com.nomad.audit5s.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityVerAuditorias extends AppCompatActivity {

    public static final String AUDITORIA="AUDITORIA";
    public static String idAuditoria;

    private ViewPager pager;
    private AdapterPagerVerAudits adapterPager;
    private ControllerDatos controllerDatos;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_audit);
        // Get a support ActionBar corresponding to this toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.marfil));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        Intent unIntent=getIntent();
        Bundle bundle= unIntent.getExtras();

        idAuditoria=bundle.getString(AUDITORIA);

        pager=(ViewPager)findViewById(R.id.viewPagerVerAudit);

//        SETEAR EL VIEWPAGER
        controllerDatos=new ControllerDatos(this);
        adapterPager=new AdapterPagerVerAudits(getSupportFragmentManager());
        adapterPager.setListaEses(controllerDatos.traerListaVerAudit());
        adapterPager.setUnaListaTitulos(controllerDatos.traerTitulosVerAudit());
        pager.setAdapter(adapterPager);
        adapterPager.notifyDataSetChanged();


        //        SETEAR EL TABLAYOUT
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition(), true);
            }
        });





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
