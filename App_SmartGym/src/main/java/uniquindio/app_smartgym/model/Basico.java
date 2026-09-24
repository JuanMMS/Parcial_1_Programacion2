package uniquindio.app_smartgym.Model;

public class Basico extends PlanEntrenamiento{
    private String nombre;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private uniquindio.app_smartgym.model.Estado estado;

    public Basico(String codigo) {
        super(codigo);
        this.nombre = "Plan Basico";
        this.descripcion = "Este es el plan basico, te damos lo basico!";
        this.duracionMeses = 3;
        this.valorMensual = 50000;
        this.estado = uniquindio.app_smartgym.model.Estado.ACTIVO;

    }

}
