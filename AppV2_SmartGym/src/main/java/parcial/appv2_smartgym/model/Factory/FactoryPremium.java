package parcial.appv2_smartgym.model.Factory;

import parcial.appv2_smartgym.model.PlanEntrenamiento;
import parcial.appv2_smartgym.model.Premium;

public class FactoryPremium extends FactoryPlanEntrenamiento {

    @Override
    public PlanEntrenamiento crearPlanEntrenamiento(String codigo) {
        return new Premium(codigo);
    }
}
