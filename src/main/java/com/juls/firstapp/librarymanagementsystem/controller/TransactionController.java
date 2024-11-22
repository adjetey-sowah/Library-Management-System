package com.juls.firstapp.librarymanagementsystem.controller;

import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import com.juls.firstapp.librarymanagementsystem.dao.dto.TransactionDTO;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.service.TransactionServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {


    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
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


        public TransactionController() throws Exception {
            transactionService = new TransactionServiceImpl();
            transactionList = FXCollections.observableArrayList(transactionService.getAllTransactions());
        }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        setUpComboBox();

        transactionList.forEach(System.out::println);
    }


    public void handleButtonEvent(){

            String value = transactionTypeCombo.getValue();
            if(value.equalsIgnoreCase("borrow")){
                createActionButton.setText("BORROW");
            }
            else if(value.equalsIgnoreCase("return")){
                createActionButton.setId("returnButton");
                createActionButton.setOnAction(event -> {
                    try {
                        handleReturnButtonClicked();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                dueDateField.setVisible(false);
                createActionButton.setText("RETURN");
            }

    }


    private void handleReturnButtonClicked() throws Exception {
            String name = patronNameField.getText();
            String title = resourceNameField.getText();
            LocalDate returnDate = null;

            TransactionDTO transactionDTO = new TransactionDTO();
            for(TransactionDTO transaction : transactionList){
                if(transaction.getPatronName().equalsIgnoreCase(name) &&
                        transaction.getResourceName().equalsIgnoreCase(title)){
                    returnDate = transaction.getBorrowedDate();
                    break;
                }
            }

            if(transactionService.returnBook(returnDate)){
                statusLabel.setText("Resource Returned Successfully");
            }

            else statusLabel.setText("Could not update records");

        }

    private void setupTableColumns(){

        idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("resourceName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("patronName"));
        issuedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        returnedColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        fineColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
        setupActionColumn();

        totalUsersLabel.setText(String.valueOf(transactionList.size()));
        transactionTable.setItems(transactionList);

    }

    @FXML
    private void setUpComboBox(){
            this.transactionTypeCombo.getItems().addAll("BORROW","RETURN");
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
                String name = patronNameField.getText();
                String title = resourceNameField.getText();
                LocalDate dueDate = dueDateField.getValue();
                transactionService.borrowResource(name,title,dueDate);
                statusLabel.setText("Transaction Added Successfully");
            } catch (Exception e) {
                statusLabel.setText(e.getMessage());
            }

    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final HBox container = new HBox(5,editButton);
            {
                editButton.setOnAction(event -> handleEdit(getTableRow().getItem()));

                editButton.getStyleClass().add("edit-button");
                container.setAlignment(Pos.CENTER);

            }

        });
    }

    private void handleEdit(TransactionDTO transactionDTO){
            statusLabel.setText("Updating Transaction");
    }

}
