module uniquindio.app_smartgym {
    requires javafx.controls;
    requires javafx.fxml;


    opens uniquindio.app_smartgym to javafx.fxml;
    exports uniquindio.app_smartgym;
}