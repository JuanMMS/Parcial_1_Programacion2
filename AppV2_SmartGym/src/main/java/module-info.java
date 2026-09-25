module uniquindio.app_smartgym {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens parcial.appv2_smartgym.app to javafx.fxml;
    exports parcial.appv2_smartgym.app;

    opens parcial.appv2_smartgym.ViewController to javafx.fxml;
    exports parcial.appv2_smartgym.ViewController;

    opens parcial.appv2_smartgym.model to javafx.fxml;
    exports parcial.appv2_smartgym.model;

    opens parcial.appv2_smartgym.Controller to javafx.fxml;
    exports parcial.appv2_smartgym.Controller;
}