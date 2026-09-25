package uniquindio.app_smartgym.model.Factory;

import uniquindio.app_smartgym.model.PlanEntrenamiento;
import uniquindio.app_smartgym.model.Basico;

public class FactoryBasico extends FactoryPlanEntrenamiento {

    @Override
    public PlanEntrenamiento crearPlanEntrenamiento(String codigo) {
        return  new Basico(codigo);
    }
}
