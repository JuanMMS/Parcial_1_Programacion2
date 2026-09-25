package uniquindio.app_smartgym.model;

import java.util.List;
import java.util.ArrayList;

public class Gimnasio {
    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String paginaWeb;

    // Relaciones de composición/asociación (Diamantes negros en el UML hacia listas de elementos 'n')
    private List<Cliente> lstClientes;
    private List<Entrenador> lstEntrenadores;
    private List<AbstractPlanEntrenamiento> lstPlanesEntrenamientos;
    private List<ServicioAdicional> lstServiciosAdicionales;
    private List<Inscripcion> lstInscripciones;

    // Relación de asociación (Multiplicidad 1)
    private FactoryPlanEntrenamiento factoryPlanEntrenamiento;

    public Gimnasio(String nombreComercial, String nit, String direccion, String telefono, String correoElectronico, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.paginaWeb = paginaWeb;

        // Inicialización de las listas de las relaciones para evitar NullPointerException
        this.lstClientes = new ArrayList<>();
        this.lstEntrenadores = new ArrayList<>();
        this.lstPlanesEntrenamientos = new ArrayList<>();
        this.lstServiciosAdicionales = new ArrayList<>();
        this.lstInscripciones = new ArrayList<>();
    }

    // Métodos indicados en el diagrama UML
    public void CRUDClientes() {}
    public void CRUDPlanesEntrenamiento() {}
    public void CRUDEntrenador() {}
    public void CRUDServiciosAdicionales() {}
    public void CRUDInscripcion() {}
    public void BuscarClientePorTelefono() {}
    public void ValidarNumeroPerfecto() {}
    public void CalcularIngresosPeriodo() {}

    // Getters y Setters
    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public List<Cliente> getLstClientes() {
        return lstClientes;
    }

    public void setLstClientes(List<Cliente> lstClientes) {
        this.lstClientes = lstClientes;
    }

    public List<Entrenador> getLstEntrenadores() {
        return lstEntrenadores;
    }

    public void setLstEntrenadores(List<Entrenador> lstEntrenadores) {
        this.lstEntrenadores = lstEntrenadores;
    }

    public List<AbstractPlanEntrenamiento> getLstPlanesEntrenamientos() {
        return lstPlanesEntrenamientos;
    }

    public void setLstPlanesEntrenamientos(List<AbstractPlanEntrenamiento> lstPlanesEntrenamientos) {
        this.lstPlanesEntrenamientos = lstPlanesEntrenamientos;
    }

    public List<ServicioAdicional> getLstServiciosAdicionales() {
        return lstServiciosAdicionales;
    }

    public void setLstServiciosAdicionales(List<ServicioAdicional> lstServiciosAdicionales) {
        this.lstServiciosAdicionales = lstServiciosAdicionales;
    }

    public List<Inscripcion> getLstInscripciones() {
        return lstInscripciones;
    }

    public void setLstInscripciones(List<Inscripcion> lstInscripciones) {
        this.lstInscripciones = lstInscripciones;
    }

    public FactoryPlanEntrenamiento getFactoryPlanEntrenamiento() {
        return factoryPlanEntrenamiento;
    }

    public void setFactoryPlanEntrenamiento(FactoryPlanEntrenamiento factoryPlanEntrenamiento) {
        this.factoryPlanEntrenamiento = factoryPlanEntrenamiento;
    }
}