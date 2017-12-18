package com.nomad.audit5s.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by elmar on 11/8/2017.
 */

public class Auditoria extends RealmObject{

    @PrimaryKey
    private String idAuditoria;
    private RealmList<SubItem>subItems;
    private String fechaAuditoria;
    private String usuario;
    private Area areaAuditada;
    private Double puntajeFinal;

    public Auditoria() {
    }

    public String getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(String idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public RealmList<SubItem> getSubItems() {
        return subItems;
    }

    public void setSubItems(RealmList<SubItem> subItems) {
        this.subItems = subItems;
    }

    public String getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(String fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Area getAreaAuditada() {
        return areaAuditada;
    }

    public void setAreaAuditada(Area areaAuditada) {
        this.areaAuditada = areaAuditada;
    }

    public Double getPuntajeFinal() {
        return puntajeFinal;
    }

    public void setPuntajeFinal(Double puntajeFinal) {
        this.puntajeFinal = puntajeFinal;
    }
}
