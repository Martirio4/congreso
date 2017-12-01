package com.nomad.audit5s.Activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nomad.audit5s.Controller.ControllerDatos;
import com.nomad.audit5s.Fragments.FragmentBarrasApiladas;
import com.nomad.audit5s.Fragments.FragmentManageAreas;
import com.nomad.audit5s.Fragments.FragmentRadar;
import com.nomad.audit5s.Model.Auditoria;
import com.nomad.audit5s.Model.AuditoriaFirebase;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import io.realm.Realm;
import io.realm.RealmResults;


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

    private PDFWriter writer;

    private File fotoComprimida;
    private Bitmap fotoOriginal;


    public static final int MARGEN_IZQUIERDO=30;
    public static final int SALTO_LINEA=18;
    public static final int SEPARACIONFOTOS=9;


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


        FragmentActivity unaActivity = (FragmentActivity) this;
        FragmentManager fragmentManager = (FragmentManager) unaActivity.getSupportFragmentManager();
        FragmentManageAreas fragmentRadar = (FragmentManageAreas) fragmentManager.findFragmentByTag("radar");

        calcularPuntajes();

        if (fragmentRadar != null && fragmentRadar.isVisible()) {

        }
        else{
            cargarGraficoRadar();
            cargarGraficoBarras();

            Realm realm=Realm.getDefaultInstance();
            Auditoria mAudit=realm.where(Auditoria.class)
                    .equalTo("idAuditoria",idAudit)
                    .findFirst();

        }





        
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
        fragmentTransaction.add(R.id.contenedorGraficos,graficoFragment,"radar");
        fragmentTransaction.commit();
    }

    public void cargarGraficoBarras(){
        FragmentBarrasApiladas fragmentBarrasApiladas= new FragmentBarrasApiladas();

        Bundle bundle=new Bundle();
        bundle.putDouble(FragmentBarrasApiladas.PROMEDIO3S, promedio5s);
        fragmentBarrasApiladas.setArguments(bundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contenedorGraficos,fragmentBarrasApiladas,"barras");
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

    /*
    public void enviarePDF(){

        writer = new PDFWriter(PaperSize.LETTER_WIDTH, PaperSize.LETTER_HEIGHT);
        ControllerDatos controllerDatos=new ControllerDatos(this);
        Realm realm= Realm.getDefaultInstance();
        Auditoria mAudit= realm.where(Auditoria.class)
                .equalTo("idAuditoria",idAudit)
                .findFirst();

        
        List<String>alistaSeiri=controllerDatos.traerSeiri();
        List<String>alistaSeiton=controllerDatos.traerSeiton();
        List<String>alistaSeiso=controllerDatos.traerSeiso();
        List<String>alistaSeiketsu=controllerDatos.traerSeiketsu();
        List<String>alistaShitsuke=controllerDatos.traerShitsuke();
        
        List<SubItem>unListaSeiri=new ArrayList<>();
        List<SubItem>unListaSeiton=new ArrayList<>();
        List<SubItem>unListaSeiso=new ArrayList<>();
        List<SubItem>unListaSeiketsu=new ArrayList<>();
        List<SubItem>unListaShitsuke=new ArrayList<>();
        
        for (SubItem sub:mAudit.getSubItems()
             ) {
            if (alistaSeiri.contains(sub.getId())){
                unListaSeiri.add(sub);
            }
            if (alistaSeiton.contains(sub.getId())){
                unListaSeiton.add(sub);
            }
            if (alistaSeiso.contains(sub.getId())){
                unListaSeiso.add(sub);
            }
            if (alistaSeiketsu.contains(sub.getId())){
                unListaSeiketsu.add(sub);
            }
            if (alistaShitsuke.contains(sub.getId())){
                unListaShitsuke.add(sub);
            }
        }
        //fuente titulo
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.HELVETICA, StandardFonts.WIN_ANSI_ENCODING);
        //escribir titulo
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT - MARGEN_IZQUIERDO,20,"5S Audit Report");
        //fuente fecha escribir fecga
        writer.addText(MARGEN_IZQUIERDO, PaperSize.LETTER_HEIGHT-(75),12,"Date: "+mAudit.getFechaAuditoria());


        armarPagina(unListaSeiri);
        writer.newPage();
        armarPagina(unListaSeiton);
        writer.newPage();
        armarPagina(unListaSeiso);
        writer.newPage();
        armarPagina(unListaSeiketsu);
        writer.newPage();
        armarPagina(unListaShitsuke);
        writer.newPage();
        cargarGraficos(mAudit);

        outputToFile("5S Report-"+mAudit.getAreaAuditada().getNombreArea()+"-"+mAudit.getFechaAuditoria()+".pdf", writer.asString(), "ISO-8859-1");
    }
    */





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
    
    /*
    public void armarPagina(List<SubItem> unaLista){

        Integer inicioFotos=85;
        Integer altoImagen=120;

        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.HELVETICA, StandardFonts.WIN_ANSI_ENCODING);
        writer.addText(PaperSize.LETTER_WIDTH-4*MARGEN_IZQUIERDO, PaperSize.LETTER_HEIGHT-(75),12,unaLista.get(0).getaQuePertenece());

        //linea separacion
        writer.addLine(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(85),PaperSize.LETTER_WIDTH-MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(85));
        
        //agrego primer subitem
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA),12,unaLista.get(0).getEnunciado());
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA+12),12,"Score: "+unaLista.get(0).getPuntuacion1().toString());
        Integer cantidadFotos=unaLista.get(0).getListaFotos().size();
        if (cantidadFotos>3){
            cantidadFotos=3;
        }

        for (int i=0;i<cantidadFotos;i++){

            Foto unaFoto=unaLista.get(0).getListaFotos().get(i);
            File unFile= new File(unaFoto.getRutaFoto());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(unFile.getAbsolutePath(),bmOptions);
            Bitmap bitmapScaled= Bitmap.createScaledBitmap(bitmap,120,120,false);
            writer.addImage(MARGEN_IZQUIERDO+(i*145),PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*2+altoImagen),bitmapScaled);
            writer.addText(MARGEN_IZQUIERDO+(i*145),PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*2+altoImagen+10),10,unaFoto.getComentario());

        }
        //agrego segundo subitem
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*3+altoImagen),12,unaLista.get(1).getEnunciado());
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*3+altoImagen+12),12,"Score: "+unaLista.get(1).getPuntuacion1().toString());
        cantidadFotos=unaLista.get(1).getListaFotos().size();
        if (cantidadFotos>3){
            cantidadFotos=3;
        }

        for (int i=0;i<cantidadFotos;i++){

            Foto unaFoto=unaLista.get(1).getListaFotos().get(i);
            File unFile= new File(unaFoto.getRutaFoto());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(unFile.getAbsolutePath(),bmOptions);
            Bitmap bitmapScaled= Bitmap.createScaledBitmap(bitmap,120,120,false);
            writer.addImage(MARGEN_IZQUIERDO+(i*145),PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*4+altoImagen*2),bitmapScaled);
            writer.addText(MARGEN_IZQUIERDO+(i*145),PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*4+altoImagen*2+10),10,unaFoto.getComentario());

        }
        //agrego tercer subitem
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*5+altoImagen*2),12,unaLista.get(2).getEnunciado());
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*5+altoImagen*2+12),12,"Score: "+unaLista.get(2).getPuntuacion1().toString());
        cantidadFotos=unaLista.get(2).getListaFotos().size();
        if (cantidadFotos>3){
            cantidadFotos=3;
        }

        for (int i=0;i<cantidadFotos;i++){

            Foto unaFoto=unaLista.get(2).getListaFotos().get(i);
            File unFile= new File(unaFoto.getRutaFoto());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(unFile.getAbsolutePath(),bmOptions);
            Bitmap bitmapScaled= Bitmap.createScaledBitmap(bitmap,120,120,false);
            writer.addImage(MARGEN_IZQUIERDO+(i*145),PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*6+altoImagen*3),bitmapScaled);
            writer.addText(MARGEN_IZQUIERDO+(i*145),PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*6+altoImagen*3+10),10,unaFoto.getComentario());

        }
        //agrego cuarto subitem
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*7+altoImagen*3),12,unaLista.get(3).getEnunciado());
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*7+altoImagen*3+12),12,"Score: "+unaLista.get(3).getPuntuacion1().toString());
        cantidadFotos=unaLista.get(3).getListaFotos().size();
        if (cantidadFotos>3){
            cantidadFotos=3;
        }

        for (int i=0;i<cantidadFotos;i++){

            Foto unaFoto=unaLista.get(3).getListaFotos().get(i);
            File unFile= new File(unaFoto.getRutaFoto());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(unFile.getAbsolutePath(),bmOptions);
            Bitmap bitmapScaled= Bitmap.createScaledBitmap(bitmap,120,120,false);
            writer.addImage(MARGEN_IZQUIERDO+(i*145),PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*8+altoImagen*4),bitmapScaled);
            writer.addText(MARGEN_IZQUIERDO+(i*145),PaperSize.LETTER_HEIGHT-(inicioFotos+SALTO_LINEA*8+altoImagen*4+10),10,unaFoto.getComentario());

        }
    }
    */
    public void outputToFile(String fileName, String pdfContent, String encoding) {
        if (existeDirectorio()) {
            String pathe = Environment.getExternalStorageDirectory().getAbsolutePath();

            File newFile = new File(pathe+File.separator + "nomad" +File.separator+ "audit5s" + File.separator+"audits"+File.separator+fileName);
            try {
                newFile.createNewFile();
                try {
                    FileOutputStream pdfFile = new FileOutputStream(newFile);
                    pdfFile.write(pdfContent.getBytes(encoding));
                    pdfFile.close();


                    Uri path = Uri.fromFile(newFile);
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    // set the type to 'email'
                    emailIntent.setType("vnd.android.cursor.dir/email");
                    String to[] = {""};
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                    // the attachment
                    emailIntent.putExtra(Intent.EXTRA_STREAM, path);
                    // the mail subject
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                } catch (FileNotFoundException e) {
                    Log.e("NOMAD/ERROR", "exception", e);
                }
            } catch (IOException e) {
                Log.e("NOMAD/ERROR", "exception", e);
            }
        }
        else{
            Toast.makeText(this, "Error, check permissions", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean existeDirectorio() {
        Boolean sePudo=true;
            File dir = new File(Environment.getExternalStorageDirectory()+ File.separator +"nomad"+ File.separator +"audit5s"+ File.separator + "audits");
            if (!dir.exists() || !dir.isDirectory()) {
                sePudo = dir.mkdirs();
            }
            return sePudo;
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public void cargarGraficos(Auditoria unAudit){
        writer.newPage();

        int cursorX=MARGEN_IZQUIERDO;
        int cursorY=792;
        //fuente titulo
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.HELVETICA, StandardFonts.WIN_ANSI_ENCODING);
        //escribir titulo
        writer.addText(cursorX,PaperSize.LETTER_HEIGHT - MARGEN_IZQUIERDO,20,"5S Audit Report");
        cursorY=cursorY-MARGEN_IZQUIERDO;
        cursorX=cursorX+MARGEN_IZQUIERDO;
        //fuente fecha escribir fecga

        cursorY=cursorY-SALTO_LINEA;


        //linea separacion
        writer.addLine( MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(65),PaperSize.LETTER_WIDTH-MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(65));
        cursorY=PaperSize.LETTER_HEIGHT-65;
        cursorY=cursorY-SALTO_LINEA;
        cursorX=MARGEN_IZQUIERDO;


        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.HELVETICA, StandardFonts.WIN_ANSI_ENCODING);
        writer.addText(PaperSize.LETTER_WIDTH-4*MARGEN_IZQUIERDO, PaperSize.LETTER_HEIGHT-(75),12,"Final result");



        //agrego puntaje final
        Locale locale = new Locale("en","US");
        NumberFormat format = NumberFormat.getPercentInstance(locale);
        String percentage1 = format.format(unAudit.getPuntajeFinal());
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(85+SALTO_LINEA),12,"Final score: "+percentage1);

        View rootView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        View v = rootView.findViewById(R.id.contenedorGraficos);

        Bitmap unBitmap=screenShot(v);
        Bitmap scaledBitmap= scaleSinRotar(unBitmap,250);
       // Bitmap SunBitmap=Bitmap.createScaledBitmap(unBitmap, 300,510,false);
        writer.addImage(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-85-SALTO_LINEA-scaledBitmap.getHeight()-SEPARACIONFOTOS,scaledBitmap);

    }




    public void enviarPDF(){

        ControllerDatos controllerDatos=new ControllerDatos(this);
        Realm realm= Realm.getDefaultInstance();
        Auditoria mAudit= realm.where(Auditoria.class)
                .equalTo("idAuditoria",idAudit)
                .findFirst();


        List<String>alistaSeiri=controllerDatos.traerSeiri();
        List<String>alistaSeiton=controllerDatos.traerSeiton();
        List<String>alistaSeiso=controllerDatos.traerSeiso();
        List<String>alistaSeiketsu=controllerDatos.traerSeiketsu();
        List<String>alistaShitsuke=controllerDatos.traerShitsuke();

        List<SubItem>unListaSeiri=new ArrayList<>();
        List<SubItem>unListaSeiton=new ArrayList<>();
        List<SubItem>unListaSeiso=new ArrayList<>();
        List<SubItem>unListaSeiketsu=new ArrayList<>();
        List<SubItem>unListaShitsuke=new ArrayList<>();

        for (SubItem sub:mAudit.getSubItems()
                ) {
            if (alistaSeiri.contains(sub.getId())){
                unListaSeiri.add(sub);
            }
            if (alistaSeiton.contains(sub.getId())){
                unListaSeiton.add(sub);
            }
            if (alistaSeiso.contains(sub.getId())){
                unListaSeiso.add(sub);
            }
            if (alistaSeiketsu.contains(sub.getId())){
                unListaSeiketsu.add(sub);
            }
            if (alistaShitsuke.contains(sub.getId())){
                unListaShitsuke.add(sub);
            }
        }

        writer = new PDFWriter(PaperSize.LETTER_WIDTH, PaperSize.LETTER_HEIGHT);
        crearPdfEse(unListaSeiri);
        writer.newPage();
        crearPdfEse(unListaSeiton);
        writer.newPage();
        crearPdfEse(unListaSeiso);
        writer.newPage();
        crearPdfEse(unListaSeiketsu);
        writer.newPage();
        crearPdfEse(unListaShitsuke);
        cargarGraficos(mAudit);

        outputToFile("5S Report-"+mAudit.getAreaAuditada().getNombreArea()+"-"+mAudit.getFechaAuditoria()+".pdf", writer.asString(), "ISO-8859-1");
    }



    /**
     * Scales the provided bitmap to have the height and width provided.
     * (Alternative method for scaling bitmaps
     * since Bitmap.createScaledBitmap(...) produces bad (blocky) quality bitmaps.)

     */



    public void crearPdfEse(List<SubItem> laLista){

        Integer cursorX=0;
        Integer cursorY=792;
        Integer renglonesFoto;

//        traigo la auditoria que quiero armar
        Realm realm= Realm.getDefaultInstance();
        Auditoria mAudit= realm.where(Auditoria.class)
                .equalTo("idAuditoria",idAudit)
                .findFirst();

//        declaro el pdwriter
        //fuente titulo
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.HELVETICA, StandardFonts.WIN_ANSI_ENCODING);
        //escribir titulo
        writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT - MARGEN_IZQUIERDO,20,"5S Audit Report");
        cursorY=cursorY-MARGEN_IZQUIERDO;
        cursorX=cursorX+MARGEN_IZQUIERDO;
        //fuente fecha escribir fecga
        writer.addText(cursorX, cursorY-SALTO_LINEA,12,"Date: "+mAudit.getFechaAuditoria());
        cursorY=cursorY-SALTO_LINEA;
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.HELVETICA, StandardFonts.WIN_ANSI_ENCODING);
        writer.addText(PaperSize.LETTER_WIDTH-4*MARGEN_IZQUIERDO, cursorY,12,laLista.get(0).getaQuePertenece());

        //linea separacion
        writer.addLine( MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(65),PaperSize.LETTER_WIDTH-MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(65));
        cursorY=PaperSize.LETTER_HEIGHT-65;
        cursorY=cursorY-SALTO_LINEA;
        cursorX=MARGEN_IZQUIERDO;
