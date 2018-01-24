package com.nomad.audit5s.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nomad.audit5s.activities.ActivityAuditoria;
import com.nomad.audit5s.model.Auditoria;
import com.nomad.audit5s.utils.FuncionesPublicas;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;


public class ServiceLoco extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ClearFromRecentService", "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");

//        Realm realm = Realm.getDefaultInstance();
//        final Auditoria mAuditDelete=realm.where(Auditoria.class)
//                .equalTo("idAuditoria", ActivityAuditoria.idAuditoria)
//                .findFirst();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                mAuditDelete.deleteFromRealm();
//            }
//        });
        //GUARDAR EN SHARED
        SharedPreferences config =getSharedPreferences("prefs",0);
        Integer cantidadUsos = config.getInt("cantidadUsos",0);

        SharedPreferences.Editor editor = config.edit();
        editor.putInt("cantidadUsos",cantidadUsos+1);
        editor.commit();

        //GUARDAR EN FIREBASE
        stopSelf();
    }
}