package com.nomad.audit5s.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by elmar on 12/8/2017.
 */

public class Area extends RealmObject{
    @PrimaryKey
    private String idArea;
    private String NombreArea;
    private Foto fotoArea;

    public Area() {
    }

    public String getIdArea() {
        return idArea;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

    public String getNombreArea() {
        return NombreArea;
    }

    public void setNombreArea(String nombreArea) {
        NombreArea = nombreArea;
    }

    public Foto getFotoArea() {
        return fotoArea;
    }

    public void setFotoArea(Foto fotoArea) {
        this.fotoArea = fotoArea;
    }
}
