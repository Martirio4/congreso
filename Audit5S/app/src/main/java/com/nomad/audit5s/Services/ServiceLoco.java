package com.nomad.audit5s.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.nomad.audit5s.activities.ActivityAuditoria;
import com.nomad.audit5s.model.Auditoria;

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

        stopSelf();
    }
}