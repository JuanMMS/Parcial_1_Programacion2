package uniquindio.app_smartgym.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uniquindio.app_smartgym.Controller.ServicioAdicionalController;
import uniquindio.app_smartgym.model.ServicioAdicional;
import uniquindio.app_smartgym.model.Tipo;

import java.net.URL;
import java.util.ResourceBundle;

public class ServicioAdicionalViewController implements Initializable {

    private ServicioAdicionalController servicioController;
    private ObservableList<ServicioAdicional> listaServiciosObservable;

    @FXML private Button btnVolver;
    @FXML private Button btnCrear;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;

    @FXML private ComboBox<Tipo> cmbTipo;
    @FXML private CheckBox chkDisponibilidad;

    @FXML private TableView<ServicioAdicional> tblServicios;
    @FXML private TableColumn<ServicioAdicional, String> colCodigo;
    @FXML private TableColumn<ServicioAdicional, String> colNombre;
    @FXML private TableColumn<ServicioAdicional, Tipo> colTipo;
    @FXML private TableColumn<ServicioAdicional, Double> colPrecio;
    @FXML private TableColumn<ServicioAdicional, Boolean> colDisponibilidad;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.servicioController = new ServicioAdicionalController();

        // 1. Cargar valores del enumerador Tipo en el ComboBox
        cmbTipo.setItems(FXCollections.observableArrayList(Tipo.values()));

        // 2. Mapear las columnas de la tabla con los Getters del modelo ServicioAdicional
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("disponibilidad"));

        actualizarTabla();

        // 3. Asignar eventos
        btnCrear.setOnAction(this::crearServicio);
        btnActualizar.setOnAction(this::actualizarServicio);
        btnEliminar.setOnAction(this::eliminarServicio);

        // 4. Listener de selección en la tabla
        tblServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                llenarCamposConSeleccion(newSelection);
            }
        });
    }

    private void crearServicio(ActionEvent event) {
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            Tipo tipo = cmbTipo.getValue();
            boolean disponibilidad = chkDisponibilidad.isSelected();

            if (codigo.isEmpty() || nombre.isEmpty() || tipo == null) {
                mostrarAlerta("Error", "El Código, Nombre y Tipo son obligatorios.", Alert.AlertType.ERROR);
                return;
            }

            boolean creado = servicioController.crearServicio(codigo, nombre, descripcion, precio, disponibilidad, tipo);

            if (creado) {
                mostrarAlerta("Éxito", "Servicio adicional creado correctamente.", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "Ya existe un servicio adicional con ese código.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio debe ser un número válido.", Alert.AlertType.ERROR);
        }
    }

    private void actualizarServicio(ActionEvent event) {
        ServicioAdicional seleccionado = tblServicios.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                boolean actualizado = servicioController.actualizarServicio(
                        seleccionado.getCodigo(),
                        txtCodigo.getText(),
                        txtNombre.getText(),
                        txtDescripcion.getText(),
                        Double.parseDouble(txtPrecio.getText()),
                        chkDisponibilidad.isSelected(),
                        cmbTipo.getValue()
                );

                if (actualizado) {
                    mostrarAlerta("Éxito", "Servicio adicional actualizado.", Alert.AlertType.INFORMATION);
                    actualizarTabla();
                    limpiarCampos();
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el servicio adicional.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "El precio debe ser un número válido.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un servicio de la tabla.", Alert.AlertType.WARNING);
        }
    }

    private void eliminarServicio(ActionEvent event) {
        ServicioAdicional seleccionado = tblServicios.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (servicioController.eliminarServicio(seleccionado.getCodigo())) {
                mostrarAlerta("Éxito", "Servicio adicional eliminado.", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el servicio adicional.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un servicio de la tabla para eliminar.", Alert.AlertType.WARNING);
        }
    }

    private void actualizarTabla() {
        listaServiciosObservable = FXCollections.observableArrayList(servicioController.obtenerListaServicios());
        tblServicios.setItems(listaServiciosObservable);
        tblServicios.refresh();
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        cmbTipo.setValue(null);
        chkDisponibilidad.setSelected(false);
        tblServicios.getSelectionModel().clearSelection();
    }

    private void llenarCamposConSeleccion(ServicioAdicional servicio) {
        txtCodigo.setText(servicio.getCodigo());
        txtNombre.setText(servicio.getNombre());
        txtDescripcion.setText(servicio.getDescripcion());
        txtPrecio.setText(String.valueOf(servicio.getPrecio()));
        cmbTipo.setValue(servicio.getTipo());
        chkDisponibilidad.setSelected(servicio.getDisponibilidad());
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}