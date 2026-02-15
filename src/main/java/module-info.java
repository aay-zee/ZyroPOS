module org.example.zyropos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.jfoenix;
    requires java.sql;
    requires java.desktop;
    requires com.zaxxer.hikari;
    requires io.github.cdimascio.dotenv.java;
    requires org.slf4j;
    requires mysql.connector.j;
    requires jbcrypt;
    requires javafx.base;

    opens org.example.zyropos to javafx.fxml;
    exports org.example.zyropos;
}