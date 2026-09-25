package uniquindio.app_smartgym.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Inscripcion {
    private LocalDate fechaInscripcion;
    private double valorAPagar;

    // Relaciones de asociación (Multiplicidad '1' según las líneas del diagrama)
    private Cliente cliente;
    private AbstractPlanEntrenamiento planEntrenamiento;
    private Entrenador entrenador;

    // Relaciones de asociación (Multiplicidad 'n' según las líneas del diagrama)
    private List<ServicioAdicional> lstServiciosAdicionalesPlanEntrenamiento;

    /*
    Constructor:
    Ajustado estrictamente a la firma que aparece en la caja del UML:
    + Inscripcion(date fechaInscripcion, double valorAPagar, Cliente cliente, PlanEntrenamiento planEntrenamiento)
    */
    public Inscripcion(LocalDate fechaInscripcion, double valorAPagar, Cliente cliente, AbstractPlanEntrenamiento planEntrenamiento) {
        this.fechaInscripcion = fechaInscripcion;
        this.valorAPagar = valorAPagar;
        this.cliente = cliente;
        this.planEntrenamiento = planEntrenamiento;

        // Inicialización de la lista para evitar NullPointerException
        this.lstServiciosAdicionalesPlanEntrenamiento = new ArrayList<>();
    }

    // Métodos funcionales indicados en el diagrama UML
    public void EstablecerDescuento() {}

    public void CalcularValorAPagar() {}

    // Getters y Setters

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public double getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(double valorAPagar) {
        this.valorAPagar = valorAPagar;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public AbstractPlanEntrenamiento getPlanEntrenamiento() {
        return planEntrenamiento;
    }

    public void setPlanEntrenamiento(AbstractPlanEntrenamiento planEntrenamiento) {
        this.planEntrenamiento = planEntrenamiento;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public List<ServicioAdicional> getLstServiciosAdicionalesPlanEntrenamiento() {
        return lstServiciosAdicionalesPlanEntrenamiento;
    }

    public void setLstServiciosAdicionalesPlanEntrenamiento(List<ServicioAdicional> lstServiciosAdicionalesPlanEntrenamiento) {
        this.lstServiciosAdicionalesPlanEntrenamiento = lstServiciosAdicionalesPlanEntrenamiento;
    }
}