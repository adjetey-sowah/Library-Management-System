package com.juls.firstapp.librarymanagementsystem;

import com.juls.firstapp.librarymanagementsystem.views.images.DashboardView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("modernDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 960, 640);
        stage.setTitle("Library Management System");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}