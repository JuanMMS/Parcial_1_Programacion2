module parcial.appv2_smartgym {
    requires javafx.controls;
    requires javafx.fxml;


    opens parcial.appv2_smartgym to javafx.fxml;
    exports parcial.appv2_smartgym;
}