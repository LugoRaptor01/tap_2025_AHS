module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires java.sql;
    requires java.desktop;
    requires com.google.protobuf;
    opens  com.example.demo2.modelos;
}