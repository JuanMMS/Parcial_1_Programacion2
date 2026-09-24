package uniquindio.app_smartgym.model;

public class Gimnasio {
    private String nombre;
    private String direccion;
    private String ID;


    public Gimnasio(String nombre, String direccion, String ID) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ID = ID;
    }


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

}
