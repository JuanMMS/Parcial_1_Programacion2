module org.example.programa_smartgym {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.programa_smartgym to javafx.fxml;
    exports org.example.programa_smartgym;
}