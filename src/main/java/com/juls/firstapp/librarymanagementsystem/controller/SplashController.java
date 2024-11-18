package com.juls.firstapp.librarymanagementsystem.controller;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SplashController {

    @FXML
    private ImageView logo;

    @FXML
    private Text loadingText;

    public void initialize() {
        // Load logo image
        Image image = new Image(getClass().getResourceAsStream("/path/to/your/logo.png")); // Replace with your logo path
        logo.setImage(image);
    }
}