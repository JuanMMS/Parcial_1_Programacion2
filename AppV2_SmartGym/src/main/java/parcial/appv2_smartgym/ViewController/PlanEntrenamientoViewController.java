package parcial.appv2_smartgym.ViewController;

import javafx.beans.property.SimpleStringProperty;
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
import parcial.appv2_smartgym.Controller.PlanEntrenamientoController;
import parcial.appv2_smartgym.model.Estado;
import parcial.appv2_smartgym.model.PlanEntrenamiento;

import java.net.URL;
import java.util.ResourceBundle;

public class PlanEntrenamientoViewController implements Initializable {

    private PlanEntrenamientoController planController;
    private ObservableList<PlanEntrenamiento> listaPlanesObservable;

    @FXML private Button btnVolver;
    @FXML private Button btnCrear;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtValorMensual;

    @FXML private ComboBox<Estado> cmbEstado;
    @FXML private ComboBox<String> cmbTipoPlan;

    // Campos específicos para Plan Personalizado
    @FXML private TextField txtCantSesiones;
    @FXML private TextField txtEspecialidad;
    @FXML private TextField txtObjetivo;

    @FXML private TableView<PlanEntrenamiento> tblPlanes;
    @FXML private TableColumn<PlanEntrenamiento, String> colCodigo;
    @FXML private TableColumn<PlanEntrenamiento, String> colNombre;
    @FXML private TableColumn<PlanEntrenamiento, String> colTipo;
    @FXML private TableColumn<PlanEntrenamiento, Integer> colDuracion;
    @FXML private TableColumn<PlanEntrenamiento, Double> colValor;
    @FXML private TableColumn<PlanEntrenamiento, Estado> colEstado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.planController = new PlanEntrenamientoController();

        // 1. Cargar datos en los ComboBox
        if (cmbEstado != null) cmbEstado.setItems(FXCollections.observableArrayList(Estado.values()));
        if (cmbTipoPlan != null) cmbTipoPlan.setItems(FXCollections.observableArrayList("Estándar", "Personalizado"));

