package uniquindio.app_smartgym.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Inscripcion {
    private String iD;
    private LocalDate fechaInscripcion;
    private double valorAPagar;

    // Relaciones de asociación (Multiplicidad '1' según las líneas del diagrama)
    private Cliente cliente;
    private PlanEntrenamiento planEntrenamiento;
    private Entrenador entrenador;

    // Relaciones de asociación (Multiplicidad 'n' según las líneas del diagrama)
    private List<ServicioAdicional> lstServiciosAdicionalesPlanEntrenamiento;

    /*
    Constructor:
    Ajustado estrictamente a la firma que aparece en la caja del UML:
    + Inscripcion(date fechaInscripcion, double valorAPagar, Cliente cliente, PlanEntrenamiento planEntrenamiento)
    */
    public Inscripcion(String iD, LocalDate fechaInscripcion, double valorAPagar, Cliente cliente, PlanEntrenamiento planEntrenamiento) {
        this.iD = iD;
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


    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

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

    public PlanEntrenamiento getPlanEntrenamiento() {
        return planEntrenamiento;
    }

    public void setPlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
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