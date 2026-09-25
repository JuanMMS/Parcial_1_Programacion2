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
import parcial.appv2_smartgym.Controller.EntrenadorController;
import parcial.appv2_smartgym.model.Entrenador;

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
    @FXML private TextField txtTarifa; // Corregido para que coincida con el FXML

    @FXML private TableView<Entrenador> tblEntrenadores;
    @FXML private TableColumn<Entrenador, String> colIdentificacion;
    @FXML private TableColumn<Entrenador, String> colNombre;
    @FXML private TableColumn<Entrenador, String> colEspecialidad;
    @FXML private TableColumn<Entrenador, String> colTelefono;
    @FXML private TableColumn<Entrenador, Double> colTarifa; // Corregido para que coincida con el FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entrenadorController = new EntrenadorController();

        // 1. Configurar columnas
        if (colIdentificacion != null) colIdentificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colEspecialidad != null) colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        if (colTelefono != null) colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        // El nombre "tarifaSesion" sí se mantiene aquí porque así se llama el atributo en tu clase Entrenador
        if (colTarifa != null) colTarifa.setCellValueFactory(new PropertyValueFactory<>("tarifaSesion"));

        actualizarTabla();

        // 2. Eventos de los botones
        if (btnVolver != null) btnVolver.setOnAction(this::volverAlMenu);
        if (btnCrear != null) btnCrear.setOnAction(this::crearEntrenador);
        if (btnActualizar != null) btnActualizar.setOnAction(this::actualizarEntrenador);
        if (btnEliminar != null) btnEliminar.setOnAction(this::eliminarEntrenador);
        if (btnLimpiar != null) btnLimpiar.setOnAction(e -> limpiarCampos());

        // 3. Listener para seleccionar en la tabla
        if (tblEntrenadores != null) {
            tblEntrenadores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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

    private void crearEntrenador(ActionEvent event) {
        try {
            String identificacion = txtIdentificacion.getText();
            String nombre = txtNombre.getText();
            String especialidad = txtEspecialidad.getText();
            String telefono = txtTelefono.getText();
            double tarifa = Double.parseDouble(txtTarifa.getText());

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
                        Double.parseDouble(txtTarifa.getText())
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
        if (entrenadorController != null && tblEntrenadores != null) {
            listaEntrenadoresObservable = FXCollections.observableArrayList(entrenadorController.obtenerListaEntrenadores());
            tblEntrenadores.setItems(listaEntrenadoresObservable);
            tblEntrenadores.refresh();
        }
    }

    private void limpiarCampos() {
        if (txtIdentificacion != null) txtIdentificacion.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtEspecialidad != null) txtEspecialidad.clear();
        if (txtTelefono != null) txtTelefono.clear();
        if (txtTarifa != null) txtTarifa.clear();
        if (tblEntrenadores != null) tblEntrenadores.getSelectionModel().clearSelection();
    }

    private void llenarCamposConSeleccion(Entrenador entrenador) {
        if (entrenador != null) {
            txtIdentificacion.setText(entrenador.getIdentificacion());
            txtNombre.setText(entrenador.getNombre());
            txtEspecialidad.setText(entrenador.getEspecialidad());
            txtTelefono.setText(entrenador.getTelefono());
            txtTarifa.setText(String.valueOf(entrenador.getTarifaSesion()));
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