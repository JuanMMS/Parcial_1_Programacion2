package uniquindio.app_smartgym.ViewController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uniquindio.app_smartgym.Controller.PlanEntrenamientoController;
import uniquindio.app_smartgym.model.Estado;
import uniquindio.app_smartgym.model.PlanEntrenamiento;

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
        cmbEstado.setItems(FXCollections.observableArrayList(Estado.values()));
        cmbTipoPlan.setItems(FXCollections.observableArrayList("Estándar", "Personalizado"));

        // 2. Configurar binding de columnas
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMeses"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorMensual"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Obtiene dinámicamente el nombre de la clase para la columna Tipo
        colTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getClass().getSimpleName())
        );

        actualizarTabla();

        // 3. Asignar eventos a los botones
        btnCrear.setOnAction(this::crearPlan);
        btnActualizar.setOnAction(this::actualizarPlan);
        btnEliminar.setOnAction(this::eliminarPlan);
        btnLimpiar.setOnAction(e -> limpiarCampos());

        // 4. Control de campos según el tipo de plan seleccionado en el ComboBox
        cmbTipoPlan.setOnAction(e -> habilitarCamposPersonalizados());

        // 5. Listener de selección en la tabla
        tblPlanes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                llenarCamposConSeleccion(newSelection);
            }
        });
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

            boolean creado = planController.crearPlan(codigo, nombre, descripcion, duracion, valorMensual, estado);

            if (creado) {
                mostrarAlerta("Éxito", "Plan de entrenamiento registrado.", Alert.AlertType.INFORMATION);
                actualizarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "Ya existe un plan registrado con ese código.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Duración y Valor Mensual deben ser valores numéricos válidos.", Alert.AlertType.ERROR);
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
        listaPlanesObservable = FXCollections.observableArrayList(planController.obtenerListaPlanes());
        tblPlanes.setItems(listaPlanesObservable);
        tblPlanes.refresh();
    }

    private void habilitarCamposPersonalizados() {
        boolean esPersonalizado = "Personalizado".equals(cmbTipoPlan.getValue());
        txtCantSesiones.setDisable(!esPersonalizado);
        txtEspecialidad.setDisable(!esPersonalizado);
        txtObjetivo.setDisable(!esPersonalizado);
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtDuracion.clear();
        txtValorMensual.clear();
        cmbEstado.setValue(null);
        cmbTipoPlan.setValue(null);
        txtCantSesiones.clear();
        txtEspecialidad.clear();
        txtObjetivo.clear();
        tblPlanes.getSelectionModel().clearSelection();
    }

    private void llenarCamposConSeleccion(PlanEntrenamiento plan) {
        txtCodigo.setText(plan.getCodigo());
        txtNombre.setText(plan.getNombre());
        txtDescripcion.setText(plan.getDescripcion());
        txtDuracion.setText(String.valueOf(plan.getDuracionMeses()));
        txtValorMensual.setText(String.valueOf(plan.getValorMensual()));
        cmbEstado.setValue(plan.getEstado());
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}