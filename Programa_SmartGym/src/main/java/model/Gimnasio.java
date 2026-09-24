package model;

import java.util.List;

public class Gimnasio {
        private String nombre;
        private String direccion;
        private String ID;

        // Atributos de asociación (Agregación/Composición)
        private List<Cliente> listaClientes;
        private List<PlanEntrenamiento> listaPlanes;
        private List<ServicioAdicional> listaServiciosAdicionales;
        private List<Entrenador> listaEntrenadores;

    public Gimnasio(String nombre, String direccion, String id) {
        this.nombre = nombre;
        this.direccion = direccion;
        ID = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
