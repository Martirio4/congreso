package com.nomad.audit5s.Model;

import io.realm.RealmObject;

/**
 * Created by elmar on 11/8/2017.
 */

public class Foto extends RealmObject{

    private String rutaFoto;

    public Foto() {
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }
}
