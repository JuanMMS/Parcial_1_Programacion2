package uniquindio.app_smartgym.model;


import uniquindio.app_smartgym.model.Factory.FactoryPlanEntrenamiento;

import java.time.LocalDate;
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

    /**
     * Metodo para actualizar un cliente del gimnasio, hace parte del crud
     * @param identificacion
     * @param actualizado
     * @return boolean
     */

    public boolean actualizarCliente(String identificacion, Cliente actualizado){
        boolean centinela = false;
        for (Cliente cliente : listClientes) {
            if (cliente.getDocumentoIdentidad().equals(identificacion)) {
                cliente.setNombreCompleto(actualizado.getNombreCompleto());
                cliente.setDocumentoIdentidad(actualizado.getDocumentoIdentidad());
                cliente.setEdad(actualizado.getEdad());
                cliente.setTelefono(actualizado.getTelefono());
                cliente.setCorreoElectronico(actualizado.getCorreoElectronico());
                cliente.setFechaRegistro(actualizado.getFechaRegistro());
                centinela = true;
                break;
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
                entrenadorDeLista.setIdentificacion(identificacion);
                entrenadorDeLista.setNombre(entrenador.getNombre());
                entrenadorDeLista.setEspecialidad(entrenador.getEspecialidad());
                entrenadorDeLista.setTelefono(entrenador.getTelefono());
                entrenadorDeLista.setTarifaSesion(entrenador.getTarifaSesion());
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    /**
     * Metodo para agregar planes de entrenamiento al gimnasio, parte del crud
     * @param planEntrenamientoNuevo
     * @return boolean
     */
    public boolean agregarPlanEntrenamiento(PlanEntrenamiento planEntrenamientoNuevo) {
        boolean centinela = false;
        if(!listEntrenadores.contains(planEntrenamientoNuevo)){
        listPlanesEntrenamientos.add(planEntrenamientoNuevo);
        centinela = true;
        }
        return centinela;
    }

    /**
     * Metodo para eliminar planes de entrenamiento del gimnasio, parte del crud
     * @param numeroIdentificacion
     * @return boolean
     */
    public boolean eliminarPlanEntrenamiento(String numeroIdentificacion) {
        boolean centinela = false;
        for (PlanEntrenamiento planEntrenamiento : listPlanesEntrenamientos) {
            if (planEntrenamiento.getCodigo().equals(numeroIdentificacion)) {
                listPlanesEntrenamientos.remove(planEntrenamiento);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    /**
     * Metodo para actualizar planes de entrenamiento del gimnasio, parte del crud
     * @param identificacion
     * @param planEntrenamientoNuevo
     * @return boolean
     */
    public boolean actualizarPlanEntrenamiento(String identificacion, PlanEntrenamiento planEntrenamientoNuevo) {
        boolean centinela = false;
        for(PlanEntrenamiento planEntrenamiento : listPlanesEntrenamientos){
            if(planEntrenamiento.getCodigo().equals(identificacion)) {
                planEntrenamiento.setCodigo(identificacion);
                planEntrenamiento.setNombre(planEntrenamientoNuevo.getNombre());
                planEntrenamiento.setDescripcion(planEntrenamientoNuevo.getDescripcion());
                planEntrenamiento.setDuracionMeses(planEntrenamientoNuevo.getDuracionMeses());
                planEntrenamiento.setValorMensual(planEntrenamientoNuevo.getValorMensual());
                planEntrenamiento.setEstado(planEntrenamientoNuevo.getEstado());
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    /**
     * Metodo para agregar Servicios adicionales al gimnasio, hace parte del crud
     * @param servicioAdicional
     * @return boolean
     */
    public boolean agregarServiciosAdicionales(ServicioAdicional servicioAdicional){
        boolean centinela = false;
        if(!listServiciosAdicionales.contains(servicioAdicional)){
            listServiciosAdicionales.add(servicioAdicional);
            centinela = true;
        }
        return centinela;
    }


    /**
     * metodo para eliminar servicios adicional del gimnasio, hace parte del crud
     * @param numeroIdentificacion
     * @return boolean
     */
    public boolean eliminarServiciosAdicionales(String numeroIdentificacion) {
        boolean centinela = false;
        for (ServicioAdicional servicioAdicional : listServiciosAdicionales) {
            if (servicioAdicional.getCodigo().equals(numeroIdentificacion)) {
                listServiciosAdicionales.remove(servicioAdicional);
                centinela = true;
                break;
            }
        }
        return centinela;
    }


    /**
     * metodo para actualizar servicio adicional del gimnasio, hace parte del crud
     * @param codigo
     * @param servicioAdicional
     * @return boolean
     */
    public boolean actualizarServiciosAdicionales(String codigo, ServicioAdicional servicioAdicional) {
        boolean centinela = false;
        for (ServicioAdicional servicioAdicionalLista : listServiciosAdicionales) {
            if (servicioAdicionalLista.getCodigo().equals(codigo)) {
                servicioAdicionalLista.setCodigo(servicioAdicional.getCodigo());
                servicioAdicionalLista.setNombre(servicioAdicional.getNombre());
                servicioAdicionalLista.setDescripcion(servicioAdicional.getDescripcion());
                servicioAdicionalLista.setDescripcion(servicioAdicional.getDescripcion());
                servicioAdicionalLista.setPrecio(servicioAdicional.getPrecio());
                servicioAdicionalLista.setDisponibilidad(servicioAdicional.getDisponibilidad());
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    /**
     * Metodo para agregar una inscripcion al gimnasio, parte del CRUD
     * @param inscripcion
     * @return Boolean
     */
    public boolean agregarInscripcion(Inscripcion inscripcion){
        boolean centinela = false;
        if(!listInscripciones.contains(inscripcion)){
            listInscripciones.add(inscripcion);
            centinela = true;
        }
        return centinela;
    }

    /**
     * Metodo para eliminar una inscripcion del gimnasio, parte del CRUD
     * @param codigo
     * @return
     */
    public boolean eliminarInscripcion(String codigo) {
        boolean centinela = false;
        for (Inscripcion inscripcion : listInscripciones) {
            if (inscripcion.getiD().equals(codigo)) {
                listInscripciones.remove(inscripcion);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    /**
     * Matodo para actualizar una inscripcion del gimnasio, parte del crud
     * @param codigo
     * @param inscripcionActualizada
     * @return
     */
    public boolean actualizarInscripcion(String codigo, Inscripcion inscripcionActualizada) {
        boolean centinela = false;
        for (Inscripcion inscripcion : listInscripciones) {
            if (inscripcion.getiD().equals(codigo)) {
                inscripcion.setiD(inscripcionActualizada.getiD());
                inscripcion.setFechaInscripcion(inscripcionActualizada.getFechaInscripcion());
                inscripcion.setValorAPagar(inscripcionActualizada.getValorAPagar());
                inscripcion.setCliente(inscripcionActualizada.getCliente());
                inscripcion.setPlanEntrenamiento(inscripcionActualizada.getPlanEntrenamiento());
                centinela = true;
                break;
            }
        }
        return centinela;

    }


    public void BuscarClientePorTelefono() {}

    /**
     * Metodo para validar si un numero es perfecto
     * @param numeroPerfecto
     * @return boolean
     */
    public boolean ValidarNumeroPerfecto(String numeroPerfecto) {
        boolean centinela = false;
        if (numeroPerfecto == null || numeroPerfecto.isEmpty()) {
            centinela = false;
        }
        try {
            String numeroLimpio = numeroPerfecto.replaceAll("[^0-9]", "");
            if(numeroLimpio.isEmpty()){
                centinela = false;
            }
            long numero = Long.parseLong(numeroLimpio);
            if(numero<=1){
                centinela = false;
            }
            long sumaDivisores = 0;

            for(long i=1; i<=numero/2 ;i++){
                if(numero%i==0){
                    sumaDivisores+=i;
                }
            }
            return sumaDivisores == numero;
        } catch(NumberFormatException e) {
            centinela = false;
        }
        return centinela;
    }

    /**
     * Metodo para calcular los ingresos del gimnasio en un periodo de tiempo definido
     * @param fechaInicio
     * @param fechaFin
     * @return double total ingresos
     */
    public double CalcularIngresosPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        if(fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("La fecha inicial no puede ser nula");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        double totalIngresos = 0.0;
        for(Inscripcion inscripcion : listInscripciones) {
            LocalDate fechaInscripcion = inscripcion.getFechaInscripcion();

            if(fechaInscripcion != null && (!fechaInscripcion.isBefore(fechaFin) && !fechaInscripcion.isAfter(fechaInicio))) {
                totalIngresos += inscripcion.getValorAPagar();
            }
        }
        return totalIngresos;
    }

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

    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> lstClientes) {
        this.listClientes = lstClientes;
    }

    public List<Entrenador> getiLstEntrenadores() {
        return listEntrenadores;
    }

    public void setListEntrenadores(List<Entrenador> lstEntrenadores) {
        this.listEntrenadores = lstEntrenadores;
    }


    public List<uniquindio.app_smartgym.model.PlanEntrenamiento> getListPlanesEntrenamientos() {
        return listPlanesEntrenamientos;
    }

    public void setListPlanesEntrenamientos(List<uniquindio.app_smartgym.model.PlanEntrenamiento> lstPlanesEntrenamientos) {
        this.listPlanesEntrenamientos = lstPlanesEntrenamientos;

    }

    public List<ServicioAdicional> getListServiciosAdicionales() {
        return listServiciosAdicionales;
    }

    public void setListServiciosAdicionales(List<ServicioAdicional> lstServiciosAdicionales) {
        this.listServiciosAdicionales = lstServiciosAdicionales;
    }

    public List<Inscripcion> getListInscripciones() {
        return listInscripciones;
    }

    public void setListInscripciones(List<Inscripcion> lstInscripciones) {
        this.listInscripciones = lstInscripciones;
    }

    public FactoryPlanEntrenamiento getFactoryPlanEntrenamiento() {
        return factoryPlanEntrenamiento;
    }

    public void setFactoryPlanEntrenamiento(FactoryPlanEntrenamiento factoryPlanEntrenamiento) {
        this.factoryPlanEntrenamiento = factoryPlanEntrenamiento;
    }
}