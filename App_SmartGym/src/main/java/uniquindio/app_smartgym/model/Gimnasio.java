package uniquindio.app_smartgym.model;


import uniquindio.app_smartgym.model.Factory.FactoryPlanEntrenamiento;

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

    private List<Cliente> listClientes;
    private List<Entrenador> listEntrenadores;
    private List<uniquindio.app_smartgym.model.PlanEntrenamiento> listPlanesEntrenamientos;
    private List<ServicioAdicional> listServiciosAdicionales;
    private List<Inscripcion> listInscripciones;


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
        this.listClientes = new ArrayList<>();
        this.listEntrenadores = new ArrayList<>();
        this.listPlanesEntrenamientos = new ArrayList<>();
        this.listServiciosAdicionales = new ArrayList<>();
        this.listInscripciones = new ArrayList<>();
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

    /**
     * Metodo para buscar clientes de un gimnasio, siendo el READ de clientes
     * @param numeroTelefono
     * @return Cliente
     */
    public Cliente buscarClientePorTelefono(String numeroTelefono){
        for (Cliente cliente : listClientes) {
            if (cliente.getTelefono().equals(numeroTelefono)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Metodo para agregar clientes a gimnasio, parte crud
     * @param cliente
     * @return Boolean
     */
    public boolean agregarCliente(Cliente cliente) {
        boolean centinela = false;
        if (!verificarCliente(cliente.getDocumentoIdentidad())){
            listClientes.add(cliente);
            centinela = true;
        }
        return centinela;
    }

    /**
     * Metodo para verificar la existencia de un cliente en la lista de clientes del gimnasio
     * @param numeroIdentificacion
     * @return boolean
     */
    public boolean verificarCliente(String numeroIdentificacion) {
        boolean centinela = false;
        for (Cliente cliente : listClientes) {
            if (cliente.getDocumentoIdentidad().equals(numeroIdentificacion)) {
                centinela = true;
            }
        }
        return centinela;
    }

    /**
     * Metodo para eliminar cliente determinado de un gimnasio, hace parte del CRUD
     * @param numeroIdentificacion
     * @return boolean
     */
    public boolean eliminarCLiente(String numeroIdentificacion) {
        boolean centinela = false;
        for (Cliente cliente : listClientes) {
            if (cliente.getDocumentoIdentidad().equals(numeroIdentificacion)) {
                listClientes.remove(cliente);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean actualizarCliente(String identificacion, Cliente actualizado){
        boolean centinela = false;
        for (Cliente cliente : listClientes) {
            if (cliente.getDocumentoIdentidad().equals(identificacion)) {
                cliente.setNombreCompleto(actualizado.getNombreCompleto());
                cliente.setDocumentoIdentidad(actualizado.getDocumentoIdentidad());
                cliente.setEdad(actualizado.getEdad());
                cliente.setTelefono(actualizado.getTelefono());
                cliente.setCorreoElectronico(actualizado.getCorreoElectronico());
            }
        }
        return centinela;
    }

    /**
     * Metodo para agregar entrenador a la lista de entrenadores del gimnasio, hace parte del crud
     * @param entrenador
     * @return boolean
     */
    public boolean agregarEntrenador(Entrenador entrenador) {
        boolean centinela = false;
        if(!listEntrenadores.contains(entrenador)){
            listEntrenadores.add(entrenador);
            centinela = true;
        }
        return centinela;
    }


    /**
     * Metodo para eliminar entenadores del gimnasio, hace parte del crud
     * @param numeroIdentificacion
     * @return boolean
     */
    public boolean eliminarEntrenador(String numeroIdentificacion) {
        boolean centinela = false;
        for (Entrenador entrenador : listEntrenadores) {
            if(entrenador.getIdentificacion().equals(numeroIdentificacion)){
                listEntrenadores.remove(entrenador);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    /**
     * Metodo para actualizar los datos de un entrenador existente en gimnasio, hace parte del CRUD
     * @param identificacion
     * @param entrenador
     * @return boolean
     */
    public boolean actualizarEntrenador(String identificacion, Entrenador entrenador) {
        boolean centinela = false;
        for(Entrenador entrenadorDeLista : listEntrenadores){
            if(entrenadorDeLista.getIdentificacion().equals(identificacion)){
                entrenador.setIdentificacion(identificacion);
                entrenador.setNombre(entrenador.getNombre());
                entrenador.setEspecialidad(entrenador.getEspecialidad());
                entrenador.setTelefono(entrenador.getTelefono());
                entrenador.setTarifaSesion(entrenador.getTarifaSesion());
                centinela = true;
                break;
            }
        }
        return centinela;
    }




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

    public List<Cliente> getiLstClientes() {
        return listClientes;
    }

    public void setLstClientes(List<Cliente> lstClientes) {
        this.listClientes = lstClientes;
    }

    public List<Entrenador> getLstEntrenadores() {
        return listEntrenadores;
    }

    public void setLstEntrenadores(List<Entrenador> lstEntrenadores) {
        this.listEntrenadores = lstEntrenadores;
    }


    public List<uniquindio.app_smartgym.model.PlanEntrenamiento> getLstPlanesEntrenamientos() {
        return listPlanesEntrenamientos;
    }

    public void setListPlanesEntrenamientos(List<uniquindio.app_smartgym.model.PlanEntrenamiento> lstPlanesEntrenamientos) {
        this.listPlanesEntrenamientos = lstPlanesEntrenamientos;

    }

    public List<ServicioAdicional> getLstServiciosAdicionales() {
        return listServiciosAdicionales;
    }

    public void setLstServiciosAdicionales(List<ServicioAdicional> lstServiciosAdicionales) {
        this.listServiciosAdicionales = lstServiciosAdicionales;
    }

    public List<Inscripcion> getLstInscripciones() {
        return listInscripciones;
    }

    public void setLstInscripciones(List<Inscripcion> lstInscripciones) {
        this.listInscripciones = lstInscripciones;
    }

    public FactoryPlanEntrenamiento getFactoryPlanEntrenamiento() {
        return factoryPlanEntrenamiento;
    }

    public void setFactoryPlanEntrenamiento(FactoryPlanEntrenamiento factoryPlanEntrenamiento) {
        this.factoryPlanEntrenamiento = factoryPlanEntrenamiento;
    }
}