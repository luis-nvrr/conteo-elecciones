package org.openjfx;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import org.openjfx.Negocio.*;

public class InterfazController {

    private Escrutinio escrutinio = new Escrutinio();
    private ObservableList ol;


    public Alert alerta;
    public Label lblRuta;
    public ComboBox cmbDistritos;
    public ComboBox cmbSecciones;
    public ComboBox cmbCircuitos;
    public ComboBox cmbMesas;
    public TableView tblTotales;


    public void buscarRuta(){
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Seleccione la carpeta que contiene los datos...");
        File file = dc.showDialog(null);
        if (file != null) lblRuta.setText(file.getPath());
    }

    private void crearAlerta(String titulo, String texto) {
        alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        this.alerta.setHeaderText(null);
        alerta.setContentText(texto);
    }



    public void btnCargar(){
        crearAlerta("Cargando", "Por favor, espere.... " +
                "\n Este dialogo se cerrará automaticamente" );

        Task<Boolean> task = new Task<Boolean>() {
            @Override public Boolean call() {
                // do your operation in here
                Carga carga = new Carga();
                carga.cargarRegiones(escrutinio, lblRuta.getText());
                carga.cargarAgrupaciones(escrutinio, lblRuta.getText());
                carga.cargarVotos(escrutinio, lblRuta.getText());

                ObservableList ol;
                ol = FXCollections.observableArrayList(escrutinio.getDistritos());
                cmbDistritos.setItems(ol);
                return cmbDistritos.getItems() != null;
            }
        };

        task.setOnRunning((e) -> alerta.show());
        task.setOnSucceeded((e) -> { alerta.hide(); });
        task.setOnFailed((e) -> { });
        new Thread(task).start();
    }

    public void filtrarSecciones(ActionEvent actionEvent) {
        Region distrito = (Region) cmbDistritos.getValue();
        ol = FXCollections.observableArrayList(distrito.getRegiones());
        cmbSecciones.setItems(ol);
        mostrarTotales(distrito);
        cmbCircuitos.getItems().clear();
        cmbMesas.getItems().clear();
    }

    public void filtrarCircuitos(ActionEvent actionEvent) {
        if (cmbSecciones.getValue() != null)
        {
            Region seccion = (Region) cmbSecciones.getValue();
            ol = FXCollections.observableArrayList(seccion.getRegiones());
            cmbCircuitos.setItems(ol);
            mostrarTotales(seccion);
        }
        else { cmbCircuitos.getItems().clear(); }
    }

    public void filtrarMesas(ActionEvent actionEvent) {
        if (cmbCircuitos.getValue() != null)
        {
            Region circuito = (Region) cmbCircuitos.getValue();
            ol = FXCollections.observableArrayList(circuito.getRegiones());
            cmbMesas.setItems(ol);
            mostrarTotales(circuito);
        }
        else cmbMesas.getItems().clear();
    }

    public void totalMesa(ActionEvent actionEvent) {
        if (cmbMesas.getValue() != null)
        {
            Region mesa = (Region) cmbMesas.getValue();
            mostrarTotales(mesa);
        }
        else cmbMesas.getItems().clear();
    }

    private void mostrarTotales(Region region){
        Collection<String> totales  = region.totalAgrupaciones(escrutinio.getAgrupaciones());
        ObservableList<Total> listaDatos = FXCollections.observableArrayList();
        Iterator<String> it = totales.iterator();

        while(it.hasNext()){
            String[] datos = it.next().split("\\|");
            Total total = new Total(datos[0], datos[1], Integer.parseInt(datos[2]));
            listaDatos.add(total);
        }

        tblTotales.setItems(listaDatos);
    }


    public void initialize() {
        TableColumn<Voto, String> codigo = new TableColumn<>("Código");
        TableColumn<Voto, String> agrupacion = new TableColumn<>("Agrupación");
        agrupacion.setMinWidth(395);
        TableColumn<Voto, Integer> total = new TableColumn<>("Total");
        codigo.setCellValueFactory(new PropertyValueFactory<>("codigoAgrupacion"));
        agrupacion.setCellValueFactory(new PropertyValueFactory<>("nombreAgrupacion"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblTotales.getColumns().addAll(codigo, agrupacion, total);

    }
}
