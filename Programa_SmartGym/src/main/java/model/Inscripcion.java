package model;

import java.util.Date;

public class Inscripcion {
    private Date fechaInscripcion;
    private double valorAPagar;

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public double getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(double valorAPagar) {
        this.valorAPagar = valorAPagar;
    }

    public Inscripcion(Date fechaInscripcion, double valorAPagar) {
        this.fechaInscripcion = fechaInscripcion;
        this.valorAPagar = valorAPagar;
    }

    // Conecta al Cliente con el Plan
    private Cliente cliente;
    private PlanEntrenamiento planEntrenamiento;
}
