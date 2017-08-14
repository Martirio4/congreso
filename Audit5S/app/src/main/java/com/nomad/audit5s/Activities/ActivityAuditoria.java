package com.nomad.audit5s.Activities;

import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nomad.audit5s.Adapter.AdapterPagerSubItems;
import com.nomad.audit5s.Controller.ControllerDatos;
import com.nomad.audit5s.R;


public class ActivityAuditoria extends AppCompatActivity {

    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private ViewPager pager;
    private AdapterPagerSubItems adapterPager;

    public static final String AREA="area";
    private String areaAuditada;
    private ControllerDatos controllerDatos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditoria);
        drawerLayout = (DrawerLayout) findViewById(R.id.elDrawer);
        navigationView = (NavigationView) findViewById(R.id.naview);
        pager=(ViewPager)findViewById(R.id.viewPagerAuditoria);
        adapterPager=new AdapterPagerSubItems(getSupportFragmentManager());
        pager.setAdapter(adapterPager);
        controllerDatos=new ControllerDatos(this);
        adapterPager.setListaSubItems(controllerDatos.traerSubItems());
        adapterPager.notifyDataSetChanged();

        Menu menu = navigationView.getMenu();

        MenuItem tools= menu.findItem(R.id.titulo1s);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.tituloNav), 0, s.length(), 0);
        tools.setTitle(s);

        MenuItem tools1= menu.findItem(R.id.titulo2s);
        SpannableString s1 = new SpannableString(tools1.getTitle());
        s1.setSpan(new TextAppearanceSpan(this, R.style.tituloNav), 0, s1.length(), 0);
        tools1.setTitle(s1);

        MenuItem tools2= menu.findItem(R.id.titulo3s);
        SpannableString s2 = new SpannableString(tools2.getTitle());
        s2.setSpan(new TextAppearanceSpan(this, R.style.tituloNav), 0, s2.length(), 0);
        tools2.setTitle(s2);

        // Get a support ActionBar corresponding to this toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        View headerLayout = navigationView.getHeaderView(0);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.primary_light));
        }
        else{
            actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.primary_light));
        }

        drawerLayout.addDrawerListener(actionBarDrawerToggle);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }



}
