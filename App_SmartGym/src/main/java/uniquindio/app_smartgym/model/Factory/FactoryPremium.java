package uniquindio.app_smartgym.model.Factory;

import uniquindio.app_smartgym.Model.PlanEntrenamiento;
import uniquindio.app_smartgym.model.Premium;

public class FactoryPremium extends FactoryPlanEntrenamiento {

    @Override
    public PlanEntrenamiento crearPlanEntrenamiento(String codigo) {
        return new Premium(codigo);
    }
}
