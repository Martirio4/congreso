package com.nomad.audit5s.utils;

import android.os.Environment;

/**
 * Created by elmar on 15/1/2018.
 */

public class FuncionesPublicas {

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
