package uniquindio.app_smartgym.model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Cliente {
    private String nombreCompleto;
    private String documentoIdentidad;
    private int edad;
    private String telefono;
    private String correoElectronico;
    private Date fechaRegistro;

    // Relaciones (Multiplicidad 'n' mostradas en el diagrama UML mediante asociaciones)
    private List<Inscripcion> lstInscripcionesCliente;
    private List<PlanEntrenamiento> lstPlanesEntrenamientoCliente;

    /*
    Constructor:
    Este es el constructor de nuestra clase cliente.
    */
    public Cliente(String nombreCompleto, String documentoIdentidad, int edad, String telefono, String correoElectronico, Date fechaRegistro) {
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.edad = edad;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.fechaRegistro = fechaRegistro;

        // Inicialización de las listas de las relaciones para evitar NullPointerException
        this.lstInscripcionesCliente = new ArrayList<>();
        this.lstPlanesEntrenamientoCliente = new ArrayList<>();
    }

    //Getters y Setters

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Inscripcion> getLstInscripcionesCliente() {
        return lstInscripcionesCliente;
    }

    public void setLstInscripcionesCliente(List<Inscripcion> lstInscripcionesCliente) {
        this.lstInscripcionesCliente = lstInscripcionesCliente;
    }

    public List<PlanEntrenamiento> getLstPlanesEntrenamientoCliente() {
        return lstPlanesEntrenamientoCliente;
    }

    public void setLstPlanesEntrenamientoCliente(List<PlanEntrenamiento> lstPlanesEntrenamientoCliente) {
        this.lstPlanesEntrenamientoCliente = lstPlanesEntrenamientoCliente;
    }
}