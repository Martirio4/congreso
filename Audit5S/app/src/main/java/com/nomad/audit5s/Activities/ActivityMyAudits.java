package com.nomad.audit5s.activities;

import android.content.Intent;
import android.graphics.Typeface;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.nomad.audit5s.adapter.AdapterPagerAudits;
import com.nomad.audit5s.controller.ControllerDatos;
import com.nomad.audit5s.fragments.FragmentMyAudits;
import com.nomad.audit5s.fragments.FragmentRanking;
import com.nomad.audit5s.model.Auditoria;
import com.nomad.audit5s.R;

public class ActivityMyAudits extends AppCompatActivity implements FragmentMyAudits.Graficable, FragmentRanking.Graficable {

    private ViewPager pager;
    private AdapterPagerAudits adapterPager;
    private TabLayout tabLayout;
    private ControllerDatos controllerDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_audits);
        pager=(ViewPager)findViewById(R.id.viewPagerMyAudits);
        controllerDatos=new ControllerDatos(this);
        adapterPager= new AdapterPagerAudits(getSupportFragmentManager(),controllerDatos.traerListaViewPager());
        pager.setAdapter(adapterPager);
        adapterPager.notifyDataSetChanged();

//        SETEAR EL TABLAYOUT
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutMyAudits);
        tabLayout.setupWithViewPager(pager);

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

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // Get a support ActionBar corresponding to this toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.marfil));

        Typeface robotoR = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        TextView unText=toolbar.findViewById(R.id.textoToolbar);
        unText.setTypeface(robotoR);
        unText.setTextColor(getResources().getColor(R.color.tile5));
        unText.setText(getResources().getString(R.string.misAuditorias));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void cargarFragmentMyAudits() {
        FragmentMyAudits fragment = new FragmentMyAudits();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedorMyAudits,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void GraficarAuditVieja(Auditoria unAuditoria) {
        Intent intent=new Intent(this, GraficosActivity.class);
        Bundle unBundle=new Bundle();
        unBundle.putString(GraficosActivity.AUDIT,unAuditoria.getIdAuditoria());
        unBundle.putString(GraficosActivity.ORIGEN, "myAudits");
        intent.putExtras(unBundle);
        startActivity(intent);
        this.finish();
    }
}