        // 2. Configurar binding de columnas
        if (colCodigo != null) colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colDuracion != null) colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMeses"));
        if (colValor != null) colValor.setCellValueFactory(new PropertyValueFactory<>("valorMensual"));
        if (colEstado != null) colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Obtiene dinámicamente el nombre de la clase para la columna Tipo
        if (colTipo != null) {
            colTipo.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getClass().getSimpleName())
            );
        }

        actualizarTabla();

        // 3. Asignar eventos a los botones
        if (btnVolver != null) btnVolver.setOnAction(this::volverAlMenu);
        if (btnCrear != null) btnCrear.setOnAction(this::crearPlan);
        if (btnActualizar != null) btnActualizar.setOnAction(this::actualizarPlan);
        if (btnEliminar != null) btnEliminar.setOnAction(this::eliminarPlan);
        if (btnLimpiar != null) btnLimpiar.setOnAction(e -> limpiarCampos());

        // 4. Control de campos según el tipo de plan seleccionado en el ComboBox
        if (cmbTipoPlan != null) cmbTipoPlan.setOnAction(e -> habilitarCamposPersonalizados());

        // 5. Listener de selección en la tabla
        if (tblPlanes != null) {
            tblPlanes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    llenarCamposConSeleccion(newSelection);
                }
            });
        }

        habilitarCamposPersonalizados(); // Llamada inicial para asegurar el estado correcto
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

    private void crearPlan(ActionEvent event) {
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            int duracion = Integer.parseInt(txtDuracion.getText());
            double valorMensual = Double.parseDouble(txtValorMensual.getText());
            Estado estado = cmbEstado.getValue();

            if (codigo.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta("Error", "El Código y Nombre son obligatorios.", Alert.AlertType.ERROR);
                return;
            }

            // Nota: Si tu PlanEntrenamientoController tiene un método sobrecargado para planes personalizados
            // usando la Factory, deberías capturar los campos específicos (txtCantSesiones, txtEspecialidad, txtObjetivo)
            // y enviarlos aquí mediante una estructura condicional basada en cmbTipoPlan.getValue().
            boolean creado = planController.crearPlan(codigo, nombre, descripcion, duracion, valorMensual, estado);

            if (creado) {
                mostrarAlerta("Éxito", "Plan de entrenamiento registrado.", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "Ya existe un plan registrado con ese código.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Duración y Valor Mensual deben ser numéricos.", Alert.AlertType.ERROR);
        }
    }

    private void actualizarPlan(ActionEvent event) {
        PlanEntrenamiento seleccionado = tblPlanes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                boolean actualizado = planController.actualizarPlan(
                        seleccionado.getCodigo(),
                        txtCodigo.getText(),
                        txtNombre.getText(),
                        txtDescripcion.getText(),
                        Integer.parseInt(txtDuracion.getText()),
                        Double.parseDouble(txtValorMensual.getText()),
                        cmbEstado.getValue()
                );

                if (actualizado) {
                    mostrarAlerta("Éxito", "Plan actualizado correctamente.", Alert.AlertType.INFORMATION);
                    actualizarTabla();
                    limpiarCampos();
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar el plan.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Verifique los datos numéricos ingresados.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un plan de la tabla para actualizar.", Alert.AlertType.WARNING);
        }
    }

    private void eliminarPlan(ActionEvent event) {
        PlanEntrenamiento seleccionado = tblPlanes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            if (planController.eliminarPlan(seleccionado.getCodigo())) {
                mostrarAlerta("Éxito", "Plan eliminado del sistema.", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el plan seleccionado.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un plan de la tabla para eliminar.", Alert.AlertType.WARNING);
        }
    }

    private void actualizarTabla() {
        if (planController != null && tblPlanes != null) {
            listaPlanesObservable = FXCollections.observableArrayList(planController.obtenerListaPlanes());
            tblPlanes.setItems(listaPlanesObservable);
            tblPlanes.refresh();
        }
    }

    private void habilitarCamposPersonalizados() {
        if (cmbTipoPlan != null && txtCantSesiones != null && txtEspecialidad != null && txtObjetivo != null) {
            boolean esPersonalizado = "Personalizado".equals(cmbTipoPlan.getValue());
            txtCantSesiones.setDisable(!esPersonalizado);
            txtEspecialidad.setDisable(!esPersonalizado);
            txtObjetivo.setDisable(!esPersonalizado);
        }
    }

    private void limpiarCampos() {
        if (txtCodigo != null) txtCodigo.clear();
        if (txtNombre != null) txtNombre.clear();
        if (txtDescripcion != null) txtDescripcion.clear();
        if (txtDuracion != null) txtDuracion.clear();
        if (txtValorMensual != null) txtValorMensual.clear();
        if (cmbEstado != null) cmbEstado.setValue(null);
        if (cmbTipoPlan != null) cmbTipoPlan.setValue(null);
        if (txtCantSesiones != null) txtCantSesiones.clear();
        if (txtEspecialidad != null) txtEspecialidad.clear();
        if (txtObjetivo != null) txtObjetivo.clear();
        if (tblPlanes != null) tblPlanes.getSelectionModel().clearSelection();

        habilitarCamposPersonalizados();
    }

    private void llenarCamposConSeleccion(PlanEntrenamiento plan) {
        if (plan != null) {
            if (txtCodigo != null) txtCodigo.setText(plan.getCodigo());
            if (txtNombre != null) txtNombre.setText(plan.getNombre());
            if (txtDescripcion != null) txtDescripcion.setText(plan.getDescripcion());
            if (txtDuracion != null) txtDuracion.setText(String.valueOf(plan.getDuracionMeses()));
            if (txtValorMensual != null) txtValorMensual.setText(String.valueOf(plan.getValorMensual()));
            if (cmbEstado != null) cmbEstado.setValue(plan.getEstado());
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