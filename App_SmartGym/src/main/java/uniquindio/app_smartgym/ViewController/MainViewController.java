package uniquindio.app_smartgym.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import uniquindio.app_smartgym.Controller.MainController;

import java.io.IOException;
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
        cambiarPantalla("/uniquindio/app_smartgym/view/ClienteView.fxml", "Gestión de Clientes");
    }

    private void navegarAGestionEntrenadores(ActionEvent event) {
        cambiarPantalla("/uniquindio/app_smartgym/view/EntrenadorView.fxml", "Gestión de Entrenadores");
    }

    private void navegarAGestionPlanes(ActionEvent event) {
        cambiarPantalla("/uniquindio/app_smartgym/view/PlanView.fxml", "Gestión de Planes de Entrenamiento");
    }

    private void navegarAGestionServicios(ActionEvent event) {
        cambiarPantalla("/uniquindio/app_smartgym/view/ServicioView.fxml", "Gestión de Servicios Adicionales");
    }

    private void navegarAGestionInscripciones(ActionEvent event) {
        cambiarPantalla("/uniquindio/app_smartgym/view/InscripcionView.fxml", "Gestión de Inscripciones");
    }

    private void ejecutarCalcularIngresos(ActionEvent event) {
        // Delega la operación de cálculo al Controlador de Negocio
        double totalIngresos = mainController.calcularIngresosPeriodo();

        mostrarAlerta("Ingresos por Periodo",
                "El cálculo de ingresos del periodo ha sido procesado exitosamente.\nTotal: $" + totalIngresos,
                Alert.AlertType.INFORMATION);
    }

    /**
     * Método auxiliar para cargar vistas FXML en la misma ventana (Stage)
     */
    private void cambiarPantalla(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Obtiene la ventana actual desde el botón presionado
            Stage stage = (Stage) btnGestionClientes.getScene().getWindow();
            stage.setTitle("SmartGym - " + titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Navegación", "No se pudo cargar la vista: " + fxmlPath, Alert.AlertType.ERROR);
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