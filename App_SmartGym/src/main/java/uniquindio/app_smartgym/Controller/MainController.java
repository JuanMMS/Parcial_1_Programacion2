package uniquindio.app_smartgym.Controller;

import uniquindio.app_smartgym.model.Gimnasio;

public class MainController {

    private Gimnasio gimnasio;

    public MainController() {
        this.gimnasio = Gimnasio.getInstance();
    }

    /**
     * Lógica de negocio para invocar el cálculo de ingresos en el modelo
     */
    public double calcularIngresosPeriodo() {
        // Invoca el método del Singleton Gimnasio
        gimnasio.CalcularIngresosPeriodo();

        // Aquí puedes retornar el cálculo realizado por el modelo
        return 0.0;
    }

    public String obtenerNombreGimnasio() {
        return gimnasio.getNombreComercial();
    }
}