package parcial.appv2_smartgym.Controller;

import parcial.appv2_smartgym.model.Entrenador;
import parcial.appv2_smartgym.model.Gimnasio;

import java.util.List;

public class EntrenadorController {

    private Gimnasio gimnasio;

    public EntrenadorController() {
        this.gimnasio = Gimnasio.getInstance();
    }

    public boolean crearEntrenador(String identificacion, String nombre, String especialidad, String telefono, double tarifaSesion) {
        Entrenador nuevoEntrenador = new Entrenador(identificacion, nombre, especialidad, telefono, tarifaSesion);
        return gimnasio.agregarEntrenador(nuevoEntrenador);
    }

    public boolean actualizarEntrenador(String identificacionOriginal, String identificacionNueva, String nombre, String especialidad, String telefono, double tarifaSesion) {
        Entrenador entrenadorActualizado = new Entrenador(identificacionNueva, nombre, especialidad, telefono, tarifaSesion);
        return gimnasio.actualizarEntrenador(identificacionOriginal, entrenadorActualizado);
    }

    public boolean eliminarEntrenador(String identificacion) {
        return gimnasio.eliminarEntrenador(identificacion);
    }

    public List<Entrenador> obtenerListaEntrenadores() {
        return gimnasio.getiLstEntrenadores();
    }
}