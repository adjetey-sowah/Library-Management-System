package com.juls.firstapp.librarymanagementsystem.controller.book;

import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ResourceRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.Genre;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ResourceController {

    @FXML private DatePicker publicationDateField;
    @FXML private VBox addResourceBox;
    @FXML private ComboBox<String> filterCategoryComboBox;
    @FXML private TableView<LibraryResource> resourceTable;
    @FXML private TableColumn<LibraryResource, String> authorColumn;
    @FXML private TableColumn<LibraryResource, String> isbnColumn;
    @FXML private  TableColumn<LibraryResource, String> publicationColumn;
    @FXML private TableColumn<LibraryResource, Long> categoryColumn;
    @FXML private TableColumn<LibraryResource, String> idColumn;
    @FXML private TableColumn<LibraryResource, String> titleColumn;
    @FXML private TableColumn<LibraryResource, ResourceType> availableColumn;
    @FXML private TableColumn<LibraryResource, String> formatColumn;
    @FXML private TableColumn<LibraryResource, Void> actionsColumn;
    @FXML private TableColumn<LibraryResource, String> issueNumberColumn;



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


    private final ResourceRepository resourceRepository;
    private final ObservableList<LibraryResource> resourceList;

    public ResourceController() throws Exception {
        resourceRepository = new ResourceRepository();
        resourceList = FXCollections.observableArrayList(resourceRepository.findAllResource());
    }

    @FXML
    public void initialize() {
        // Initialize the category combo box
//        resourcesTable.setVisible(false);
//        categoryComboBox.getItems().addAll(Arrays.asList(Arrays.toString(Genre.values())));
        Arrays.stream(Genre.values())
                .map(genre -> genre.name().charAt(0) + genre.name().substring(1).toLowerCase())
                .forEach(categoryComboBox.getItems()::add);

        // Initialize the status combo box
        filterStatusComboBox.getItems().addAll(
                "All",
                "Available",
                "Low Stock",
                "Out of Stock"
        );

        filterCategoryComboBox.getItems().addAll("Journal","Book","Media");

        // Set up table columns
        setupTableColumns();

        // Load initial data
        loadResources();
    }

    @FXML
    private void handleAddResource() {

        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnColumn.getText();
        LocalDate publicationDate = publicationDateField.getValue();
        Genre bookGenre = Genre.valueOf(categoryComboBox.getValue().toUpperCase());

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPublicationDate(publicationDate);
        book.setGenre(bookGenre);
        book.setResourceStatus(ResourceStatus.AVAILABLE);
        resourceRepository.addLibraryResource(book);
        resourcesTable.refresh();
        statusLabel.setText("Book Added Successfully");
    }

    @FXML
    private void handleClearForm() {
        // Implementation for clearing the form
    }

    @FXML
    private void handleApplyFilters() {
        // Implementation for applying filters

        String selectedCategory = filterCategoryComboBox.getValue();

        if(selectedCategory.equalsIgnoreCase(ResourceType.BOOK.toString())){
            authorColumn.setVisible(true);
            isbnColumn.setVisible(true);
            publicationColumn.setVisible(true);


            authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
            isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            publicationColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        }
        else if(selectedCategory.equalsIgnoreCase(ResourceType.JOURNAL.toString())){
            issueNumberColumn.setVisible(true);

            issueNumberColumn.setCellValueFactory(new PropertyValueFactory<>("issueNumber"));




        }
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
        // Configure table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("resourceId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("resourceType"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("resourceStatus"));

        // Setup action column with buttons
//        setupActionColumn();

        resourcesTable.setItems(resourceList);
    }


    private void setupActionColumn() {
        actionsColumn.setCellFactory(column -> new TableCell<>() {
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


        });
        }

    private void handleEdit(LibraryResource libraryResource) {
        // Implement edit logic
//        updateStatus("Editing user: " + user.getName());
    }

    private void handleDelete(LibraryResource resource) {
        boolean isDeleted = false;
        try {
            isDeleted =   resourceRepository.deleteLibraryResource(resource.getResourceId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resourceList.remove(resource);
//              updateStatus("User deleted successfully!");
//        else updateStatus("Could no delete this user");
    }


    private void loadResources() {
        // Implementation for loading resources data
    }

    public void handleCategoryComboBox(){

    }


}
