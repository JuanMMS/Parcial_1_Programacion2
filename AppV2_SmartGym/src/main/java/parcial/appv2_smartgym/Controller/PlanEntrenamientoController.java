package parcial.appv2_smartgym.Controller;

import parcial.appv2_smartgym.model.Estado;
import parcial.appv2_smartgym.model.Gimnasio;
import parcial.appv2_smartgym.model.PlanEntrenamiento;

import java.util.List;

public class PlanEntrenamientoController {

    private Gimnasio gimnasio;

    public PlanEntrenamientoController() {
        this.gimnasio = Gimnasio.getInstance();
    }

    public boolean crearPlan(String codigo, String nombre, String descripcion, int duracionMeses, double valorMensual, Estado estado) {
        PlanEntrenamiento nuevoPlan = new PlanEntrenamiento(codigo, nombre, descripcion, duracionMeses, valorMensual);
        if (estado != null) {
            nuevoPlan.setEstado(estado);
        }
        return gimnasio.agregarPlanEntrenamiento(nuevoPlan);
    }

    public boolean actualizarPlan(String codigoOriginal, String codigoNuevo, String nombre, String descripcion, int duracionMeses, double valorMensual, Estado estado) {
        PlanEntrenamiento planActualizado = new PlanEntrenamiento(codigoNuevo, nombre, descripcion, duracionMeses, valorMensual);
        if (estado != null) {
            planActualizado.setEstado(estado);
        }
        return gimnasio.actualizarPlanEntrenamiento(codigoOriginal, planActualizado);
    }

    public boolean eliminarPlan(String codigo) {
        return gimnasio.eliminarPlanEntrenamiento(codigo);
    }

    public List<PlanEntrenamiento> obtenerListaPlanes() {
        return gimnasio.getListPlanesEntrenamientos();
    }
}