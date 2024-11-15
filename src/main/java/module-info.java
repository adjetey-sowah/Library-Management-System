module com.juls.firstapp.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
<<<<<<< HEAD
    requires org.kordamp.ikonli.core;
    requires java.sql;
    requires mysql.connector.j;
    requires io.github.cdimascio.dotenv.java;
    requires spring.security.crypto;
    requires spring.context;

=======
>>>>>>> parent of 3030b5f (Implemented the core entities of the application. Upnext: Work on the application logic and also the interactions)

    opens com.juls.firstapp.librarymanagementsystem to javafx.fxml;
    exports com.juls.firstapp.librarymanagementsystem;
    exports com.juls.firstapp.librarymanagementsystem.model.resource;
    exports com.juls.firstapp.librarymanagementsystem.model.enums;
    exports com.juls.firstapp.librarymanagementsystem.model.users;
    exports com.juls.firstapp.librarymanagementsystem.model.lending;
}