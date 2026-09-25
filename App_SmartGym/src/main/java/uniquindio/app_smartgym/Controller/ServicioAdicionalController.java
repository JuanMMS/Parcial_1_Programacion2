package uniquindio.app_smartgym.Controller;

import uniquindio.app_smartgym.model.Gimnasio;
import uniquindio.app_smartgym.model.ServicioAdicional;
import uniquindio.app_smartgym.model.Tipo;

import java.util.List;

public class ServicioAdicionalController {

    private Gimnasio gimnasio;

    public ServicioAdicionalController() {
        this.gimnasio = Gimnasio.getInstance();
    }

    public boolean crearServicio(String codigo, String nombre, String descripcion, double precio, boolean disponibilidad, Tipo tipo) {
        ServicioAdicional nuevoServicio = new ServicioAdicional(codigo, nombre, descripcion, precio, disponibilidad, tipo);
        return gimnasio.agregarServiciosAdicionales(nuevoServicio);
    }

    public boolean actualizarServicio(String codigoOriginal, String codigoNuevo, String nombre, String descripcion, double precio, boolean disponibilidad, Tipo tipo) {
        ServicioAdicional servicioActualizado = new ServicioAdicional(codigoNuevo, nombre, descripcion, precio, disponibilidad, tipo);
        return gimnasio.actualizarServiciosAdicionales(codigoOriginal, servicioActualizado);
    }

    public boolean eliminarServicio(String codigo) {
        return gimnasio.eliminarServiciosAdicionales(codigo);
    }

    public List<ServicioAdicional> obtenerListaServicios() {
        return gimnasio.getLstServiciosAdicionales();
    }
}