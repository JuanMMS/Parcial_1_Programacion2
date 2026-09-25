package parcial.appv2_smartgym.ViewController;

import parcial.appv2_smartgym.Controller.InscripcionController;
import parcial.appv2_smartgym.model.*;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador JavaFX (ViewController) para el módulo de Gestión de Inscripciones e Ingresos por Periodo.
 */
public class InscripcionViewController implements Initializable {

    // --- CONTROLES DE FORMULARIO DIBUJADOS EN FXML ---
    @FXML private TextField txtCodigo;
    @FXML private DatePicker dpFechaInscripcion;
    @FXML private ComboBox<Cliente> cmbCliente;
    @FXML private ComboBox<PlanEntrenamiento> cmbPlan;
    @FXML private TextField txtDescuento;
    @FXML private ListView<ServicioAdicional> listServiciosAdicionales;
    @FXML private Label lblValorTotalCalculado;

    // --- CONTROLES DE CONSULTA DE INGRESOS ---
    @FXML private DatePicker dpFechaInicioReporte;
    @FXML private DatePicker dpFechaFinReporte;
    @FXML private Button btnConsultarIngresos;
    @FXML private Label lblIngresosTotales;

    // --- BOTONES Y CONTROLES NAVEGACIONALES ---
    @FXML private Button btnCalcularTotal;
    @FXML private Button btnCrearInscripcion;
    @FXML private Button btnEliminarInscripcion;
    @FXML private Button btnLimpiar;
    @FXML private Button btnVolver;

    // --- TABLA Y COLUMNAS ---
    @FXML private TableView<Inscripcion> tblInscripciones;
    @FXML private TableColumn<Inscripcion, String> colCodigo;
    @FXML private TableColumn<Inscripcion, String> colFecha;
    @FXML private TableColumn<Inscripcion, String> colCliente;
    @FXML private TableColumn<Inscripcion, String> colPlan;
    @FXML private TableColumn<Inscripcion, Number> colDescuento;
    @FXML private TableColumn<Inscripcion, Number> colValorTotal;

    // --- CONTROLADOR DE NEGOCIO Y OBSERVABLE LIST ---
    private InscripcionController inscripcionController;
    private ObservableList<Inscripcion> listaInscripcionesObservable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inscripcionController = new InscripcionController();
        listaInscripcionesObservable = FXCollections.observableArrayList();

        if (dpFechaInscripcion != null) dpFechaInscripcion.setValue(LocalDate.now());
        if (txtDescuento != null) txtDescuento.setText("0");

