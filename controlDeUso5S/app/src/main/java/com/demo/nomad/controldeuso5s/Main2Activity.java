package com.demo.nomad.controldeuso5s;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView recycler;
    private AdapterUsuarios adapterUsuarios;
    private ArrayList<Usuario> listaUsuarios;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listaUsuarios=new ArrayList<>();


        final DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        reference = mDatabase.child("usuarios");

        recycler=findViewById(R.id.recyclerUsuarios);
        adapterUsuarios= new AdapterUsuarios();
        adapterUsuarios.setContext(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(linearLayoutManager);
        adapterUsuarios.setListaAuditsOriginales(listaUsuarios);
        recycler.setAdapter(adapterUsuarios);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot usuariosSnap: dataSnapshot.getChildren()) {
                    Usuario unUsuario=usuariosSnap.getValue(Usuario.class);

                    if (unUsuario.getEstadisticas()!=null){
                        String cantidadAreasStr=unUsuario.getEstadisticas().getCantidadAreasCreadas();
                        if (cantidadAreasStr!=null) {
                            Integer cantidadAreasInt=Integer.parseInt(cantidadAreasStr);
                            if (cantidadAreasInt>=1){
                                listaUsuarios.add(unUsuario);
                            }
                        }
                    }
                }
               adapterUsuarios.setListaAuditsOriginales(listaUsuarios);
               adapterUsuarios.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}
