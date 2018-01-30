package com.demo.nomad.controldeuso5s;

/**
 * Created by elmar on 25/1/2018.
 */

public class Estadisticas {

    private String cantidadAreasCreadas;
    private String cantidadAuditorias;
    private String reportesEnviados;
    private String cantidadFotosTomadas;
    private Apertura cantidadAperturas;

    public Estadisticas() {
    }

    public String getCantidadAreasCreadas() {
        return cantidadAreasCreadas;
    }

    public void setCantidadAreasCreadas(String cantidadAreasCreadas) {
        this.cantidadAreasCreadas = cantidadAreasCreadas;
    }

    public String getCantidadAuditorias() {
        return cantidadAuditorias;
    }

    public void setCantidadAuditorias(String cantidadAuditorias) {
        this.cantidadAuditorias = cantidadAuditorias;
    }

    public String getReportesEnviados() {
        return reportesEnviados;
    }

    public void setReportesEnviados(String reportesEnviados) {
        this.reportesEnviados = reportesEnviados;
    }

    public String getCantidadFotosTomadas() {
        return cantidadFotosTomadas;
    }

    public void setCantidadFotosTomadas(String cantidadFotosTomadas) {
        this.cantidadFotosTomadas = cantidadFotosTomadas;
    }

    public Apertura getCantidadAperturas() {
        return cantidadAperturas;
    }

    public void setCantidadAperturas(Apertura cantidadAperturas) {
        this.cantidadAperturas = cantidadAperturas;
    }
}
