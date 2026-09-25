package parcial.appv2_smartgym.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carga la interfaz gráfica del menú principal
            // Correcto (inicia con /)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/parcial/appv2_smartgym/Main.fxml"));
            Parent root = loader.load();

            // Configura la escena y la ventana principal (Stage)
            Scene scene = new Scene(root);
            primaryStage.setTitle("SmartGym - Sistema de Gestión");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Opcional: evita redimensionar la ventana
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la vista principal FXML:");
            e.printStackTrace();
        }
    }

    /**
     * Punto de entrada principal de la aplicación Java
     */
    public static void main(String[] args) {
        launch(args);
    }
}