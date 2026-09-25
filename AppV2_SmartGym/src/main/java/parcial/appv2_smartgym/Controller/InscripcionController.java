package parcial.appv2_smartgym.Controller;

import parcial.appv2_smartgym.model.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador de negocio para la gestión del proceso de Inscripciones.
 * Conecta la interfaz de usuario con el modelo Singleton Gimnasio.
 */
public class InscripcionController {

    private Gimnasio gimnasio;

    public InscripcionController() {
        this.gimnasio = Gimnasio.getInstance();
    }

    /**
     * Registra una nueva inscripción en el sistema calculando su valor total a pagar.
     */
    public Inscripcion crearInscripcion(String codigo, LocalDate fecha, Cliente cliente,
                                       PlanEntrenamiento plan, double porcentajeDescuento,
                                       List<ServicioAdicional> servicios) throws Exception {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de inscripción no puede estar vacío.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("Debe seleccionar un cliente.");
        }
        if (plan == null) {
            throw new IllegalArgumentException("Debe seleccionar un plan de entrenamiento.");
        }
        if (buscarInscripcionPorCodigo(codigo) != null) {
            throw new Exception("Ya existe una inscripción registrada con el código: " + codigo);
        }

        Inscripcion nuevaInscripcion = new Inscripcion(codigo, fecha, cliente, plan);

        if (servicios != null) {
            for (ServicioAdicional servicio : servicios) {
                nuevaInscripcion.agregarServicioAdicional(servicio);
            }
        }

        nuevaInscripcion.EstablecerDescuento();
        nuevaInscripcion.CalcularValorAPagar();

        gimnasio.getListInscripciones().add(nuevaInscripcion);
        return nuevaInscripcion;
    }

    /**
     * Obtiene la lista completa de inscripciones registradas.
     */
    public List<Inscripcion> obtenerTodasInscripciones() {
        return gimnasio.getListInscripciones();
    }

    /**
     * Obtiene la lista de clientes registrados en el gimnasio para poblar los controles de la UI.
     */
    public List<Cliente> obtenerClientesDisponibles() {
        return gimnasio.getListClientes();
    }

    /**
     * Obtiene la lista de planes de entrenamiento disponibles para selección.
     */
    public List<PlanEntrenamiento> obtenerPlanesDisponibles() {
        return gimnasio.getListPlanesEntrenamientos();
    }

    /**
     * Obtiene la lista de servicios adicionales configurados en el gimnasio.
     */
    public List<ServicioAdicional> obtenerServiciosDisponibles() {
        return gimnasio.getListServiciosAdicionales();
    }

    /**
     * Busca una inscripción por su código único.
     */
    public Inscripcion buscarInscripcionPorCodigo(String codigo) {
        for (Inscripcion inscripcion : obtenerTodasInscripciones()) {
            if (inscripcion.getiD().equalsIgnoreCase(codigo)) {
                return inscripcion;
            }
        }
        return null;
    }

    /**
     * Elimina una inscripción registrada.
     */
    public boolean eliminarInscripcion(String codigo) throws Exception {
        Inscripcion inscripcion = buscarInscripcionPorCodigo(codigo);
        if (inscripcion == null) {
            throw new Exception("No existe ninguna inscripción con el código: " + codigo);
        }
        return gimnasio.getListInscripciones().remove(inscripcion);
    }

    /**
     * Consulta los ingresos acumulados por inscripciones en un periodo de tiempo.
     */
    public double calcularIngresosPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        return gimnasio.CalcularIngresosPeriodo(fechaInicio, fechaFin);
    }

    public Gimnasio getGimnasio() {
        return gimnasio;
    }
}
