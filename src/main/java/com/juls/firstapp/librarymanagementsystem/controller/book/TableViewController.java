package com.juls.firstapp.librarymanagementsystem.controller.book;

import com.juls.firstapp.librarymanagementsystem.model.resource.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;

public class TableViewController {

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, ?> resourceIdColumn;

    @FXML
    private TableColumn<Book, ?> titleColumn;

    @FXML
    private TableColumn<Book, ?> authorColumn;

    @FXML
    private TableColumn<Book, ?> isbnColumn;

    @FXML
    private TableColumn<Book, ?> genreColumn;

    @FXML
    private TableColumn<Book, ?> publicationDateColumn;

    @FXML
    private TableColumn<Book, ?> status;



    // In your controller or initialization code



    // Method to initialize the table
    @SuppressWarnings("deprecation")
    @FXML
    public void initialize() {
        // Link columns with the Resource class properties
        resourceIdColumn.setCellValueFactory(new PropertyValueFactory<>("resourceId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        publicationDateColumn.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("resourceStatus"));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    // Method to populate the table with data from a LinkedList
    public void populateTable(LinkedList<Book> resources) {
        ObservableList<Book> data = FXCollections.observableArrayList(resources);
        tableView.setItems(data);
    }



}

