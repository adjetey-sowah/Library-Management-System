package com.juls.firstapp.librarymanagementsystem.controller;

import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ResourceRepository;
import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import com.juls.firstapp.librarymanagementsystem.service.TransactionServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {


    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
    @FXML private ComboBox<String> patronNameBox;
    @FXML private Label statusLabel;
    @FXML private Button createActionButton;
    @FXML private Button clearButton;
    @FXML private DatePicker dueDateField;
    @FXML private TextField resourceNameField;
    @FXML private TextField patronNameField;
    @FXML private ComboBox<String> transactionTypeCombo;
    @FXML private Button refreshButton;
    @FXML private Button homeButton;
    @FXML private Label totalUsersLabel;
    @FXML private TableView<TransactionDTO> transactionTable;
        @FXML private TableColumn<TransactionDTO, Long> idColumn;
        @FXML private TableColumn<TransactionDTO, String> titleColumn;
        @FXML private TableColumn<TransactionDTO, String> nameColumn;
        @FXML private TableColumn<TransactionDTO, LocalDate> issuedDateColumn;
        @FXML private TableColumn<TransactionDTO, LocalDate> dueDateColumn;
        @FXML private TableColumn<TransactionDTO, LocalDate> returnedColumn;
        @FXML private TableColumn<TransactionDTO, Void> actionColumn;
        @FXML private TableColumn<TransactionDTO, Double> fineColumn;

        @FXML private VBox transactionBox;


        private final TransactionServiceImpl transactionService;
        private final ObservableList<TransactionDTO> transactionList;
        private final UserRepository userRepository;
        private final ResourceRepository resourceRepository;


        public TransactionController() throws Exception {
            transactionService = new TransactionServiceImpl();
            transactionList = FXCollections.observableArrayList(transactionService.getAllTransactions());
            userRepository = new UserRepository();
            resourceRepository = new ResourceRepository();
        }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        setUpComboBox();
        try {
            setPatronNameBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        transactionList.forEach(System.out::println);
    }


    public void handleButtonEvent(){

            String value = transactionTypeCombo.getValue();
            if(value.equalsIgnoreCase("borrow")){
                createActionButton.setText("BORROW");
            }
            else if(value.equalsIgnoreCase("return")){
                createActionButton.setText("RETURN");
                dueDateField.setVisible(false);
                createActionButton.setOnAction(event -> {
                    try {
                        handleReturnResource();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }

    }

    private void setupTableColumns(){

        idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("resourceName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("patronName"));
        issuedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        returnedColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        fineColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));

        totalUsersLabel.setText(String.valueOf(transactionList.size()));
        transactionTable.setItems(transactionList);

    }

    @FXML
    private void setUpComboBox(){
            this.transactionTypeCombo.getItems().addAll("BORROW","RETURN");
    }

    @FXML
    private void setPatronNameBox() throws SQLException {
            LinkedList<User> userList = this.userRepository.getAllUsers();

        patronNameBox.getItems().addAll(
                userList.stream()
                        .filter(e -> e.getRole().equals(UserRole.PATRON)) // Filter users with the role of PATRON
                        .map(User::getName) // Extract the name of the user
                        .toList() // Collect the names into a list
        );


    }

    @FXML
    private void handleHomeButtonClicked() throws IOException {

        Stage stage = (Stage) transactionBox.getScene().getWindow();

        Parent newRoot = FXMLLoader.load(HelloApplication.class.getResource("modernDashboard.fxml"));
        Scene scene = new Scene(newRoot,stage.getWidth(),stage.getHeight());
        stage.setTitle("Gifted Labs Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleRefreshButton() {
        log.info("I am refreshing");
        transactionTable.refresh();
        log.info("Done refreshing");
    }


    // Method to create a Librarian object from input fields
    @FXML
    private  void handleCreateTransaction() throws RuntimeException {
            try {
                String name = patronNameBox.getValue();
                String title = resourceNameField.getText();
                LocalDate dueDate = dueDateField.getValue();
                transactionService.borrowResource(name,title,dueDate);
                statusLabel.setText("Transaction Added Successfully");
            } catch (Exception e) {
                statusLabel.setText(e.getMessage());
            }

    }

    @FXML
    private void handleReturnResource() throws Exception {
        String patronName = this.patronNameBox.getValue();
        String resourceName = this.resourceNameField.getText();
        Long transactionId = 0L;
        for (TransactionDTO transaction : transactionList){
            if (transaction.getResourceName()
                    .contains(resourceName)
                    && transaction
                    .getPatronName()
                    .equalsIgnoreCase(patronName)){
                transactionId = transaction.getTransactionId();
            }

            if(transactionService.returnBook(transactionId)){
                statusLabel.setText("Book Returned Successfully");
            }
            else statusLabel.setText("Transaction Update failed");
        }
    }
}
