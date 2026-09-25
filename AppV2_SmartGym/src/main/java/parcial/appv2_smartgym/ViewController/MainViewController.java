package parcial.appv2_smartgym.ViewController;

import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import parcial.appv2_smartgym.Controller.MainController;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private MainController mainController;

    @FXML private Button btnGestionClientes;
    @FXML private Button btnGestionEntrenadores;
    @FXML private Button btnGestionPlanes;
    @FXML private Button btnGestionServicios;
    @FXML private Button btnGestionInscripciones;
    @FXML private Button btnCalcularIngresos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mainController = new MainController();

        // Asignación de eventos a los botones de la interfaz principal
        btnGestionClientes.setOnAction(this::navegarAGestionClientes);
        btnGestionEntrenadores.setOnAction(this::navegarAGestionEntrenadores);
        btnGestionPlanes.setOnAction(this::navegarAGestionPlanes);
        btnGestionServicios.setOnAction(this::navegarAGestionServicios);
        btnGestionInscripciones.setOnAction(this::navegarAGestionInscripciones);
        btnCalcularIngresos.setOnAction(this::ejecutarCalcularIngresos);
    }

    private void navegarAGestionClientes(ActionEvent event) {
        cambiarPantalla("/parcial/appv2_smartgym/ClienteView.fxml", "Gestión de Clientes");
    }

    private void navegarAGestionEntrenadores(ActionEvent event) {
        cambiarPantalla("/parcial/appv2_smartgym/EntrenadorView.fxml", "Gestión de Entrenadores");
    }

    private void navegarAGestionPlanes(ActionEvent event) {
        cambiarPantalla("/parcial/appv2_smartgym/PlanEntrenamientoView.fxml", "Gestión de Planes de Entrenamiento");
    }

    private void navegarAGestionServicios(ActionEvent event) {
        cambiarPantalla("/parcial/appv2_smartgym/ServicioAdicionalView.fxml", "Gestión de Servicios Adicionales");
    }

    private void navegarAGestionInscripciones(ActionEvent event) {
        cambiarPantalla("/parcial/appv2_smartgym/InscripcionView.fxml", "Gestión de Inscripciones");
    }

    private void ejecutarCalcularIngresos(ActionEvent event) {
        LocalDate fechaInicio = LocalDate.of(2023, 1, 1);
        LocalDate fechaFin = LocalDate.now();

        double totalIngresos = mainController.calcularIngresosPeriodo(fechaInicio, fechaFin);

        mostrarAlerta("Ingresos por Periodo",
                "El cálculo de ingresos del período ha sido procesado exitosamente.\nTotal: $" + totalIngresos,
                Alert.AlertType.INFORMATION);
    }

    /**
     * Método auxiliar para cargar vistas FXML en la misma ventana (Stage)
     */
    private void cambiarPantalla(String fxmlPath, String titulo) {
        try {
            URL url = getClass().getResource(fxmlPath);
            if (url == null) {
                mostrarAlerta("Error de Ruta", "No se encontró el archivo FXML en: " + fxmlPath, Alert.AlertType.ERROR);
                return;
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            // Obtiene la ventana actual desde cualquiera de los botones
            Stage stage = (Stage) btnGestionClientes.getScene().getWindow();
            stage.setTitle("SmartGym - " + titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error de Navegación", "Ocurrió un error al cargar la vista: " + e.getMessage(), Alert.AlertType.ERROR);
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