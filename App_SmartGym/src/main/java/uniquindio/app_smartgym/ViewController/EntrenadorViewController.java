package uniquindio.app_smartgym.ViewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uniquindio.app_smartgym.Controller.EntrenadorController;
import uniquindio.app_smartgym.model.Entrenador;

import java.net.URL;
import java.util.ResourceBundle;

public class EntrenadorViewController implements Initializable {

    private EntrenadorController entrenadorController;
    private ObservableList<Entrenador> listaEntrenadoresObservable;

    @FXML private Button btnVolver;
    @FXML private Button btnCrear;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    @FXML private TextField txtIdentificacion;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEspecialidad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtTarifaSesion;

    @FXML private TableView<Entrenador> tblEntrenadores;
    @FXML private TableColumn<Entrenador, String> colIdentificacion;
    @FXML private TableColumn<Entrenador, String> colNombre;
    @FXML private TableColumn<Entrenador, String> colEspecialidad;
    @FXML private TableColumn<Entrenador, String> colTelefono;
    @FXML private TableColumn<Entrenador, Double> colTarifaSesion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entrenadorController = new EntrenadorController();

        // 1. Configurar columnas (Los nombres deben coincidir con los atributos de la clase Entrenador)
        colIdentificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTarifaSesion.setCellValueFactory(new PropertyValueFactory<>("tarifaSesion"));

        actualizarTabla();

        // 2. Eventos de los botones
        btnCrear.setOnAction(this::crearEntrenador);
        btnActualizar.setOnAction(this::actualizarEntrenador);
        btnEliminar.setOnAction(this::eliminarEntrenador);
        btnLimpiar.setOnAction(e -> limpiarCampos());

        // 3. Listener para seleccionar en la tabla
        tblEntrenadores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                llenarCamposConSeleccion(newSelection);
            }
        });
    }

    private void crearEntrenador(ActionEvent event) {
        try {
            String identificacion = txtIdentificacion.getText();
            String nombre = txtNombre.getText();
            String especialidad = txtEspecialidad.getText();
            String telefono = txtTelefono.getText();
            double tarifa = Double.parseDouble(txtTarifaSesion.getText());

            if (identificacion.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta("Error", "Identificación y Nombre son obligatorios.", Alert.AlertType.ERROR);
                return;
            }

            boolean creado = entrenadorController.crearEntrenador(identificacion, nombre, especialidad, telefono, tarifa);

            if (creado) {
                mostrarAlerta("Éxito", "Entrenador registrado correctamente.", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "Ya existe un entrenador con esta identificación.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La tarifa por sesión debe ser un valor numérico.", Alert.AlertType.ERROR);
        }
    }

    private void actualizarEntrenador(ActionEvent event) {
        Entrenador seleccionado = tblEntrenadores.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                boolean actualizado = entrenadorController.actualizarEntrenador(
                        seleccionado.getIdentificacion(),
                        txtIdentificacion.getText(),
                        txtNombre.getText(),
                        txtEspecialidad.getText(),
                        txtTelefono.getText(),
                        Double.parseDouble(txtTarifaSesion.getText())
                );

                if (actualizado) {
                    mostrarAlerta("Éxito", "Entrenador actualizado correctamente.", Alert.AlertType.INFORMATION);
                    actualizarTabla();
                    limpiarCampos();
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar la información.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "La tarifa por sesión debe ser un número válido.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un entrenador de la tabla.", Alert.AlertType.WARNING);
        }
    }

    private void eliminarEntrenador(ActionEvent event) {
        Entrenador seleccionado = tblEntrenadores.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (entrenadorController.eliminarEntrenador(seleccionado.getIdentificacion())) {
                mostrarAlerta("Éxito", "Entrenador eliminado del sistema.", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el entrenador.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un entrenador de la tabla para eliminar.", Alert.AlertType.WARNING);
        }
    }

    private void actualizarTabla() {
        listaEntrenadoresObservable = FXCollections.observableArrayList(entrenadorController.obtenerListaEntrenadores());
        tblEntrenadores.setItems(listaEntrenadoresObservable);
        tblEntrenadores.refresh();
    }

    private void limpiarCampos() {
        txtIdentificacion.clear();
        txtNombre.clear();
        txtEspecialidad.clear();
        txtTelefono.clear();
        txtTarifaSesion.clear();
        tblEntrenadores.getSelectionModel().clearSelection();
    }

    private void llenarCamposConSeleccion(Entrenador entrenador) {
        txtIdentificacion.setText(entrenador.getIdentificacion());
        txtNombre.setText(entrenador.getNombre());
        txtEspecialidad.setText(entrenador.getEspecialidad());
        txtTelefono.setText(entrenador.getTelefono());
        txtTarifaSesion.setText(String.valueOf(entrenador.getTarifaSesion()));
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}