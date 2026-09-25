package uniquindio.app_smartgym.model;

public class Personalizado extends uniquindio.app_smartgym.Model.PlanEntrenamiento {

    private int cantidadSesiones;
    private String especialidadRequerida;
    private String objetivosCliente;

    public Personalizado(String codigo, int cantidadSesiones, String especialidadRequerida, String objetivosCliente) {
        super(codigo,
                "plan personalizado",
                "Este es un plan personalizado, armalo como quieras!",
                12,
                100000);
    }
    this.cantidadSesiones = cantidadSesiones;
    
}
