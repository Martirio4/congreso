package com.nomad.audit5s.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.text.SelectFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.nomad.audit5s.Adapter.AdapterPagerSubItems;
import com.nomad.audit5s.Controller.ControllerDatos;
import com.nomad.audit5s.Fragments.FragmentSubitem;
import com.nomad.audit5s.Model.SubItem;
import com.nomad.audit5s.R;

import java.nio.channels.Selector;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ActivityAuditoria extends AppCompatActivity {

    public static final String IDAUDITORIA ="IDAUDITORIA";
    public static final String AREA="AREA";
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private ViewPager pager;
    private AdapterPagerSubItems adapterPager;

    private String idAuditoria;
    private String areaAuditada;
    private String fechaAuditoria;

    private SubItem sub1;
    private SubItem sub2;
    private SubItem sub3;
    private SubItem sub4;
    private SubItem sub5;
    private SubItem sub6;
    private SubItem sub7;
    private SubItem sub8;
    private SubItem sub9;
    private SubItem sub10;
    private SubItem sub11;
    private SubItem sub12;
    private String Auditor;

    private ControllerDatos controllerDatos;
    private FloatingActionMenu fabMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditoria);

        sub1=new SubItem();
        sub2=new SubItem();
        sub3=new SubItem();
        sub4=new SubItem();
        sub5=new SubItem();
        sub6=new SubItem();
        sub7=new SubItem();
        sub8=new SubItem();
        sub9=new SubItem();
        sub10=new SubItem();
        sub11=new SubItem();
        sub12=new SubItem();

        Intent intent= getIntent();
        Bundle bundle= intent.getExtras();
        idAuditoria=bundle.getString(IDAUDITORIA);
        areaAuditada=bundle.getString(AREA);
        fechaAuditoria=determinarFecha();

        drawerLayout = (DrawerLayout) findViewById(R.id.elDrawer);
        navigationView = (NavigationView) findViewById(R.id.naview);
        pager=(ViewPager)findViewById(R.id.viewPagerAuditoria);

        fabMenu=(FloatingActionMenu)findViewById(R.id.fab_menu);






























//        SETEAR EL VIEWPAGER
        controllerDatos=new ControllerDatos(this);
        adapterPager=new AdapterPagerSubItems(getSupportFragmentManager());
        adapterPager.setListaSubItems(controllerDatos.traerSubItems());
        adapterPager.setUnaListaTitulos(controllerDatos.traerTitulos());
        pager.setAdapter(adapterPager);
        adapterPager.notifyDataSetChanged();

//        CAMBIAR LA FUENTE DE LOS TITUTLOS DEL DRAWER
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

//        TOGGLE PARA EL BOTON HAMBURGUESA
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.primary_light));
        }
        else{
            actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.primary_light));
        }

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

//        ACCION DE LOS BOTONES DEL DRAWER
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.item11:
                            pager.setCurrentItem(0);
                            break;
                        case R.id.item12:
                            pager.setCurrentItem(1);
                            break;
                        case R.id.item13:
                            pager.setCurrentItem(2);
                            break;
                        case R.id.item14:
                            pager.setCurrentItem(3);
                            break;
                        case R.id.item21:
                            pager.setCurrentItem(4);
                            break;
                        case R.id.item22:
                            pager.setCurrentItem(5);
                            break;
                        case R.id.item23:
                            pager.setCurrentItem(6);
                            break;
                        case R.id.item24:
                            pager.setCurrentItem(7);
                            break;
                        case R.id.item31:
                            pager.setCurrentItem(8);
                            break;
                        case R.id.item32:
                            pager.setCurrentItem(9);
                            break;
                        case R.id.item33:
                            pager.setCurrentItem(10);
                            break;
                        case R.id.item34:
                            pager.setCurrentItem(11);
                            break;

                    }

                drawerLayout.closeDrawers();
                return true;
            }
        });
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

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(ActivityAuditoria.this, "paginaSeleccionada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                
            }
        });


        updateTabTextColors();


    }


    private void updateTabTextColors(){
        LinearLayout tabsContainer=(LinearLayout)tabLayout.getChildAt(0);

        for(int i =4;i<8;i++){
            LinearLayout item = (LinearLayout) tabsContainer.getChildAt(i);
            TextView tv =(TextView)item.getChildAt(1);
            tv.setTextColor(ContextCompat.getColor(this, R.color.tile1));
        }

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

    public String determinarFecha(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date=sdf.format(cal.getTime());
        return date;
    }



}
