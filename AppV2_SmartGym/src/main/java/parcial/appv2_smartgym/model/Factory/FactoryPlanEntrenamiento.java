package parcial.appv2_smartgym.model.Factory;

public abstract class FactoryPlanEntrenamiento {
    /**
     * Metodo factory de plan entrenamiento
     * @param codigo
     * @return Plan Entrenamiento
     */
    public abstract parcial.appv2_smartgym.model.PlanEntrenamiento crearPlanEntrenamiento(String codigo);


}
