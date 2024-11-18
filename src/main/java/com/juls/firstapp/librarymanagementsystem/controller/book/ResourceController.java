package com.juls.firstapp.librarymanagementsystem.controller.book;

import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ResourceController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField isbnField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TableView<LibraryResource> resourcesTable;
    @FXML
    private Label statusLabel;
    @FXML
    private Label totalResourcesLabel;
    @FXML
    private Label availableResourcesLabel;
    @FXML
    private Label checkedOutLabel;

    @FXML private VBox resourceBox;

    @FXML private ComboBox filterStatusComboBox;

    @FXML private Button homeButton;

    @FXML
    public void initialize() {
        // Initialize the category combo box
        categoryComboBox.getItems().addAll(
                "Books",
                "Magazines",
                "Journals",
                "Digital Resources",
                "Audio/Visual",
                "Reference Materials"
        );

        // Initialize the status combo box
        filterStatusComboBox.getItems().addAll(
                "All",
                "Available",
                "Low Stock",
                "Out of Stock"
        );

        // Set up table columns
        setupTableColumns();

        // Load initial data
        loadResources();
    }

    @FXML
    private void handleAddResource() {
        // Implementation for adding a new resource
    }

    @FXML
    private void handleClearForm() {
        // Implementation for clearing the form
    }

    @FXML
    private void handleApplyFilters() {
        // Implementation for applying filters
    }

    @FXML
    private void handleHomeButtonClicked() {

        try {
            Stage stage = (Stage) resourceBox.getScene().getWindow();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("modernDashboard.fxml"));
            Scene scene = new Scene(root,stage.getWidth(),stage.getHeight());
            stage.setScene(scene);
            stage.setTitle("Library Management System");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleExport() {
        // Implementation for exporting data
    }

    @FXML
    private void handleImport() {
        // Implementation for importing data
    }

    @FXML
    private void handleGenerateReport() {
        // Implementation for generating reports
    }

    @FXML
    private void handleRefresh() {
        // Implementation for refreshing the table
    }

    private void setupTableColumns() {
        // Implementation for setting up table columns
    }

    private void loadResources() {
        // Implementation for loading resources data
    }
}
