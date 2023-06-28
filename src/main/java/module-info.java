module com.example.cregistro {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires jasperreports;

    opens com.example.cregistro to javafx.fxml;
    exports com.example.cregistro;
}