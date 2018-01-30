package com.demo.nomad.controldeuso5s;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listaUsuarios=new ArrayList<>();


        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        DatabaseReference reference = mDatabase.child("usuarios");

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
                    listaUsuarios.add(unUsuario);
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
