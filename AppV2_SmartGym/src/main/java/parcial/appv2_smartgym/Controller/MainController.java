package parcial.appv2_smartgym.Controller;

import parcial.appv2_smartgym.model.Gimnasio;
import java.time.LocalDate;
public class MainController {

    private Gimnasio gimnasio;

    public MainController() {
        this.gimnasio = Gimnasio.getInstance();
    }

    /**
     * Lógica de negocio para invocar el cálculo de ingresos en el modelo
     */


    public double calcularIngresosPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        // Invoca el método del Singleton Gimnasio y retorna el valor real
        return gimnasio.CalcularIngresosPeriodo(fechaInicio, fechaFin);
    }

    public String obtenerNombreGimnasio() {
        return gimnasio.getNombreComercial();
    }
}