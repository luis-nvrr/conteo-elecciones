package org.openjfx.Negocio;

import org.openjfx.Estructuras.TSBHashtable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Region {
    String codigoRegion;
    String nombre;
    private TSBHashtable<String, Region> regiones;
    private TSBHashtable<Integer, Integer> total;


    public Region(String codigoRegion, String nombre){
        this.codigoRegion = codigoRegion;
        this.nombre = nombre;
        this.regiones = new TSBHashtable<String, Region>();
        this.total = new TSBHashtable<Integer, Integer>();
    }

    private String getNombre(){ return nombre; }

    private void setNombre(String nombre) {this.nombre = nombre; }


    public boolean esDistrito(){
        String[] digitos = codigoRegion.split("");
        return digitos.length == 2;
    }

    public boolean esSeccion(){
        String[] digitos = codigoRegion.split("");
        return digitos.length == 5;
    }

    public boolean esCircuito(){
        String[] digitos = codigoRegion.split("");
        return digitos.length == 11;
    }

    public String obtenerCodigoDistrito(){
        String[] digitos = codigoRegion.split("");
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            temp.append(digitos[i]);
        }
        return temp.toString();
    }

    public String obtenerCodigoSeccion(){
        String[] digitos = codigoRegion.split("");
        StringBuilder temp = new StringBuilder();
        for (int i = 2; i < 5; i++) {
            temp.append(digitos[i]);
        }
        return temp.toString();
    }

    public String obtenerCodigoCircuito(){
        String[] digitos = codigoRegion.split("");
        StringBuilder temp = new StringBuilder();
        for (int i = 5; i < 11; i++) {
            temp.append(digitos[i]);
        }
        return temp.toString();
    }


    public void agregarRegion(String codigoRegion, String nombreRegion){
        Region region = new Region(codigoRegion, null);
        if(region.esDistrito()) {
            this.agregarSubregion(region.obtenerCodigoDistrito(), nombreRegion);
        }

        if(region.esSeccion()){
            Region distrito = buscarSubregion(region.obtenerCodigoDistrito());
            distrito.agregarSubregion(region.obtenerCodigoSeccion(), nombreRegion);
        }

        if(region.esCircuito()){
            Region distrito = buscarSubregion(region.obtenerCodigoDistrito());
            Region seccion = distrito.buscarSubregion(region.obtenerCodigoSeccion());
            seccion.agregarSubregion(region.obtenerCodigoCircuito(), nombreRegion);
        }
    }

    public void agregarVoto(String codigoRegiones, String codigoMesa, int codigoAgrupacion, int votosAgrupacion){
        Voto voto = new Voto(codigoRegiones, codigoMesa, codigoAgrupacion, votosAgrupacion);
        sumarARegiones(voto);
    }

    public void sumarVotoARegion(int codigoAgrupacion, int votos){
        this.total.putIfAbsent(codigoAgrupacion, 0);
        int anterior = this.total.get(codigoAgrupacion);
        total.put(codigoAgrupacion, anterior+votos);
    }

    private void sumarARegiones(Voto voto){
        int agrupacion = voto.getCodigoAgrupacion();
        int cantidad = voto.getVotosAgrupacion();

        this.sumarVotoARegion(agrupacion, cantidad);

        Region distrito = this.buscarSubregion(voto.obtenerCodigoDistrito());
        distrito.sumarVotoARegion(agrupacion, cantidad);

        Region seccion = distrito.buscarSubregion(voto.obtenerCodigoSeccion());
        seccion.sumarVotoARegion(agrupacion, cantidad);

        Region circuito = seccion.buscarSubregion(voto.obtenerCodigoCircuito());
        circuito.sumarVotoARegion(agrupacion, cantidad);
        circuito.agregarSubregion(voto.getCodigoMesa(), "Mesa " + voto.getCodigoMesa());

        Region mesa = circuito.buscarSubregion(voto.getCodigoMesa());
        mesa.sumarVotoARegion(agrupacion, cantidad);
    }

    private Region buscarSubregion(String codigoRegion){
        Region cargado = this.regiones.get(codigoRegion);
        if(cargado == null){
            cargado = new Region(codigoRegion, null);
            this.regiones.put(codigoRegion, cargado);
        }
        return cargado;
    }

    private void agregarSubregion(String codigoRegion, String nombreRegion){
        Region cargado = this.regiones.get(codigoRegion);
        if(cargado == null){
            this.regiones.put(codigoRegion, new Region(codigoRegion, nombreRegion));
        }
        else{
            if(cargado.getNombre() == null){
                cargado.setNombre(nombreRegion);
                this.regiones.put(codigoRegion, cargado);
            }
        }
    }

    public Collection totalAgrupaciones(TSBHashtable<Integer, Agrupacion> agrupaciones){
        ArrayList<String> arrayList = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : total.entrySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            Integer codigoAgrupacion = entry.getKey();
            Integer total = entry.getValue();
            stringBuilder.append(agrupaciones.get(codigoAgrupacion).getCodigoAgrupacion());
            stringBuilder.append("|");
            stringBuilder.append(agrupaciones.get(codigoAgrupacion).getNombreAgrupacion());
            stringBuilder.append("|");
            stringBuilder.append(total);
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    public Collection<Region> getRegiones(){
        return this.regiones.values();
    }
    public String toString(){
        return codigoRegion + " - " + nombre;
    }


}
