package com.juls.firstapp.librarymanagementsystem.views.images;

import javafx.scene.layout.BorderPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class DashboardView extends BorderPane {

    public DashboardView() {
        // Set up the main container
        this.setStyle("-fx-background-color: #f5f6fa;");

        // Create and setup components
        setupSidebar();
        setupMainContent();
    }

    private void setupSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(250);
        sidebar.setStyle("-fx-background-color: #2d3436;");
        sidebar.setPadding(new Insets(20));
        sidebar.setSpacing(10);

        // Logo/Title section
        Label title = new Label("Library System");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        sidebar.getChildren().add(title);

        // Navigation Items
        String[][] menuItems = {
                {"Dashboard", "HOME"},
                {"Books", "BOOK"},
                {"Journals", "NEWSPAPER"},
                {"Media", "PLAY_CIRCLE"},
                {"Patrons", "USERS"},
                {"Transactions", "EXCHANGE"},
                {"Reports", "CHART_BAR"},
                {"Settings", "COGS"}
        };

        for (String[] item : menuItems) {
            HBox menuItem = createMenuItem(item[0], item[1]);
            sidebar.getChildren().add(menuItem);
        }

        this.setLeft(sidebar);
    }

    private HBox createMenuItem(String text, String icon) {
        HBox item = new HBox();
        item.setAlignment(Pos.CENTER_LEFT);
        item.setSpacing(10);
        item.setPadding(new Insets(10));
        item.setStyle("-fx-cursor: hand;");

        FontAwesomeIconView iconView = new FontAwesomeIconView(
                FontAwesomeIcon.valueOf(icon)
        );
        iconView.setFill(Color.WHITE);
        iconView.setSize("16");

        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white;");

        item.getChildren().addAll(iconView, label);

        // Hover effect
        item.setOnMouseEntered(e ->
                item.setStyle("-fx-background-color: #3d4446; -fx-cursor: hand;"));
        item.setOnMouseExited(e ->
                item.setStyle("-fx-background-color: transparent; -fx-cursor: hand;"));

        return item;
    }

    private void setupMainContent() {
        VBox mainContent = new VBox();
        mainContent.setPadding(new Insets(20));
        mainContent.setSpacing(20);

        // Header section
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Label welcomeLabel = new Label("Welcome, Librarian");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        header.getChildren().add(welcomeLabel);

        // Statistics Cards
        GridPane cardGrid = new GridPane();
        cardGrid.setHgap(20);
        cardGrid.setVgap(20);

        String[][] cardData = {
                {"Total Books", "1,234", "BOOK", "#2ecc71"},
                {"Journals", "567", "NEWSPAPER", "#3498db"},
                {"Media Items", "89", "PLAY_CIRCLE", "#9b59b6"},
                {"Active Patrons", "456", "USERS", "#e74c3c"},
                {"Transactions", "789", "EXCHANGE", "#f1c40f"}
        };

        int col = 0;
        int row = 0;

        for (String[] data : cardData) {
            VBox card = createStatCard(data[0], data[1], data[2], data[3]);
            cardGrid.add(card, col, row);
            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }

        // Recent Transactions Table
        TableView<Transaction> transactionTable = new TableView<>();
        transactionTable.setPrefHeight(400);

        // Create columns
        TableColumn<Transaction, String> idCol = new TableColumn<>("ID");
        TableColumn<Transaction, String> patronCol = new TableColumn<>("Patron");
        TableColumn<Transaction, String> itemCol = new TableColumn<>("Item");
        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");

        transactionTable.getColumns().addAll(
                idCol, patronCol, itemCol, typeCol, dateCol, statusCol
        );

        // Add components to main content
        mainContent.getChildren().addAll(header, cardGrid, new Label("Recent Transactions"), transactionTable);

        this.setCenter(mainContent);
    }

    private VBox createStatCard(String title, String value, String icon, String color) {
        VBox card = new VBox();
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);"
        );
        card.setPadding(new Insets(20));
        card.setSpacing(10);
        card.setPrefWidth(250);

        FontAwesomeIconView iconView = new FontAwesomeIconView(
                FontAwesomeIcon.valueOf(icon)
        );
        iconView.setFill(Color.web(color));
        iconView.setSize("30");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle(
                "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: " + color + ";"
        );

        card.getChildren().addAll(iconView, titleLabel, valueLabel);
        return card;
    }

    // Transaction class for the table
    public static class Transaction {
        private String id;
        private String patron;
        private String item;
        private String type;
        private String date;
        private String status;

        // Constructor, getters, and setters
    }
}
