package com.nomad.audit5s.Model;

import java.util.List;



/**
 * Created by elmar on 11/8/2017.
 */

public class AuditoriaFirebase {
    
    private String idAuditoria;
    private List<SubItem> subItems;
    private String fechaAuditoria;
    private String usuario;
    private Area areaAuditada;
    private Double puntajeFinal;

    public AuditoriaFirebase() {
    }

    public String getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(String idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public List<SubItem> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<SubItem> subItems) {
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
