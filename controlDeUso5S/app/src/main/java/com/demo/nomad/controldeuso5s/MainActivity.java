package com.demo.nomad.controldeuso5s;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = mDatabase.child("estadisticas");
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

                    DataSnapshot dsEstadistica=dataSnapshot;
                    Estadisticas estadisticas = dsEstadistica.getValue(Estadisticas.class);
                //    cantAperturas.setText(estadisticas.getCantidadAperturas().getVecesAbierta().toString());
                    cantAreas.setText(estadisticas.getCantidadAreasCreadas().toString());
                    cantAuditorias.setText(estadisticas.getCantidadAuditorias().toString());
                    cantReportes.setText(estadisticas.getReportesEnviados().toString());
                    cantFotos.setText(estadisticas.getCantidadFotosTomadas().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(MainActivity.this, "hola", Toast.LENGTH_SHORT).show();
            }
        };
        reference.addValueEventListener(postListener);

    }
}
