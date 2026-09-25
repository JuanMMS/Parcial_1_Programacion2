package parcial.appv2_smartgym.Controller;

import parcial.appv2_smartgym.model.Cliente;
import parcial.appv2_smartgym.model.Gimnasio;

import java.util.Date;
import java.util.List;

public class ClienteController {

    private Gimnasio gimnasio;

    public ClienteController() {
        // Obtenemos la instancia única del modelo
        this.gimnasio = Gimnasio.getInstance();
    }

    public boolean crearCliente(String nombre, String documento, int edad, String telefono, String correo) {
        Cliente nuevoCliente = new Cliente(nombre, documento, edad, telefono, correo, new Date());
        return gimnasio.agregarCliente(nuevoCliente);
    }

    public boolean actualizarCliente(String documentoOriginal, String nombre, String documentoNuevo, int edad, String telefono, String correo, Date fechaRegistro) {
        Cliente clienteActualizado = new Cliente(nombre, documentoNuevo, edad, telefono, correo, fechaRegistro);
        return gimnasio.actualizarCliente(documentoOriginal, clienteActualizado);
    }

    public boolean eliminarCliente(String documento) {
        return gimnasio.eliminarCLiente(documento);
    }

    public List<Cliente> obtenerListaClientes() {
        return gimnasio.getListClientes();
    }

    public Cliente buscarClientePorTelefono(String telefono) {
        return gimnasio.buscarClientePorTelefono(telefono);
    }

    public boolean validarNumeroPerfecto(String numero) {
        return gimnasio.ValidarNumeroPerfecto(numero);
    }
}