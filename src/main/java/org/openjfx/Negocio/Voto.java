package org.openjfx.Negocio;

public class Voto {
    private String codigoCompleto;
    private String codigoMesa;
    private int codigoAgrupacion;
    private int votosAgrupacion;


    public Voto(String codigoCompleto, String codigoMesa, int codigoAgrupacion, int votosAgrupacion) {

        this.codigoCompleto = codigoCompleto;
        this.codigoMesa = codigoMesa;
        this.codigoAgrupacion = codigoAgrupacion;
        this.votosAgrupacion = votosAgrupacion;
    }


    public String obtenerCodigoDistrito(){
        String[] digitos = codigoCompleto.split("");
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            temp.append(digitos[i]);
        }
        return temp.toString();
    }

    public String obtenerCodigoSeccion(){
        String[] digitos = codigoCompleto.split("");
        StringBuilder temp = new StringBuilder();
        for (int i = 2; i < 5; i++) {
            temp.append(digitos[i]);
        }
        return temp.toString();
    }

    public String obtenerCodigoCircuito(){
        String[] digitos = codigoCompleto.split("");
        StringBuilder temp = new StringBuilder();
        for (int i = 5; i < 11; i++) {
            temp.append(digitos[i]);
        }
        return temp.toString();
    }


    public String getCodigoMesa() {
        return codigoMesa;
    }

    public int getCodigoAgrupacion() {
        return codigoAgrupacion;
    }

    public int getVotosAgrupacion() {
        return votosAgrupacion;
    }
}
