package parcial.appv2_smartgym.model;

import java.util.List;
import java.util.ArrayList;

public class Entrenador {
    private String identificacion;
    private String nombre;
    private String especialidad;
    private String telefono;
    private double tarifaSesion;

    // Relaciones (Multiplicidad 'n' mostradas en el diagrama UML mediante asociaciones)
    private List<Inscripcion> lstInscripcionesEntrenador;
    private List<Personalizado> lstPlanPersonalizadoEntrenador;

    public Entrenador(String identificacion, String nombre, String especialidad, String telefono, double tarifaSesion) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.tarifaSesion = tarifaSesion;

        // Inicialización de las listas de las relaciones para evitar NullPointerException
        this.lstInscripcionesEntrenador = new ArrayList<>();
        this.lstPlanPersonalizadoEntrenador = new ArrayList<>();
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getTarifaSesion() {
        return tarifaSesion;
    }

    public void setTarifaSesion(double tarifaSesion) {
        this.tarifaSesion = tarifaSesion;
    }

    public List<Inscripcion> getLstInscripcionesEntrenador() {
        return lstInscripcionesEntrenador;
    }

    public void setLstInscripcionesEntrenador(List<Inscripcion> lstInscripcionesEntrenador) {
        this.lstInscripcionesEntrenador = lstInscripcionesEntrenador;
    }

    public List<Personalizado> getLstPlanPersonalizadoEntrenador() {
        return lstPlanPersonalizadoEntrenador;
    }

    public void setLstPlanPersonalizadoEntrenador(List<Personalizado> lstPlanPersonalizadoEntrenador) {
        this.lstPlanPersonalizadoEntrenador = lstPlanPersonalizadoEntrenador;
    }
}