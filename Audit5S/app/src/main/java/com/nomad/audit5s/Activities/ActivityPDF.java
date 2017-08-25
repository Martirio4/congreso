package com.nomad.audit5s.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nomad.audit5s.Model.Auditoria;
import com.nomad.audit5s.Model.Foto;
import com.nomad.audit5s.R;
import com.nomad.mylibrary.PDFWriter;
import com.nomad.mylibrary.PaperSize;
import com.nomad.mylibrary.StandardFonts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.realm.Realm;

public class ActivityPDF extends AppCompatActivity {

    public static final String IDAAUDITORIA="IDAUDITORIA";
    private String idAuditoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Intent unIntent= getIntent();
        Bundle unBundle = new Bundle();
        unBundle=unIntent.getExtras();

        idAuditoria=unBundle.getString(IDAAUDITORIA);

    }
    public void outputToFile(String fileName, String pdfContent, String encoding) {
        File newFile = new File("/storage/emulated/0/Android/data/com.nomad.audit5s/cache/EasyImage" + "/" + fileName);
        try {
            newFile.createNewFile();
            try {
                FileOutputStream pdfFile = new FileOutputStream(newFile);
                pdfFile.write(pdfContent.getBytes(encoding));
                pdfFile.close();
            } catch(FileNotFoundException e) {
                // ...
            }
        } catch(IOException e) {
            // ...
        }
    }

    public void generarInformePDF(){
        Realm realm= Realm.getDefaultInstance();
        Auditoria mAudit= realm.where(Auditoria.class)
                .equalTo("idAuditoria",idAuditoria)
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
}
