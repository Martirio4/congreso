package com.nomad.audit5s.activities;

import android.content.Intent;
import android.graphics.Typeface;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.nomad.audit5s.adapter.AdapterPagerVerAudits;
import com.nomad.audit5s.controller.ControllerDatos;
import com.nomad.audit5s.R;

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

        Typeface robotoR = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        TextView unText=toolbar.findViewById(R.id.textoToolbar);
        unText.setTypeface(robotoR);
        unText.setTextColor(getResources().getColor(R.color.tile5));
        unText.setText(getResources().getString(R.string.detalleAuditoria));


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        Intent unIntent=getIntent();
        Bundle bundle= unIntent.getExtras();

        idAuditoria=bundle.getString(AUDITORIA);

        pager= findViewById(R.id.viewPagerVerAudit);

//        SETEAR EL VIEWPAGER
        controllerDatos=new ControllerDatos(this);
        adapterPager=new AdapterPagerVerAudits(getSupportFragmentManager());
        adapterPager.setListaEses(controllerDatos.traerListaVerAudit());
        adapterPager.setUnaListaTitulos(controllerDatos.traerTitulosVerAudit());
        pager.setAdapter(adapterPager);
        adapterPager.notifyDataSetChanged();


        //        SETEAR EL TABLAYOUT
        tabLayout = findViewById(R.id.tabLayout);
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
