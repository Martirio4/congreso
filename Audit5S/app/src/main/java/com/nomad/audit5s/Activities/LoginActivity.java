package com.nomad.audit5s.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.nomad.audit5s.R;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        irALanding();


    }
    public void irALanding(){
        Intent unIntent = new Intent(this, ActivityLanding.class);
        startActivity(unIntent);
    }
}
