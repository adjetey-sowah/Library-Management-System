package com.juls.firstapp.librarymanagementsystem.controller.patron;

import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import com.juls.firstapp.librarymanagementsystem.dao.repository.UserRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.*;


import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.model.users.Patron;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;



public class UserController implements Initializable {

    // Form Fields
    @FXML
    private ComboBox<String> userTypeCombo;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> membershipTypeCombo;

    // Filter Fields
    @FXML
    private ComboBox<String> filterUserTypeCombo;
    @FXML
    private TextField searchField;

    // Table and its columns
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, Long> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> phoneColumn;
    @FXML
    private TableColumn<User, String> typeColumn;
    @FXML
    private TableColumn<User, String> membershipColumn;
    @FXML
    private TableColumn<User, Void> actionColumn;

    // Status Labels
    @FXML
    private Label statusLabel;
    @FXML
    private Label totalUsersLabel;

    @FXML private Button homeButton;

    @FXML private VBox patronBox;

    @FXML
    private Button clearButton;

    @FXML Button addUserButton;

    private final UserRepository userRepository = new UserRepository();

    // Observable list for table data
    private ObservableList<User> usersList = FXCollections.observableArrayList(userRepository.getAllUsers());

    public UserController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ComboBox items
        initializeComboBoxes();

        // Setup table columns
        setupTableColumns();

        // Add listener for user type selection
        setupUserTypeListener();

        // Initialize table data
        loadTableData();

        // Setup event handlers
        setupEventHandlers();

        totalUsersLabel.setText(String.valueOf("Total Users : "+usersList.size()));
    }

    private void initializeComboBoxes() {
        // Initialize user type combo box
        userTypeCombo.getItems().addAll(UserRole.PATRON.toString(), UserRole.LIBRARIAN.toString());

        // Initialize membership type combo box
        membershipTypeCombo.getItems()
                .addAll(MembershipType.STUDENT.toString()
                        ,MembershipType.FACULTY.toString()
                        ,MembershipType.RESEARCHER.toString());

        // Initialize filter combo box
        filterUserTypeCombo.getItems().addAll("All Users", "Patrons", "Librarians");
    }


    private void setupTableColumns() {
        // Configure table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
//        membershipColumn.setCellValueFactory(new PropertyValueFactory<>("membershipType"));

        // Setup action column with buttons
        setupActionColumn();

        // Set table items
        usersTable.setItems(usersList);
    }

    private void setupUserTypeListener() {
        userTypeCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals(UserRole.LIBRARIAN.toString())) {
                    passwordField.setVisible(true);
                    passwordField.setManaged(true);
                    membershipTypeCombo.setVisible(false);
                    membershipTypeCombo.setManaged(false);
                } else if (newValue.equals(UserRole.PATRON.toString())) {
                    passwordField.setVisible(false);
                    passwordField.setManaged(false);
                    membershipTypeCombo.setVisible(true);
                    membershipTypeCombo.setManaged(true);
                }
            }
        });
    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox container = new HBox(5,editButton,deleteButton);
            {
                editButton.setOnAction(event -> handleEdit(getTableRow().getItem()));
                deleteButton.setOnAction(event -> handleDelete(getTableRow().getItem()));

                editButton.getStyleClass().add("edit-button");
                deleteButton.getStyleClass().add("delete-button");
                container.setAlignment(Pos.CENTER);

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Create a container for the buttons
                    HBox buttons = new HBox(5, editButton, deleteButton);
                    setGraphic(buttons);
                }
            }
        });
    }

    @FXML
    private void handleAddUser() {
        try {
            // Validate input fields
            if (validateInputs()) {
                User newUser ;

                String selectedRole = userTypeCombo.getValue();
                if(selectedRole.equals(UserRole.LIBRARIAN.name())){
                    newUser = createLibrarianFromInput();
                    userRepository.insertLibrarian((Librarian) newUser);;
                }
                else {
                    newUser = createPatronFromInput();
                    userRepository.insertPatron((Patron) newUser);
                }

                usersList.add(newUser);
                clearForm();
                updateStatus("User added successfully!");
            }
        } catch (Exception e) {
            updateStatus("Error adding user: " + e.getMessage());
        }
    }

    @FXML
    private void handleClearForm() {
        clearForm();
        updateStatus("Form Cleared Successfully");
    }

    private void handleEdit(User user) {
        // Implement edit logic
        updateStatus("Editing user: " + user.getName());
    }

    private void handleDelete(User user) {
        boolean isDeleted = false;
        try {
            isDeleted =   userRepository.deleteUser(user.getUserId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        usersList.remove(user);
        if (isDeleted)
        updateStatus("User deleted successfully!");
        else updateStatus("Could no delete this user");
    }

    private boolean validateInputs() {
        // Add your validation logic here
        return true; // Placeholder
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        passwordField.clear();
        userTypeCombo.setValue(null);
        membershipTypeCombo.setValue(null);
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
        totalUsersLabel.setText("Total Users: " + usersList.size());
    }

    private void loadTableData() {
        // Load initial data or fetch from database
        // This is where you would typically load your data from a database
    }

    private void setupEventHandlers() {
        // Add any additional event handlers here
    }

    // Method to create a Librarian object from input fields
    private Librarian createLibrarianFromInput() {
        // Assuming you have methods to get input values
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        return new Librarian(name, email,phone,password);
    }

    // Method to create a Patron object from input fields
    private Patron createPatronFromInput() {
        // Assuming you have methods to get input values
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        MembershipType type = MembershipType.valueOf(membershipColumn.getText());
        // Add other relevant fields for Patron
        return new Patron(name,type, email,phone);
    }

    @FXML
    private void handleHomeButtonClicked() {

        try {
            Stage stage = (Stage) patronBox.getScene().getWindow();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("modernDashboard.fxml"));
            Scene scene = new Scene(root,stage.getWidth(),stage.getHeight());
            stage.setScene(scene);
            stage.setTitle("Library Management System");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}