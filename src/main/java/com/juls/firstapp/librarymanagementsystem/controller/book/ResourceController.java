package com.juls.firstapp.librarymanagementsystem.controller.book;

import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ResourceRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.Genre;
import com.juls.firstapp.librarymanagementsystem.model.enums.MediaFormat;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.resource.Media;
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

public class ResourceController {

    @FXML private ComboBox<ResourceType> resourceTypeCombo;
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
    private DatePicker publicationDateField;
    @FXML
    private ComboBox<MediaFormat> mediaFormatCombo;
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

        initializeComboBoxes();

        //        resourcesTable.setVisible(false);






        // Set up table columns
        setupTableColumns();

        // Load initial data
        loadResources();
    }

    @FXML
    private void handleAddResource() throws Exception {

        if(resourceTypeCombo.getValue().equals(ResourceType.BOOK)){

            handleAddBook();
            statusLabel.setText("Book added successfully.");
        }
        else if(resourceTypeCombo.getValue().equals(ResourceType.JOURNAL)){
            handleAddJournal();
            statusLabel.setText("Journal Added successfully");
        } else if (resourceTypeCombo.getValue().equals(ResourceType.MEDIA)) {
            handleAddMedia();
        }
    }

    @FXML
    private void handleTypeCombo(){

        if(resourceTypeCombo.getValue().equals(ResourceType.JOURNAL)){

        authorField.setPromptText("Issue Number");
        isbnField.setPromptText("Frequency");

        authorField.setVisible(true);
        isbnField.setVisible(true);
        mediaFormatCombo.setVisible(false);
        categoryComboBox.setVisible(false);
        publicationDateField.setVisible(false);

        }
        else if(resourceTypeCombo.getValue().equals(ResourceType.MEDIA)){
            authorField.setVisible(false);
            isbnField.setVisible(false);
            mediaFormatCombo.setVisible(true);
            publicationDateField.setVisible(false);
            categoryComboBox.setVisible(false);
        }
        else if(resourceTypeCombo.getValue().equals(ResourceType.BOOK)){
            mediaFormatCombo.setVisible(false);
            authorField.setVisible(true);
            authorField.setPromptText("Author Name");
            isbnField.setVisible(true);
            isbnField.setPromptText("ISBN");
            categoryComboBox.setVisible(true);
            publicationDateField.setVisible(true);
        }
    }

    @FXML
    private void handleAddBook(){

        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        Genre genre = Genre.valueOf(categoryComboBox.getValue());
        LocalDate publicationDate = publicationDateField.getValue();

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setGenre(genre);
        book.setPublicationDate(publicationDate);

        if(!bookExists(title,genre)){
        resourceRepository.addLibraryResource(book);
        }
        else statusLabel.setText("Book already exist");


    }

    @FXML
    private void handleAddJournal(){


        String title = titleField.getText();
        String issueNumber = authorField.getText();
        String frequency = isbnField.getText();

        Journal journal = new Journal();
        journal.setTitle(title);
        journal.setFrequency(frequency);
        journal.setIssueNumber(issueNumber);

        if(!journalExist(issueNumber,title)){
            resourceRepository.addLibraryResource(journal);
        }
        else statusLabel.setText("Journal already exist");

    }

    @FXML
    private void handleAddMedia() throws Exception {
        String title = titleField.getText();
        MediaFormat format = mediaFormatCombo.getValue();

        Media media = new Media();

        media.setTitle(title);
        media.setFormat(format);

        if(!mediaExists(title,format)){
            resourceRepository.addLibraryResource(media);
        }
        else statusLabel.setText("Media item already exists");

    }

    @FXML
    private boolean bookExists(String title, Genre genre){
        for (LibraryResource libraryResource : resourceList){
            if (libraryResource instanceof Book){
                if(libraryResource.getTitle()
                        .equalsIgnoreCase(title) && ((Book) libraryResource)
                        .getGenre().equals(genre)){

                return true;
                }
            }
        }
        return false;
    }


    @FXML
    private boolean mediaExists(String title, MediaFormat mediaFormat){
        for (LibraryResource libraryResource : resourceList){
            if (libraryResource instanceof Media){
                if(libraryResource.getTitle()
                        .equalsIgnoreCase(title) && ((Media) libraryResource)
                        .getFormat().equals(mediaFormat)){

                return true;
                }
            }
        }
        return false;
    }

    private boolean journalExist(String issueNumber, String title) {
        for (LibraryResource libraryResource : resourceList) {
            if (libraryResource instanceof Journal) {
                if (((Journal) libraryResource).getIssueNumber().equalsIgnoreCase(issueNumber) &&
                        title.equalsIgnoreCase(libraryResource.getTitle())) {
                    return true;
                }
            }
        }
        return false;
    }

    @FXML
    private void initializeComboBoxes(){
        for (Genre genre : Genre.values()){
            categoryComboBox.getItems().add(genre.name());
        }

        mediaFormatCombo.getItems().addAll(MediaFormat.AUDIO,MediaFormat.DVD);

        resourceTypeCombo.getItems().addAll(ResourceType.BOOK, ResourceType.JOURNAL,ResourceType.MEDIA);

        filterCategoryComboBox.getItems().addAll("Journal","Book","Media");

        // Initialize the status combo box
        filterStatusComboBox.getItems().addAll(
                "All",
                "Available",
                "Low Stock",
                "Out of Stock"
        );


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
