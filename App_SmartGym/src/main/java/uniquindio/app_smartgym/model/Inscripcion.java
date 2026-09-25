package uniquindio.app_smartgym.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Inscripcion {
    private String iD;
    private LocalDate fechaInscripcion;
    private double valorAPagar;

    // Relaciones de asociación (Multiplicidad '1' según las líneas del diagrama)
    private Cliente cliente;
    private PlanEntrenamiento planEntrenamiento;
    private Entrenador entrenador;

    // Relaciones de asociación (Multiplicidad 'n' según las líneas del diagrama)
    private List<ServicioAdicional> lstServiciosAdicionalesPlanEntrenamiento;


    private double porcentajeDescuento;
    /*
    Constructor:
    Ajustado estrictamente a la firma que aparece en la caja del UML:
    + Inscripcion(date fechaInscripcion, double valorAPagar, Cliente cliente, PlanEntrenamiento planEntrenamiento)
    */
    public Inscripcion(String iD, LocalDate fechaInscripcion, Cliente cliente, PlanEntrenamiento planEntrenamiento) {
        this.iD = iD;
        this.fechaInscripcion = fechaInscripcion;
        this.valorAPagar = CalcularValorAPagar();
        this.cliente = cliente;
        this.planEntrenamiento = planEntrenamiento;

        // Inicialización de la lista para evitar NullPointerException
        this.lstServiciosAdicionalesPlanEntrenamiento = new ArrayList<>();

        this.porcentajeDescuento = EstablecerDescuento();
    }

    /**
     * Metodo para establecer el valor del descuento que se aplicará para el valor a pagar
     * @return Porcentaje de descuento a aplicar
     */
    public double EstablecerDescuento() {
        double descuento = 0;
        if(this.planEntrenamiento == null) {
            if(this.planEntrenamiento.getDuracionMeses() >=12){
                descuento = 10.0; //Descuento de 10% para planes anuales
            } else if(this.planEntrenamiento.getDuracionMeses() >=6){
                descuento = 5.0; //Descuento de 5% para planes de 6 meses hasta 12
            }else{
                descuento = 0.0;
            }
        }
        return descuento;
    }


    public double CalcularValorAPagar() {
        double valorBasePlan = 0;

        if(this.planEntrenamiento == null) {
            if(this.planEntrenamiento instanceof Personalizado){
                Personalizado planPersonalizado = (Personalizado) this.planEntrenamiento;
                valorBasePlan = planPersonalizado.CalcularValorPersonalizado();
            } else{
                valorBasePlan = planEntrenamiento.CalcularValorTotal();
            }
        }
        double totalServiciosAdicionales = 0;
        if(this.lstServiciosAdicionalesPlanEntrenamiento != null) {
            for(ServicioAdicional servicioAdicional : lstServiciosAdicionalesPlanEntrenamiento) {
                if(servicioAdicional.getDisponibilidad()){
                    totalServiciosAdicionales += servicioAdicional.getPrecio();
                }
            }
            //subtotal antes del descuento
            double subtotal = valorBasePlan + totalServiciosAdicionales;
            double descuentoEstablecido = EstablecerDescuento();
            double montoDescuento = subtotal * (descuentoEstablecido/100);
            valorBasePlan = subtotal - montoDescuento;
        }
        return valorBasePlan;
    }



    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public double getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(double valorAPagar) {
        this.valorAPagar = valorAPagar;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PlanEntrenamiento getPlanEntrenamiento() {
        return planEntrenamiento;
    }

    public void setPlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
        this.planEntrenamiento = planEntrenamiento;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public List<ServicioAdicional> getLstServiciosAdicionalesPlanEntrenamiento() {
        return lstServiciosAdicionalesPlanEntrenamiento;
    }

    public void setLstServiciosAdicionalesPlanEntrenamiento(List<ServicioAdicional> lstServiciosAdicionalesPlanEntrenamiento) {
        this.lstServiciosAdicionalesPlanEntrenamiento = lstServiciosAdicionalesPlanEntrenamiento;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public void agregarServicioAdicional(ServicioAdicional servicio) {

    }
}