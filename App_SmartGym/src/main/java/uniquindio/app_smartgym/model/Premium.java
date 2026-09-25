package uniquindio.app_smartgym.model;

public class Premium extends uniquindio.app_smartgym.model.PlanEntrenamiento {

    public Premium(String codigo) {
        super(codigo,
                "plan Premium",
                "Este es el plan Premium, aqui tienes beneficios Premium!",
                12,
                75000);
    }
}