//        EMPIEZO A RECORRER LOS SUBITEMS
        recorrerSubitemLista(laLista,cursorX, cursorY,mAudit.getFechaAuditoria());
    }

    private void recorrerSubitemLista(List<SubItem>laLista,int x, int y,String fecha) {
        int cursorX=x;
        int cursorY=y;
        for (SubItem sub : laLista) {
            cursorX=MARGEN_IZQUIERDO;
            cursorY = cursorY-(SEPARACIONFOTOS/2);
            if (cursorY<2*MARGEN_IZQUIERDO) {
                writer.newPage();

                cursorX=0;
                cursorY=792;
                //fuente titulo
                writer.setFont(StandardFonts.SUBTYPE, StandardFonts.HELVETICA, StandardFonts.WIN_ANSI_ENCODING);
                //escribir titulo
                writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT - MARGEN_IZQUIERDO,20,"5S Audit Report");
                cursorY=cursorY-MARGEN_IZQUIERDO;
                cursorX=cursorX+MARGEN_IZQUIERDO;
                //fuente fecha escribir fecga
                writer.addText(cursorX, cursorY-SALTO_LINEA,12,"Date: "+fecha);
                cursorY=cursorY-SALTO_LINEA;
                writer.setFont(StandardFonts.SUBTYPE, StandardFonts.HELVETICA, StandardFonts.WIN_ANSI_ENCODING);
                writer.addText(PaperSize.LETTER_WIDTH-4*MARGEN_IZQUIERDO, cursorY,12,laLista.get(0).getaQuePertenece());

                //linea separacion
                writer.addLine( MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(65),PaperSize.LETTER_WIDTH-MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(65));
                cursorY=PaperSize.LETTER_HEIGHT-65;
                cursorY=cursorY-SALTO_LINEA;
                cursorX=MARGEN_IZQUIERDO;

            }

            writer.addText(cursorX,cursorY-SALTO_LINEA,12,sub.getEnunciado());
            cursorY=cursorY-SALTO_LINEA;
            writer.addText(cursorX,cursorY-SALTO_LINEA,12,"Score: "+sub.getPuntuacion1().toString());
            cursorY=cursorY-2*SALTO_LINEA;

            //renglonesFoto=Math.round(sub.getListaFotos().size()/3);
            for (Foto foto:sub.getListaFotos()
                    ) {
                Foto unaFoto=foto;
                File unFile= new File(unaFoto.getRutaFoto());
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inScaled=false;
                Bitmap rawBitmap = BitmapFactory.decodeFile(unFile.getAbsolutePath(),bmOptions);
                Bitmap bitmapScaled = scaleBitmap(rawBitmap,270);


//                    SI LA FOTO NO ENTRA EN LO QUE QUEDA DE PAGINA
                if (cursorY-bitmapScaled.getHeight()<SALTO_LINEA){
                    //si la imagen no entra armo una pagina nueva
                    //vuelvo a poner los titulos y demas
                    writer.newPage();
                    cursorX=0;
                    cursorY=792;
                    //fuente titulo
                    writer.setFont(StandardFonts.SUBTYPE, StandardFonts.HELVETICA, StandardFonts.WIN_ANSI_ENCODING);
                    //escribir titulo
                    writer.addText(MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT - MARGEN_IZQUIERDO,20,"5S Audit Report");
                    cursorY=cursorY-MARGEN_IZQUIERDO;
                    cursorX=cursorX+MARGEN_IZQUIERDO;
                    //fuente fecha escribir fecga
                    writer.addText(cursorX, cursorY-SALTO_LINEA,12,"Date: "+fecha);
                    cursorY=cursorY-SALTO_LINEA;
                    writer.setFont(StandardFonts.SUBTYPE, StandardFonts.HELVETICA, StandardFonts.WIN_ANSI_ENCODING);
                    writer.addText(PaperSize.LETTER_WIDTH-4*MARGEN_IZQUIERDO, cursorY,12,laLista.get(0).getaQuePertenece());

                    //linea separacion
                    writer.addLine( MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(65),PaperSize.LETTER_WIDTH-MARGEN_IZQUIERDO,PaperSize.LETTER_HEIGHT-(65));
                    cursorY=PaperSize.LETTER_HEIGHT-65;
                    cursorY=cursorY-SALTO_LINEA;
                    cursorX=MARGEN_IZQUIERDO;

                    //FINALMENTE AGREGO LA FOTO
                    writer.addImage(cursorX,cursorY-bitmapScaled.getHeight(),bitmapScaled);
                    writer.addText(cursorX,cursorY-bitmapScaled.getHeight()-SEPARACIONFOTOS,10,unaFoto.getComentario());
                    cursorX=cursorX+bitmapScaled.getWidth()+ SALTO_LINEA;
                    cursorY=cursorY-bitmapScaled.getHeight();
                }
                else{
//                        SI HAY LUGAR HACIA ABAJO CHEQUEO QUE ENTRE HORIZONTALMENTE
                    if (cursorX+bitmapScaled.getWidth()>612-MARGEN_IZQUIERDO){
//                            NO ENTRA
//                            LA PONGO ABAJO
                        cursorX=MARGEN_IZQUIERDO;
                        cursorY=cursorY-bitmapScaled.getHeight()-SEPARACIONFOTOS;
                        writer.addImage(cursorX,cursorY,bitmapScaled);
                        writer.addText(cursorX,cursorY-SEPARACIONFOTOS,10,unaFoto.getComentario());
                        cursorX=cursorX+bitmapScaled.getWidth();

                    }
                    else {
                        if (cursorX == MARGEN_IZQUIERDO) {
                            writer.addImage(cursorX, cursorY - bitmapScaled.getHeight(), bitmapScaled);
                            writer.addText(cursorX,cursorY-bitmapScaled.getHeight()-SEPARACIONFOTOS,10,unaFoto.getComentario());
                            cursorX = cursorX + bitmapScaled.getWidth();
                            cursorY = cursorY-bitmapScaled.getHeight();
                        }
                        else {
//                       ENTRA EN LA MISMA LINEA
                            writer.addImage(cursorX + SEPARACIONFOTOS, cursorY, bitmapScaled);
                            writer.addText(cursorX+SEPARACIONFOTOS,cursorY-SEPARACIONFOTOS,10,unaFoto.getComentario());
                            cursorX = cursorX + SEPARACIONFOTOS + bitmapScaled.getWidth();
                            cursorY=cursorY-SEPARACIONFOTOS;
                        }
                    }
                }
            }
        }
    }

    public static Bitmap scaleBitmap(Bitmap elBit, int newWidth) {
        Bitmap bitmap;
        Matrix rotMatrix = new Matrix();
        rotMatrix.postRotate(90);

        if (elBit.getWidth()>elBit.getHeight()){
            bitmap=elBit;
        }
        else{
            bitmap=Bitmap.createBitmap(elBit , 0, 0, elBit.getWidth(), elBit .getHeight(), rotMatrix, true);
        }
        Double proporcion=(bitmap.getWidth()*1.00/bitmap.getHeight()*1.00);

        int newHeight=(int)Math.round(newWidth / proporcion);
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);
        if (bitmap.getHeight()>bitmap.getWidth()){
            scaleMatrix.postRotate(90);
        }
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }
    public static Bitmap scaleSinRotar(Bitmap bitmap, int newWidth) {


        Double proporcion=(bitmap.getWidth()*1.00/bitmap.getHeight()*1.00);

        int newHeight=(int)Math.round(newWidth / proporcion);
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }
}

