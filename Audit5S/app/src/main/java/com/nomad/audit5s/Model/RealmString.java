package com.nomad.audit5s.Model;

import io.realm.RealmObject;

/**
 * Created by elmar on 11/8/2017.
 */

public class RealmString extends RealmObject{
    private String string;

    public RealmString() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
