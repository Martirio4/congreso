package com.nomad.audit5s.model;

import io.realm.RealmObject;

/**
 * Created by elmar on 11/8/2017.
 */

public class RealmString extends RealmObject{
    private String string;
    private String auditoria;
    private String subItem;

    public RealmString() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
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
}
