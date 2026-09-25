module uniquindio.app_smartgym {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens uniquindio.app_smartgym.app to javafx.fxml;
    exports uniquindio.app_smartgym.app;

    opens uniquindio.app_smartgym.ViewController to javafx.fxml;
    exports uniquindio.app_smartgym.ViewController;

    opens uniquindio.app_smartgym.model to javafx.fxml;
    exports uniquindio.app_smartgym.model;

    opens uniquindio.app_smartgym.Controller to javafx.fxml;
    exports uniquindio.app_smartgym.Controller;
}