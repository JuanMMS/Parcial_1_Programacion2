package uniquindio.app_smartgym.Model;

import uniquindio.app_smartgym.model.Estado;

public class PlanEntrenamiento {
    private String codigo;
    private String nombre;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private uniquindio.app_smartgym.model.Estado estado;


    /**
     * Metodo constructor de la clase PlanEntrenamiento
     */
    public PlanEntrenamiento(String codigo)   {
        this.codigo = codigo;
        this.estado = uniquindio.app_smartgym.model.Estado.ACTIVO;

    }




    //Getters y setters de los atributos de la clase PlanEntrenamiento
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(double valorMensual) {
        this.valorMensual = valorMensual;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
