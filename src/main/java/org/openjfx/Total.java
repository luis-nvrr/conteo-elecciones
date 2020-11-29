package org.openjfx;


public class Total {
    private String codigoAgrupacion;
    private String nombreAgrupacion;
    private int total;

    public Total(String codigoAgrupacion, String nombreAgrupacion, int total) {
        this.codigoAgrupacion = codigoAgrupacion;
        this.nombreAgrupacion = nombreAgrupacion;
        this.total = total;
    }

    public String getCodigoAgrupacion() {
        return codigoAgrupacion;
    }

    public String getNombreAgrupacion() {
        return nombreAgrupacion;
    }

    public int getTotal() {
        return total;
    }
}
