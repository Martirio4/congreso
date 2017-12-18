package com.nomad.audit5s.activities;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Martirio on 19/07/2017.
 */

public class Aplicacion extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
