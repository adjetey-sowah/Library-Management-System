package com.juls.firstapp.librarymanagementsystem.controller.dashboard;


import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ResourceRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.service.TransactionServiceImpl;
import javafx.event.ActionEvent;
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
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    public TextField globalSearchField;
    @FXML  private  Label allTransactionsLabel;
    @FXML private Button transactionButton;
    @FXML private Button reservationButton;

    @FXML private ResourceRepository resourceRepository;
    @FXML private UserRepository userRepository;
    @FXML private TransactionServiceImpl transactionService;

    public DashboardController() throws Exception {
        try {
            this.resourceRepository = new ResourceRepository();
            this.userRepository = new UserRepository();
            this.transactionService = new TransactionServiceImpl();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private Label totalResourcesLabel;
    public Label activeUsersLabel;
    public Label pendingReturnsLabel;
    public Label availabilityLabel;
    public LineChart usageChart;
    public PieChart categoryChart;

    @FXML private Button logoutButton;

    @FXML private TableColumn activityDateColumn;

    @FXML private Button quickAddButton;

    @FXML private HBox dashboard;

    @FXML private Button dashboardButton;

    @FXML private Button resourceButton;

    @FXML private Button userButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        handleDashboardButtonClick();
        setCardValues();
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

    @FXML
    private void setCardValues(){
        try {
            this.totalResourcesLabel.setText(String.valueOf(resourceRepository.findAllResource().size()));
            this.activeUsersLabel.setText(String.valueOf(userRepository.getAllUsers().size()));
            this.allTransactionsLabel.setText(String.valueOf(transactionService.getAllTransactions().size()));
            this.pendingReturnsLabel.setText(String.valueOf(resourceRepository.getResourceNumber()));

            String percentage = String.format("%.1f",resourceRepository.getAvailablePercentage());
            this.availabilityLabel.setText(String.valueOf(percentage+"%"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private void handleLogoutClick() throws IOException {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("logins.fxml"));
        Scene scene = new Scene(root,1080,720);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleTransactionButtonClick() throws IOException {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("transactionView.fxml"));
        Scene scene = new Scene(root,dashboard.getWidth(),dashboard.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleReservationButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("reservation-view.fxml"));
        Scene scene = new Scene(root, stage.getWidth(),stage.getHeight());
        stage.setScene(scene);
        stage.setTitle("Reservation Management");
        stage.show();

    }
}