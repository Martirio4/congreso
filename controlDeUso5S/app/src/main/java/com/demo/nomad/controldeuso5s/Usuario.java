package com.demo.nomad.controldeuso5s;

/**
 * Created by elmar on 25/1/2018.
 */

public class Usuario {
    private Datos datos;
    private Estadisticas estadisticas;

    public Usuario() {
    }

    public Datos getDatos() {
        return datos;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Estadisticas estadisticas) {
        this.estadisticas = estadisticas;
    }
}
