package parcial.appv2_smartgym.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import parcial.appv2_smartgym.Controller.ServicioAdicionalController;
import parcial.appv2_smartgym.model.ServicioAdicional;
import parcial.appv2_smartgym.model.Tipo;

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
        if (cmbTipo != null) cmbTipo.setItems(FXCollections.observableArrayList(Tipo.values()));

        // 2. Mapear las columnas de la tabla con los Getters del modelo ServicioAdicional
        if (colCodigo != null) colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colTipo != null) colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        if (colPrecio != null) colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        if (colDisponibilidad != null) colDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("disponibilidad"));

        actualizarTabla();

        // 3. Asignar eventos a los botones
        if (btnVolver != null) btnVolver.setOnAction(this::volverAlMenu);
        if (btnCrear != null) btnCrear.setOnAction(this::crearServicio);
        if (btnActualizar != null) btnActualizar.setOnAction(this::actualizarServicio);
        if (btnEliminar != null) btnEliminar.setOnAction(this::eliminarServicio);

        // 4. Listener de selección en la tabla
        if (tblServicios != null) {
            tblServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    llenarCamposConSeleccion(newSelection);
                }
            });
        }
    }

    private void volverAlMenu(ActionEvent event) {
        try {
            URL url = getClass().getResource("/parcial/appv2_smartgym/Main.fxml");
            if (url == null) {
                mostrarAlerta("Error", "No se encontró Main.fxml", Alert.AlertType.ERROR);
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setTitle("SmartGym - Menú Principal");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar al menú principal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
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
        if (servicioController != null && tblServicios != null) {
            listaServiciosObservable = FXCollections.observableArrayList(servicioController.obtenerListaServicios());
            tblServicios.setItems(listaServiciosObservable);
            tblServicios.refresh();
        }
    }

    private void limpiarCampos() {
        if (txtCodigo != null) txtCodigo.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtDescripcion != null) txtDescripcion.clear();
        if (txtPrecio != null) txtPrecio.clear();
        if (cmbTipo != null) cmbTipo.setValue(null);
        if (chkDisponibilidad != null) chkDisponibilidad.setSelected(false);
        if (tblServicios != null) tblServicios.getSelectionModel().clearSelection();
    }

    private void llenarCamposConSeleccion(ServicioAdicional servicio) {
        if (servicio != null) {
            if (txtCodigo != null) txtCodigo.setText(servicio.getCodigo());
            if (txtNombre != null) txtNombre.setText(servicio.getNombre());
            if (txtDescripcion != null) txtDescripcion.setText(servicio.getDescripcion());
            if (txtPrecio != null) txtPrecio.setText(String.valueOf(servicio.getPrecio()));
            if (cmbTipo != null) cmbTipo.setValue(servicio.getTipo());
            if (chkDisponibilidad != null) chkDisponibilidad.setSelected(servicio.getDisponibilidad());
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}