        cargarCombos();
        configurarTabla();
        configurarEventos();
        actualizarTabla();
    }

    /**
     * Carga las colecciones disponibles en los ComboBox y ListView.
     */
    private void cargarCombos() {
        if (inscripcionController != null) {
            if (cmbCliente != null && inscripcionController.obtenerClientesDisponibles() != null) {
                cmbCliente.setItems(FXCollections.observableArrayList(inscripcionController.obtenerClientesDisponibles()));
            }
            if (cmbPlan != null && inscripcionController.obtenerPlanesDisponibles() != null) {
                cmbPlan.setItems(FXCollections.observableArrayList(inscripcionController.obtenerPlanesDisponibles()));
            }
            if (listServiciosAdicionales != null && inscripcionController.obtenerServiciosDisponibles() != null) {
                listServiciosAdicionales.setItems(FXCollections.observableArrayList(inscripcionController.obtenerServiciosDisponibles()));
                listServiciosAdicionales.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            }
        }
    }

    /**
     * Mapea las columnas de la TableView con las propiedades de la clase Inscripcion.
     */
    private void configurarTabla() {
        if (tblInscripciones != null) {
            if (colCodigo != null) colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getiD()));
            if (colFecha != null) colFecha.setCellValueFactory(data -> new SimpleStringProperty(
                    data.getValue().getFechaInscripcion() != null ? data.getValue().getFechaInscripcion().toString() : ""));
            if (colCliente != null) colCliente.setCellValueFactory(data -> new SimpleStringProperty(
                    data.getValue().getCliente() != null ? data.getValue().getCliente().getNombreCompleto() : ""));
            if (colPlan != null) colPlan.setCellValueFactory(data -> new SimpleStringProperty(
                    data.getValue().getPlanEntrenamiento() != null ? data.getValue().getPlanEntrenamiento().getNombre() : ""));
            if (colDescuento != null) colDescuento.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPorcentajeDescuento()));
            if (colValorTotal != null) colValorTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorAPagar()));

            tblInscripciones.setItems(listaInscripcionesObservable);
        }
    }

    /**
     * Asigna los escuchadores de eventos a los botones y la tabla.
     */
    private void configurarEventos() {
        if (btnCalcularTotal != null) btnCalcularTotal.setOnAction(e -> handleCalcularTotal());
        if (btnCrearInscripcion != null) btnCrearInscripcion.setOnAction(this::handleCrearInscripcion);
        if (btnEliminarInscripcion != null) btnEliminarInscripcion.setOnAction(this::handleEliminarInscripcion);
        if (btnLimpiar != null) btnLimpiar.setOnAction(e -> limpiarFormulario());
        if (btnConsultarIngresos != null) btnConsultarIngresos.setOnAction(e -> handleConsultarIngresos());
        if (btnVolver != null) btnVolver.setOnAction(e -> handleVolver());

        if (tblInscripciones != null) {
            tblInscripciones.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    mostrarDetalleInscripcion(newVal);
                }
            });
        }
    }

    /**
     * Calcula de forma preliminar el costo total antes de guardar la inscripción.
     */
    private double handleCalcularTotal() {
        Cliente cliente = cmbCliente != null ? cmbCliente.getValue() : null;
        PlanEntrenamiento plan = cmbPlan != null ? cmbPlan.getValue() : null;

        if (plan == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Plan Requerido", "Selección Incompleta", "Debe seleccionar un plan de entrenamiento para calcular el costo.");
            return 0.0;
        }

        double descuento = 0.0;
        try {
            if (txtDescuento != null) descuento = Double.parseDouble(txtDescuento.getText().trim());
        } catch (NumberFormatException e) {
            descuento = 0.0;
        }

        List<ServicioAdicional> serviciosSel = listServiciosAdicionales != null ? listServiciosAdicionales.getSelectionModel().getSelectedItems() : null;

        Inscripcion temp = new Inscripcion("TEMP", dpFechaInscripcion.getValue(), cliente, plan);
        if (serviciosSel != null) {
            for (ServicioAdicional servicio : serviciosSel) {
                temp.agregarServicioAdicional(servicio);
            }
        }
        temp.EstablecerDescuento();
        double total = temp.CalcularValorAPagar();

        if (lblValorTotalCalculado != null) lblValorTotalCalculado.setText(String.format("$ %.2f", total));
        return total;
    }

    /**
     * Procesa y registra la inscripción en el modelo.
     */
    private void handleCrearInscripcion(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        LocalDate fecha = dpFechaInscripcion.getValue();
        Cliente cliente = cmbCliente.getValue();
        PlanEntrenamiento plan = cmbPlan.getValue();

        if (codigo.isEmpty() || fecha == null || cliente == null || plan == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos Incompletos", "Campos Requeridos", "Complete el código, la fecha, el cliente y el plan.");
            return;
        }

        double descuento = 0.0;
        try {
            descuento = Double.parseDouble(txtDescuento.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Formato Inválido", "Descuento Incorrecto", "El descuento debe ser un número entero o decimal.");
            return;
        }

        List<ServicioAdicional> serviciosSel = listServiciosAdicionales != null ? listServiciosAdicionales.getSelectionModel().getSelectedItems() : null;

        try {
            Inscripcion inscripcion = inscripcionController.crearInscripcion(
                    codigo, fecha, cliente, plan, descuento, serviciosSel);

            if (lblValorTotalCalculado != null) lblValorTotalCalculado.setText(String.format("$ %.2f", inscripcion.getValorAPagar()));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Inscripción Registrada",
                    "La inscripción fue procesada exitosamente por un total de: $" + String.format("%.2f", inscripcion.getValorAPagar()));

            actualizarTabla();
            limpiarFormulario();

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Inscripción", "No se pudo registrar la inscripción", e.getMessage());
        }
    }

    private void handleEliminarInscripcion(ActionEvent event) {
        Inscripcion seleccionada = tblInscripciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección Requerida", "Sin selección", "Seleccione una inscripción de la tabla para eliminar.");
            return;
        }

        try {
            inscripcionController.eliminarInscripcion(seleccionada.getiD());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Inscripción Eliminada", "La inscripción fue eliminada exitosamente.");
            actualizarTabla();
            limpiarFormulario();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la inscripción", e.getMessage());
        }
    }

    private void handleConsultarIngresos() {
        LocalDate inicio = dpFechaInicioReporte.getValue();
        LocalDate fin = dpFechaFinReporte.getValue();

        if (inicio == null || fin == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Fechas Requeridas", "Rango Incompleto", "Seleccione la fecha inicial y final para consultar los ingresos.");
            return;
        }

        try {
            double totalIngresos = inscripcionController.calcularIngresosPeriodo(inicio, fin);
            if (lblIngresosTotales != null) lblIngresosTotales.setText(String.format("$ %.2f", totalIngresos));
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Consulta", "No se pudo calcular el ingreso", e.getMessage());
        }
    }

    private void mostrarDetalleInscripcion(Inscripcion inscripcion) {
        if (txtCodigo != null) txtCodigo.setText(inscripcion.getiD());
        if (dpFechaInscripcion != null) dpFechaInscripcion.setValue(inscripcion.getFechaInscripcion());
        if (cmbCliente != null) cmbCliente.setValue(inscripcion.getCliente());
        if (cmbPlan != null) cmbPlan.setValue(inscripcion.getPlanEntrenamiento());
        if (txtDescuento != null) txtDescuento.setText(String.valueOf(inscripcion.getPorcentajeDescuento()));
        if (lblValorTotalCalculado != null) lblValorTotalCalculado.setText(String.format("$ %.2f", inscripcion.getValorAPagar()));
    }

    private void actualizarTabla() {
        if (inscripcionController != null && tblInscripciones != null) {
            listaInscripcionesObservable.setAll(inscripcionController.obtenerTodasInscripciones());
        }
    }

    private void limpiarFormulario() {
        if (txtCodigo != null) txtCodigo.clear();
        if (dpFechaInscripcion != null) dpFechaInscripcion.setValue(LocalDate.now());
        if (cmbCliente != null) cmbCliente.setValue(null);
        if (cmbPlan != null) cmbPlan.setValue(null);
        if (txtDescuento != null) txtDescuento.setText("0");
        if (listServiciosAdicionales != null) listServiciosAdicionales.getSelectionModel().clearSelection();
        if (lblValorTotalCalculado != null) lblValorTotalCalculado.setText("$ 0.00");
        if (tblInscripciones != null) tblInscripciones.getSelectionModel().clearSelection();
    }

    private void handleVolver() {
        try {
            URL url = getClass().getResource("/parcial/appv2_smartgym/Main.fxml");
            if (url == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró Main.fxml", "La vista principal no fue localizada.");
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
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al regresar", e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String cabecera, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}