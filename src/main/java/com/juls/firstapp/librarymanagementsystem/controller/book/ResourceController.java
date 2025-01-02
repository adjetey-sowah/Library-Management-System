package com.juls.firstapp.librarymanagementsystem.controller.book;

import com.juls.firstapp.librarymanagementsystem.HelloApplication;
import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.repository.ResourceRepository;
import com.juls.firstapp.librarymanagementsystem.model.enums.Genre;
import com.juls.firstapp.librarymanagementsystem.model.enums.MediaFormat;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;
import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import com.juls.firstapp.librarymanagementsystem.model.resource.Media;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ResourceController {

    @FXML private TextField searchField;
    @FXML private ComboBox<ResourceType> resourceTypeCombo;
    @FXML private ComboBox<String> filterCategoryComboBox;
    @FXML private TableView<LibraryResource> resourceTable;
    @FXML private TableColumn<LibraryResource, String> authorColumn;
    @FXML private TableColumn<LibraryResource, String> isbnColumn;
    @FXML private  TableColumn<LibraryResource, String> publicationColumn;
    @FXML private TableColumn<LibraryResource, Long> categoryColumn;
    @FXML private TableColumn<LibraryResource, String> idColumn;
    @FXML private TableColumn<LibraryResource, String> titleColumn;
    @FXML private TableColumn<LibraryResource, Genre> genreTableColumn;
    @FXML private TableColumn<LibraryResource, ResourceType> availableColumn;
    @FXML private TableColumn<LibraryResource, String> frequencyColumn;
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

    @FXML private Label tableTitle;

    @FXML
    protected ResourceRepository resourceRepository;
    @FXML private ObservableList<LibraryResource> resourceList;

    public ResourceController() throws Exception {
        resourceRepository = new ResourceRepository();
        resourceList = FXCollections.observableArrayList(resourceRepository.findAllResource());
    }

    public ResourceController(DatabaseConfig databaseConfig) throws Exception {
        this.resourceRepository = new ResourceRepository(databaseConfig);
        resourceList = FXCollections.observableArrayList(resourceRepository.findAllResource());
    }

    @FXML
    public void initialize() {

        initializeComboBoxes();

        totalResourcesLabel.setText("Total Resources: "+String.valueOf(resourceList.size()));

        int availableSize = resourceList.stream().filter(e -> e.getResourceStatus()
                .equals(ResourceStatus.AVAILABLE)).toList().size();

        int borrowed = resourceList.size() - availableSize;

        availableResourcesLabel.setText("Available Resources: "+String.valueOf(availableSize));
        checkedOutLabel.setText("Borrowed Resources" +String.valueOf(borrowed));

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

        handleRefresh();
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
    public void handleAddBook(){

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
    public boolean bookExists(String title, Genre genre){
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

        filterCategoryComboBox.getItems().addAll("All " +
                "Resources","Journal","Book","Media");

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
        titleField.clear();
        authorField.clear();
        mediaFormatCombo.getSelectionModel().clearSelection();
        resourceTypeCombo.getSelectionModel().clearSelection();
        isbnField.clear();
        categoryComboBox.getSelectionModel().clearSelection();
        publicationDateField.setValue(null);

    }

    @FXML
    private void handleApplyFilters() {
        // Implementation for applying filters

        String selectedCategory = filterCategoryComboBox.getValue();

        if(selectedCategory.equalsIgnoreCase(ResourceType.BOOK.toString())){
            setBookTable();
        }
        else if(selectedCategory.equalsIgnoreCase(ResourceType.JOURNAL.toString())){
            setJournalTable();
        }

        else if(selectedCategory.equalsIgnoreCase(ResourceType.MEDIA.toString())){
            setMediaTable();
        }

        else if(selectedCategory.equalsIgnoreCase("all resources")){
            formatColumn.setVisible(false);
            authorColumn.setVisible(false);
            isbnColumn.setVisible(false);
            issueNumberColumn.setVisible(false);
            publicationDateField.setVisible(false);
            frequencyColumn.setVisible(false);

            setupTableColumns();

        }
    }

    @FXML private void setBookTable(){
        authorColumn.setVisible(true);
        isbnColumn.setVisible(true);
        publicationColumn.setVisible(true);
        issueNumberColumn.setVisible(false);
        frequencyColumn.setVisible(false);
        formatColumn.setVisible(false);
        genreTableColumn.setVisible(true);

        tableTitle.setText("Book List");

        ObservableList<LibraryResource> bookList = FXCollections.observableArrayList(
                resourceList.stream()
                        .filter(e -> e instanceof Book)
                        .map(e -> (Book) e)
                        .toList()
        );

        idColumn.setCellValueFactory(new PropertyValueFactory<>("resourceId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("resourceType"));
        genreTableColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("resourceStatus"));
        publicationColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));

        resourcesTable.setItems(bookList);


    }

    @FXML private void setJournalTable(){



        // Hide irrelevant columns
        authorColumn.setVisible(false);
        isbnColumn.setVisible(false);
        publicationColumn.setVisible(false);
        formatColumn.setVisible(false);
        genreTableColumn.setVisible(false);

        // Set table title
        tableTitle.setText("Journal List");

        // Show relevant columns for Journals
        issueNumberColumn.setVisible(true);
        frequencyColumn.setVisible(true);
        // Filter resourceList to only Journals
        ObservableList<LibraryResource> journalList = FXCollections.observableArrayList(
                resourceList.stream()
                        .filter(e -> e instanceof Journal) // Only include Journals
                        .map(e -> (Journal) e)             // Cast to Journal type
                        .toList()
        );

        // Set cell value factories for Journal-specific properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("resourceId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        issueNumberColumn.setCellValueFactory(new PropertyValueFactory<>("issueNumber"));
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));


        // Populate the table with Journals
        resourcesTable.setItems(journalList);
    }

    @FXML private void setMediaTable()
    {
        tableTitle.setText("Media List");

        authorColumn.setVisible(false);
        isbnColumn.setVisible(false);
        frequencyColumn.setVisible(false);
        issueNumberColumn.setVisible(false);
        publicationColumn.setVisible(false);
        formatColumn.setVisible(true);
        genreTableColumn.setVisible(false);

        ObservableList<LibraryResource> mediaList = FXCollections.observableArrayList(resourceList
                .stream()
                .filter(e -> e instanceof Media)
                .map(e -> (Media) e)
                .toList());

        formatColumn.setCellValueFactory(new PropertyValueFactory<>("format"));

        resourcesTable.setItems(mediaList);

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
    private void handleRefresh() throws Exception {
        resourcesTable.getItems().clear();
        resourceList = FXCollections.observableArrayList(this.resourceRepository.findAllResource());
        resourcesTable.setItems(resourceList);
        resourcesTable.refresh();
    }

    private void setupTableColumns() {
        // Configure table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("resourceId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("resourceType"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("resourceStatus"));
        genreTableColumn.setVisible(false);
        // Setup action column with buttons
        setupActionColumn();
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


    public static void main(String[] args) throws Exception {
        ResourceController controller = new ResourceController();

        ObservableList<LibraryResource> journalList = FXCollections.observableArrayList(
                controller.resourceList.stream()
                        .filter(e -> e.getResourceType().equals(ResourceType.JOURNAL))
                        .map(e -> (Journal) e)
                        .toList()
        );

        journalList.forEach(System.out::println);
    }


    @FXML
    private void handleSearchItems(KeyEvent actionEvent) throws Exception {
        ObservableList<LibraryResource> searchList = FXCollections.observableArrayList(
                this.resourceRepository.searchResources(searchField.getText())
        );
        resourcesTable.setItems(searchList);
    }
}
