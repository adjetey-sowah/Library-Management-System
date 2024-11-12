module com.juls.firstapp.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires  java.base;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.kordamp.ikonli.core;
    requires java.sql;
    requires mysql.connector.j;

    exports com.juls.firstapp.librarymanagementsystem.model;

    opens com.juls.firstapp.librarymanagementsystem to javafx.fxml;
    exports com.juls.firstapp.librarymanagementsystem;
}