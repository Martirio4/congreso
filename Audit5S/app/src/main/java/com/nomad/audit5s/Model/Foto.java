package com.nomad.audit5s.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by elmar on 11/8/2017.
 */

public class Foto extends RealmObject{

    @PrimaryKey
    private String rutaFoto;
    private String auditoria;
    private String subItem;
    private String comentario;

    public Foto() {
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(String auditoria) {
        this.auditoria = auditoria;
    }

    public String getSubItem() {
        return subItem;
    }

    public void setSubItem(String subItem) {
        this.subItem = subItem;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
