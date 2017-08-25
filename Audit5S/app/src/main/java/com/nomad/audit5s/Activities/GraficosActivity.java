package com.nomad.audit5s.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.nomad.audit5s.Controller.ControllerDatos;
import com.nomad.audit5s.Fragments.FragmentBarrasApiladas;
import com.nomad.audit5s.Fragments.FragmentRadar;
import com.nomad.audit5s.Model.Auditoria;
import com.nomad.audit5s.Model.Foto;
import com.nomad.audit5s.Model.SubItem;
import com.nomad.audit5s.R;
import com.nomad.mylibrary.PDFWriter;
import com.nomad.mylibrary.PaperSize;
import com.nomad.mylibrary.StandardFonts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class GraficosActivity extends AppCompatActivity {

    public static final String AUDIT="AUDIT";
    public static final String AREA="AREA";
    public static final String ORIGEN="ORIGEN";

    private String origenIntent;
    private String idAudit;
    private String areaAuditada;
    private Double promedioSeiri;
    private Double promedioSeiton;
    private Double promedioSeiso;
    private Double promedio5s;
    
    private FloatingActionMenu fabMenuGraficos;
    private FloatingActionButton fabGenerarPDF;
    private FloatingActionButton fabQuit;

    private ProgressBar progressBar;
    private Double promedioSeiketsu;
    private Double promedioShitsuke;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        promedioSeiso=0.0;
        promedioSeiton=0.0;
        promedioSeiri=0.0;
        promedio5s =0.0;
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        idAudit=bundle.getString(AUDIT);
        origenIntent=bundle.getString(ORIGEN);
        calcularPuntajes();
        cargarGraficoRadar();
        cargarGraficoBarras();
        
        fabMenuGraficos= (FloatingActionMenu) findViewById(R.id.menuSalida);

        fabMenuGraficos.setMenuButtonColorNormal(ContextCompat.getColor(this,R.color.colorAccent));



        fabGenerarPDF = new FloatingActionButton(this);
        fabGenerarPDF.setColorNormal(ContextCompat.getColor(this, R.color.tile3));
        fabGenerarPDF.setButtonSize(FloatingActionButton.SIZE_MINI);
        fabGenerarPDF.setLabelText(getString(R.string.generarPDF));
        fabGenerarPDF.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
        fabMenuGraficos.addMenuButton(fabGenerarPDF);

        fabGenerarPDF.setLabelColors(ContextCompat.getColor(this, R.color.tile3),
                ContextCompat.getColor(this, R.color.light_grey),
                ContextCompat.getColor(this, R.color.white_transparent));
        fabGenerarPDF.setLabelTextColor(ContextCompat.getColor(this, R.color.black));

        fabGenerarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenuGraficos.close(true);
                new EnviarPDF().execute();
            }
        });

        fabQuit = new FloatingActionButton(this);
        fabQuit.setColorNormal(ContextCompat.getColor(this, R.color.tile3));
        fabQuit.setButtonSize(FloatingActionButton.SIZE_MINI);
        fabQuit.setLabelText(getString(R.string.quit));
        fabQuit.setImageResource(R.drawable.ic_exit_to_app_black_24dp);
        fabMenuGraficos.addMenuButton(fabQuit);

        fabQuit.setLabelColors(ContextCompat.getColor(this, R.color.tile3),
                ContextCompat.getColor(this, R.color.light_grey),
                ContextCompat.getColor(this, R.color.white_transparent));
        fabQuit.setLabelTextColor(ContextCompat.getColor(this, R.color.black));

        fabQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenuGraficos.close(true);
               Intent intent=new Intent(v.getContext(),ActivityLanding.class);
                startActivity(intent);
                GraficosActivity.this.finish();
            }
        });



    }


    public void cargarGraficoRadar(){
        FragmentRadar graficoFragment= new FragmentRadar();
        Bundle bundle=new Bundle();
        bundle.putDouble(FragmentRadar.PUNJTAJE1, promedioSeiri);
        bundle.putDouble(FragmentRadar.PUNJTAJE2, promedioSeiton);
        bundle.putDouble(FragmentRadar.PUNJTAJE3, promedioSeiso);
        bundle.putDouble(FragmentRadar.PUNJTAJE4, promedioSeiketsu);
        bundle.putDouble(FragmentRadar.PUNJTAJE5, promedioShitsuke);
        bundle.putString(FragmentRadar.AREA,areaAuditada);

        graficoFragment.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedorGraficos,graficoFragment);
        fragmentTransaction.commit();
    }

    public void cargarGraficoBarras(){
        FragmentBarrasApiladas fragmentBarrasApiladas= new FragmentBarrasApiladas();

        Bundle bundle=new Bundle();
        bundle.putDouble(FragmentBarrasApiladas.PROMEDIO3S, promedio5s);
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
        List<String>listaSeiketsu=controllerDatos.traerSeiketsu();
        List<String>listaShitsuke=controllerDatos.traerShitsuke();
        Realm realm = Realm.getDefaultInstance();

        Auditoria mAudit=realm.where(Auditoria.class)
                .equalTo("idAuditoria",idAudit)
                .findFirst();
        areaAuditada=mAudit.getAreaAuditada().getNombreArea();

        Integer sumaSeiri=0;
        Integer sumaSeiton =0;
        Integer sumaSeiso=0;
        Integer sumaSeiketsu=0;
        Integer sumaShitsuke=0;

        for (String unString:listaSeiri
             ) {
            SubItem resultSeiri = realm.where(SubItem.class)
                    .equalTo("pertenencia", idAudit+unString)
                    .findFirst();
            if (resultSeiri.getPuntuacion1()!=null) {
                sumaSeiri = sumaSeiri + resultSeiri.getPuntuacion1();
            }
        }
        for (String unString:listaSeiton
                ) {
            SubItem resultSeiton = realm.where(SubItem.class)
                    .equalTo("pertenencia", idAudit+unString)
                    .findFirst();
            if (resultSeiton.getPuntuacion1()!=null) {
                sumaSeiton = sumaSeiton + resultSeiton.getPuntuacion1();
            }
        }
        for (String unString:listaSeiso
                ) {
            SubItem resultSeiso = realm.where(SubItem.class)
                    .equalTo("pertenencia", idAudit+unString)
                    .findFirst();
            if (resultSeiso.getPuntuacion1()!=null) {
                sumaSeiso = sumaSeiso + resultSeiso.getPuntuacion1();
            }
        }
        for (String unString:listaSeiketsu
                ) {
            SubItem resultSeiketsu = realm.where(SubItem.class)
                    .equalTo("pertenencia", idAudit+unString)
                    .findFirst();
            if (resultSeiketsu.getPuntuacion1()!=null) {
                sumaSeiketsu = sumaSeiketsu + resultSeiketsu.getPuntuacion1();
            }
        }
        for (String unString:listaShitsuke
                ) {
            SubItem resultShitsuke = realm.where(SubItem.class)
                    .equalTo("pertenencia", idAudit+unString)
                    .findFirst();
            if (resultShitsuke.getPuntuacion1()!=null) {
                sumaShitsuke = sumaShitsuke + resultShitsuke.getPuntuacion1();
            }
        }
        promedioSeiri=((sumaSeiri/4.0)/5.0);
        promedioSeiton=((sumaSeiton /4.0)/5.0);
        promedioSeiso=((sumaSeiso/4.0)/5.0);
        promedioSeiketsu=((sumaSeiketsu/4.0)/5.0);
        promedioShitsuke=((sumaShitsuke/4.0)/5.0);

        promedio5s =(promedioSeiso+promedioSeiri+promedioSeiton+promedioSeiketsu+promedioShitsuke)/5.0;

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Auditoria mAudit = realm.where(Auditoria.class)
                        .equalTo("idAuditoria",idAudit)
                        .findFirst();
                mAudit.setPuntajeFinal(promedio5s);
            }

        });


    }

    @Override
    public void onBackPressed() {
        if (origenIntent.equals("myAudits")){
            Intent unIntent= new Intent(this, ActivityMyAudits.class);
            startActivity(unIntent);
            GraficosActivity.this.finish();
        }
        else {
            super.onBackPressed();
            this.finish();
        }
    }

    public void enviarPDF(){
        Realm realm= Realm.getDefaultInstance();
        Auditoria mAudit= realm.where(Auditoria.class)
                .equalTo("idAuditoria",idAudit)
                .findFirst();

        PDFWriter writer = new PDFWriter(PaperSize.LETTER_WIDTH, PaperSize.LETTER_HEIGHT);
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);
        writer.addText(25,280,20,"Hola primeraPrueba");
        Foto unaFoto=mAudit.getSubItems().get(0).getListaFotos().get(0);
        File unFile= new File(unaFoto.getRutaFoto());
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(unFile.getAbsolutePath(),bmOptions);
        writer.addImage(25,250,bitmap);
        writer.newPage();


        outputToFile("MyFirstReport.pdf", writer.asString(), "ISO-8859-1");
    }
    public void outputToFile(String fileName, String pdfContent, String encoding) {
        File newFile = new File("/storage/emulated/0/Android/data/com.nomad.audit5s/cache/EasyImage" + "/" + fileName);
        try {
            newFile.createNewFile();
            try {
                FileOutputStream pdfFile = new FileOutputStream(newFile);
                pdfFile.write(pdfContent.getBytes(encoding));
                pdfFile.close();


                ArrayList<Uri> uris = new ArrayList<>();
                Uri uri = Uri.parse("file://" + newFile.getAbsolutePath());
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "5S Report");
                List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(emailIntent, 0);
                List<LabeledIntent> intents = new ArrayList<>();
                for (ResolveInfo info : resolveInfos) {
                    Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    intent.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "5S Report");
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris); //ArrayList<Uri> of attachment Uri's
                    intents.add(new LabeledIntent(intent, info.activityInfo.packageName, info.loadLabel(getPackageManager()), info.icon));
                }
                Intent chooser = Intent.createChooser(intents.remove(intents.size() - 1), "Send PDF report");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toArray(new LabeledIntent[intents.size()]));


                try {
                    startActivityForResult(chooser,1);
                } catch (Exception e) {
                    Toast.makeText(this, "Error: Cannot open or share created PDF report.", Toast.LENGTH_SHORT).show();
                }

            } catch(FileNotFoundException e) {
                // ...
            }
        } catch(IOException e) {
            // ...
        }
    }

    private class EnviarPDF extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            enviarPDF();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();

        }
    }
}

