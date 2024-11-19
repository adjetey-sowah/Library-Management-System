package com.juls.firstapp.librarymanagementsystem.controller.auth;

import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import com.juls.firstapp.librarymanagementsystem.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AuthController implements Initializable {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorMessage;
    @FXML private AnchorPane loginPane;

    private AuthenticationService authenticationService;

    public AuthController(){
        try {
            this.authenticationService = new AuthenticationService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleLoginRequest() throws SQLException, IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (authenticationService.isAuthenticated(email,password)){
            switchToDashBoard();
        }
            errorMessage.setVisible(true);
        errorMessage.setStyle("--body-background-color: red");
        errorMessage.setStyle("--body-text-color: white");
    }

    @FXML
    private void switchToDashBoard() throws IOException {
        Stage stage = (Stage) loginPane.getScene().getWindow();

        Parent root = FXMLLoader.load(HelloApplication.class.getResource("modernDashboard.fxml"));
        double width = stage.getWidth();
        double height = stage.getHeight();
        Scene scene = new Scene(root,width,height);
        stage.setScene(scene);
        stage.setTitle("Library Management System");
        stage.show();
    }
}
