package com.juls.firstapp.librarymanagementsystem.controller.dashboard;


import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    public TextField globalSearchField;
    public Label totalResourcesLabel;
    public Label activeUsersLabel;
    public Label pendingReturnsLabel;
    public Label availabilityLabel;
    public LineChart usageChart;
    public PieChart categoryChart;
    public TableColumn activityDateColumn;

    @FXML
    private HBox dashboard;

    @FXML private Button dashboardButton;

    @FXML private Button resourceButton;

    @FXML private Button userButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        handleDashboardButtonClick();
    }


    @FXML
    private void handleDashboardButtonClick() {

        try {
            Stage stage = (Stage) dashboard.getScene().getWindow();
            double height = stage.getHeight();
            double width = stage.getWidth();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("modernDashBoard.fxml"));
            Scene scene = new Scene(root,width,height);
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleResourceButtonClick(){

        try {
            Stage stage = (Stage) dashboard.getScene().getWindow();
            double height = stage.getHeight();
            double width = stage.getWidth();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("book-view.fxml"));
            Scene scene = new Scene(root,width,height);
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void handleUserButtonClick() {
        try {
            Stage stage = (Stage) dashboard.getScene().getWindow();
            double height = stage.getHeight();
            double width = stage.getWidth();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("patron-view.fxml"));
            Scene scene = new Scene(root,width,height);
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
 }