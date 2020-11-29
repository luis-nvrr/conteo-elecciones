package org.openjfx.Negocio;

public class Agrupacion {
    private int codigoAgrupacion;
    private String nombreAgrupacion;

    public Agrupacion(int codigoAgrupacion, String nombreAgrupacion){
        this.codigoAgrupacion = codigoAgrupacion;
        this.nombreAgrupacion = nombreAgrupacion;
    }

    public int getCodigoAgrupacion() {
        return codigoAgrupacion;
    }

    public String getNombreAgrupacion() {
        return nombreAgrupacion;
    }
}
