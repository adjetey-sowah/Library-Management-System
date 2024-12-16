package com.juls.firstapp.librarymanagementsystem.controller;


import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ReservationRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.ReservationStatus;
import com.juls.firstapp.librarymanagementsystem.model.lending.Reservations;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {


    @FXML private TableView<Reservations> transactionTable;
    @FXML private TableColumn<Reservations, Long> idColumn;
    @FXML private TableColumn<Reservations,String> nameColumn;
    @FXML private TableColumn<Reservations, String> titleColumn;
    @FXML private TableColumn<Reservations, LocalDate> reservationDateColumn;
    @FXML private TableColumn<Reservations, LocalDate> expectancyDateColumn;
    @FXML private TableColumn<Reservations, ReservationStatus> statusColumn;



    @FXML private VBox reservationBox;
    @FXML private ComboBox<String> reservationTypeTypeCombo;
    @FXML private ComboBox<String> patronNameBox;
    @FXML private TextField resourceNameField;
    @FXML private Button clearButton;
    @FXML private Button createReservationButton;

    @FXML private ReservationRepository reservationRepository;
    @FXML private ObservableList reservationList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleHomeButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) reservationBox.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("modernDashboard.fxml"));
        Scene scene = new Scene(root,stage.getWidth(),stage.getHeight());
        stage.setTitle("GIFTED LIBRARIES");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleCreateReservation(ActionEvent actionEvent) {
    }

    @FXML
    private void handleReservationTypeCombo(ActionEvent actionEvent) {
    }

    @FXML
    private void setUpTable(){


    }
    


}
