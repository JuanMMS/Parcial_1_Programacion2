package parcial.appv2_smartgym.model.Factory;

import parcial.appv2_smartgym.model.PlanEntrenamiento;
import parcial.appv2_smartgym.model.Basico;

public class FactoryBasico extends FactoryPlanEntrenamiento {

    @Override
    public PlanEntrenamiento crearPlanEntrenamiento(String codigo) {
        return  new Basico(codigo);
    }
}
