package com.nomad.audit5s.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by elmar on 11/8/2017.
 */

public class SubItem extends RealmObject {
    private String id;
    private String enunciado;
    private String punto1;
    private String punto2;
    private String punto3;
    private String punto4;
    private String punto5;
    private String pertenencia;

    private Integer puntuacion1;


    private RealmList<Foto> listaFotos;
    private RealmList<RealmString> listaComments;

    public SubItem() {
    }

    public String getPertenencia() {
        return pertenencia;
    }

    public void setPertenencia(String pertenencia) {
        this.pertenencia = pertenencia;
    }

    public String getPunto5() {
        return punto5;
    }

    public void setPunto5(String punto5) {
        this.punto5 = punto5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getPunto1() {
        return punto1;
    }

    public void setPunto1(String punto1) {
        this.punto1 = punto1;
    }

    public String getPunto2() {
        return punto2;
    }

    public void setPunto2(String punto2) {
        this.punto2 = punto2;
    }

    public String getPunto3() {
        return punto3;
    }

    public void setPunto3(String punto3) {
        this.punto3 = punto3;
    }

    public String getPunto4() {
        return punto4;
    }

    public void setPunto4(String punto4) {
        this.punto4 = punto4;
    }

    public Integer getPuntuacion1() {
        return puntuacion1;
    }

    public void setPuntuacion1(Integer puntuacion1) {
        this.puntuacion1 = puntuacion1;
    }

    public RealmList<Foto> getListaFotos() {
        return listaFotos;
    }

    public void setListaFotos(RealmList<Foto> listaFotos) {
        this.listaFotos = listaFotos;
    }

    public RealmList<RealmString> getListaComments() {
        return listaComments;
    }

    public void setListaComments(RealmList<RealmString> listaComments) {
        this.listaComments = listaComments;
    }
}
