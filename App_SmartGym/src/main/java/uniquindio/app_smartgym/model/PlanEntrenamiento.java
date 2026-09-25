package uniquindio.app_smartgym.Model;

import uniquindio.app_smartgym.model.Estado;
import uniquindio.app_smartgym.model.Inscripcion;
import uniquindio.app_smartgym.model.ServicioAdicional;

import java.util.ArrayList;

public class PlanEntrenamiento {
    private String codigo;
    private String nombre;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private uniquindio.app_smartgym.model.Estado estado;
    private ArrayList<Inscripcion> listInscripcionesPlanEntrenamiento;
    private ArrayList<ServicioAdicional> listServiciosAdicionalesPlanEntrenamiento;

    /**
     * Metodo constructor de la clase PlanEntrenamiento
     */
    public PlanEntrenamiento(String codigo, String nombre, String descripcion, int duracionMeses, double valorMensual)   {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = uniquindio.app_smartgym.model.Estado.ACTIVO;
        this.listInscripcionesPlanEntrenamiento = new ArrayList<>();
        this.listServiciosAdicionalesPlanEntrenamiento = new ArrayList<>();
    }


    /**
     * Metodo para calcular el valor total del plan segun la duracion
     * @return valor total
     */
    public double calcularValorTotal(){
        return duracionMeses * valorMensual;
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

    public ArrayList<Inscripcion> getListInscripcionesPlanEntrenamiento() {
        return listInscripcionesPlanEntrenamiento;
    }

    public void setListInscripcionesPlanEntrenamiento(ArrayList<Inscripcion> listInscripcionesPlanEntrenamiento) {
        this.listInscripcionesPlanEntrenamiento = listInscripcionesPlanEntrenamiento;
    }

    public ArrayList<ServicioAdicional> getListServiciosAdicionalesPlanEntrenamiento() {
        return listServiciosAdicionalesPlanEntrenamiento;
    }

    public void setListServiciosAdicionalesPlanEntrenamiento(ArrayList<ServicioAdicional> listServiciosAdicionalesPlanEntrenamiento) {
        this.listServiciosAdicionalesPlanEntrenamiento = listServiciosAdicionalesPlanEntrenamiento;
    }
}
