package parcial.appv2_smartgym.model;

public class Personalizado extends parcial.appv2_smartgym.model.PlanEntrenamiento {

    private static int cantidadSesiones;
    private String especialidadRequerida;
    private String objetivosCliente;
    private static Entrenador theEntrenadorPersonalizado;

    public Personalizado(String codigo, int cantidadSesiones, String especialidadRequerida, String objetivosCliente, Entrenador theEntrenadorPersonalizado) {
        super(codigo,
                "plan personalizado",

                "Este es un plan personalizado, armalo como quieras y con el entrenador que prefieras!",
                1,
                80000
                );
        this.cantidadSesiones = cantidadSesiones;
        this.especialidadRequerida = especialidadRequerida;
        this.objetivosCliente = objetivosCliente;
        this.theEntrenadorPersonalizado = theEntrenadorPersonalizado;

    }

    /**
     * Metodo para calcular el valor personalizado basado en la cantidad de sesiones y la tarifa del entrenador
     * @return valor del plan personalizado
     */
    public double CalcularValorPersonalizado() {
        return  CalcularValorTotal() + cantidadSesiones * theEntrenadorPersonalizado.getTarifaSesion();
    }


    // Getters y setters de los atributos propios de la clase Personalizado

    public int getCantidadSesiones() {
        return cantidadSesiones;
    }

    public void setCantidadSesiones(int cantidadSesiones) {
        this.cantidadSesiones = cantidadSesiones;
    }

    public String getEspecialidadRequerida() {
        return especialidadRequerida;
    }

    public void setEspecialidadRequerida(String especialidadRequerida) {
        this.especialidadRequerida = especialidadRequerida;
    }

    public String getObjetivosCliente() {
        return objetivosCliente;
    }

    public void setObjetivosCliente(String objetivosCliente) {
        this.objetivosCliente = objetivosCliente;
    }

    public Entrenador getTheEntrenadorPersonalizado() {
        return theEntrenadorPersonalizado;
    }

    public void setTheEntrenadorPersonalizado(Entrenador theEntrenadorPersonalizado) {
        this.theEntrenadorPersonalizado = theEntrenadorPersonalizado;
    }
}
