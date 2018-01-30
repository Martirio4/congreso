package com.demo.nomad.controldeuso5s;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView cantAperturas;
    private TextView cantAuditorias;
    private TextView cantAreas;
    private TextView cantFotos;
    private TextView cantReportes;
    private Button boton;

    private ProgressBar progressBar;

    private Integer contadorAuditorias=0;
    private Integer contadorAreas=0;
    private Integer contadorReportes=0;
    private Integer contadorFotos=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);

        boton= findViewById(R.id.botonPorUsuario);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Main2Activity.class);
               startActivity(intent);
            }
        });


        //---leer cantidad de auditorias---//

        cantAperturas = findViewById(R.id.cantApertura);
        cantAreas = findViewById(R.id.cantAreas);
        cantAuditorias = findViewById(R.id.cantAuditorias);
        cantFotos = findViewById(R.id.cantfotos);
        cantReportes = findViewById(R.id.cantReportes);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

               new CargarDatos().execute(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        };

        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = mDatabase.child("usuarios");
        reference.addValueEventListener(postListener);



    }
    private class CargarDatos extends AsyncTask<DataSnapshot, Void, Void> {

        @Override
        protected Void doInBackground(DataSnapshot... args) {

            DataSnapshot dataSnapshot= args[0];

            for (DataSnapshot usuariosSnap: dataSnapshot.getChildren()) {
                Usuario unUsuario=usuariosSnap.getValue(Usuario.class);
                if (unUsuario.getEstadisticas()!=null){
                    if (unUsuario.getEstadisticas().getCantidadAreasCreadas()!=null && Integer.parseInt(unUsuario.getEstadisticas().getCantidadAreasCreadas())>=1){
                        contadorAreas = contadorAreas + Integer.parseInt(unUsuario.getEstadisticas().getCantidadAreasCreadas());
                    }
                    if (unUsuario.getEstadisticas().getCantidadAuditorias()!=null && Integer.parseInt(unUsuario.getEstadisticas().getCantidadAuditorias())>=1){
                        contadorAuditorias = contadorAuditorias + Integer.parseInt(unUsuario.getEstadisticas().getCantidadAuditorias());
                    }
                    if (unUsuario.getEstadisticas().getReportesEnviados()!=null && Integer.parseInt(unUsuario.getEstadisticas().getReportesEnviados())>=1){
                        contadorReportes = contadorReportes + Integer.parseInt(unUsuario.getEstadisticas().getReportesEnviados());
                    }
                    if (unUsuario.getEstadisticas().getCantidadFotosTomadas()!=null && Integer.parseInt(unUsuario.getEstadisticas().getCantidadFotosTomadas())>=1){
                        contadorFotos = contadorFotos + Integer.parseInt(unUsuario.getEstadisticas().getCantidadFotosTomadas());
                    }
                }
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            cantAreas.setText(contadorAreas.toString());
            cantAuditorias.setText(contadorAuditorias.toString());
            cantReportes.setText(contadorReportes.toString());
            cantFotos.setText(contadorFotos.toString());
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();

        }
    }
}
