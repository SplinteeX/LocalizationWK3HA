module com.example.wk3localizationha {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.wk3localizationha to javafx.fxml;
    exports com.example.wk3localizationha;
}