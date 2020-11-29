package org.openjfx.Negocio;
import org.openjfx.Estructuras.TSBHashtable;

import java.util.Collection;

public class Escrutinio {
    private Region pais;
    private TSBHashtable<Integer, Agrupacion> agrupaciones;

    public Escrutinio(){
        this.pais = new Region("00000000", "ARGENTINA");
        this.agrupaciones = new TSBHashtable<Integer, Agrupacion>();
    }

    public void agregarRegion(String codigoRegion, String nombreRegion){
        pais.agregarRegion(codigoRegion, nombreRegion);
    }

    public void agregarAgrupacion(int codigoAgrupacion, String nombreAgrupacion){
        Agrupacion agrupacion = new Agrupacion(codigoAgrupacion, nombreAgrupacion);
        this.agrupaciones.put(codigoAgrupacion, agrupacion);
    }

    public void agregarVoto(String codigoRegiones, String codigoMesa, int codigoAgrupacion, int votosAgrupacion){
        pais.agregarVoto(codigoRegiones, codigoMesa, codigoAgrupacion, votosAgrupacion);
    }

    public Collection<Region> getDistritos(){
        return this.pais.getRegiones();
    }

    public TSBHashtable<Integer, Agrupacion> getAgrupaciones(){
        return this.agrupaciones;
    }

}
