package org.openjfx.Negocio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Carga {


    public void cargarRegiones(Escrutinio escrutinio, String path){

        File file = new File(path + "\\descripcion_regiones.dsv");

        try (Scanner scanner = new Scanner(file);)
        {
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String lineaCompleta = scanner.nextLine();
                String[] lineaSeparada = lineaCompleta.split("\\|");
                String codigoRegion = lineaSeparada[0];
                String nombreRegion = lineaSeparada[1];
                escrutinio.agregarRegion(codigoRegion, nombreRegion);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void cargarAgrupaciones(Escrutinio escrutinio, String path){
        File file = new File(path + "\\descripcion_postulaciones.dsv");

        try (Scanner lector = new Scanner(file))
        {
            lector.nextLine();
            while(lector.hasNextLine()){
                String lineaCompleta = lector.nextLine();
                String[] lineaSeparada = lineaCompleta.split("\\|");

                if(lineaSeparada[0].equals("000100000000000")){
                    int codigoAgrupacion = Integer.parseInt(lineaSeparada[2]);
                    String nombreAgrupacion = lineaSeparada[3];
                    escrutinio.agregarAgrupacion(codigoAgrupacion, nombreAgrupacion);
                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }


    public void cargarVotos(Escrutinio escrutinio, String path){
        File file = new File(path + "\\mesas_totales_agrp_politica.dsv");

        try (Scanner lector = new Scanner(file))
        {
            lector.nextLine();
            while(lector.hasNextLine()){
                String lineaCompleta = lector.nextLine();

                if(lineaCompleta.contains("000100000000000"))
                {
                    String[] lineaSeparada = lineaCompleta.split("\\|");
                    String codigoRegiones = lineaSeparada[2];
                    String codigoMesa = lineaSeparada[3];
                    int codigoAgrupacion = Integer.parseInt(lineaSeparada[5]);
                    int votosAgrupacion = Integer.parseInt(lineaSeparada[6]);

                    escrutinio.agregarVoto(codigoRegiones,codigoMesa,codigoAgrupacion,votosAgrupacion);
                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

}
