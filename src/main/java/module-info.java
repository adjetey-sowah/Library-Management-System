module com.juls.firstapp.librarymanagementsystem {
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires org.kordamp.ikonli.core;
    requires java.sql;
    requires mysql.connector.j;
    requires io.github.cdimascio.dotenv.java;
    requires spring.security.crypto;
    requires spring.context;
    requires org.slf4j;
    requires de.jensd.fx.glyphs.fontawesome;

    exports com.juls.firstapp.librarymanagementsystem.controller.dashboard to javafx.fxml;
    opens com.juls.firstapp.librarymanagementsystem.controller.dashboard to javafx.fxml;
    exports com.juls.firstapp.librarymanagementsystem.controller.patron to  javafx.fxml;
    opens com.juls.firstapp.librarymanagementsystem.controller.patron to  javafx.fxml;
    exports com.juls.firstapp.librarymanagementsystem.controller.book to  javafx.fxml;
    opens com.juls.firstapp.librarymanagementsystem.controller.book to  javafx.fxml;
    exports com.juls.firstapp.librarymanagementsystem.controller.auth to javafx.fxml;
    opens com.juls.firstapp.librarymanagementsystem.controller.auth to javafx.fxml;
    exports com.juls.firstapp.librarymanagementsystem.controller to javafx.fxml;
    opens com.juls.firstapp.librarymanagementsystem.controller to javafx.fxml;


    opens com.juls.firstapp.librarymanagementsystem to javafx.fxml;
    opens com.juls.firstapp.librarymanagementsystem.dao.dto to javafx.base;
    exports com.juls.firstapp.librarymanagementsystem;
    exports com.juls.firstapp.librarymanagementsystem.model.resource;
    exports com.juls.firstapp.librarymanagementsystem.model.enums;
    exports com.juls.firstapp.librarymanagementsystem.model.users;
    exports com.juls.firstapp.librarymanagementsystem.model.lending;
}