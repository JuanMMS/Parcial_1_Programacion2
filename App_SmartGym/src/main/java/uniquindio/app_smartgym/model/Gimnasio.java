package uniquindio.app_smartgym.model;
import uniquindio.app_smartgym.model.Factory.*;
import java.util.List;
import java.util.ArrayList;

public class Gimnasio {

    // 1. Instancia estática privada (Singleton)

    private static Gimnasio instancia;

    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String paginaWeb;

    // Relaciones de composición/asociación
    private List<Cliente> lstClientes;
    private List<Entrenador> lstEntrenadores;
    private List<PlanEntrenamiento> lstPlanesEntrenamientos;
    private List<ServicioAdicional> lstServiciosAdicionales;
    private List<Inscripcion> lstInscripciones;

    // Relación de asociación (Factory)
    private FactoryPlanEntrenamiento factoryPlanEntrenamiento;

    /*
     * 2. Constructor PRIVADO: Evita que se creen instancias con "new" desde fuera de la clase.
     * Al ser un Singleton de un gimnasio específico, podemos inicializarlo con valores por defecto.
     */

    private Gimnasio() {
        // Datos quemados por defecto (Según el contexto del documento)
        this.nombreComercial = "SmartGym";
        this.nit = "900.123.456-7";
        this.direccion = "Calle Principal 123";
        this.telefono = "3001234567";
        this.correoElectronico = "contacto@smartgym.com";
        this.paginaWeb = "www.smartgym.com";

        // Inicialización de las listas de las relaciones
        this.lstClientes = new ArrayList<>();
        this.lstEntrenadores = new ArrayList<>();
        this.lstPlanesEntrenamientos = new ArrayList<>();
        this.lstServiciosAdicionales = new ArrayList<>();
        this.lstInscripciones = new ArrayList<>();
    }

    /*
     * Singleton
     */

    public static Gimnasio getInstance() {
        if (instancia == null) {
            instancia = new Gimnasio();
        }
        return instancia;
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

    public List<PlanEntrenamiento> getLstPlanesEntrenamientos() {
        return lstPlanesEntrenamientos;
    }

    public void setLstPlanesEntrenamientos(List<PlanEntrenamiento> lstPlanesEntrenamientos) {
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