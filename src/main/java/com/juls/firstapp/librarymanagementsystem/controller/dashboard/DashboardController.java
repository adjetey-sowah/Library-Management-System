package com.juls.firstapp.librarymanagementsystem.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class DashboardController {

//    @FXML
//    private Label totalBooksLabel;
//
//    @FXML
//    private Label totalPatronsLabel;
//
//    @FXML
//    private Label booksCheckedOutLabel;
//
//    @FXML
//    private PieChart circulationChart;
//
//    @FXML
//    private TableView<RecentActivity> recentActivityTable;
//
//    @FXML
//    private TableColumn<RecentActivity, String> patronColumn;
//
//    @FXML
//    private TableColumn<RecentActivity, String> bookColumn;
//
//    @FXML
//    private TableColumn<RecentActivity, String> actionColumn;
//
//    @FXML
//    private TableColumn<RecentActivity, LocalDate> dateColumn;

    @FXML
    private Button showBookManagementButton;

    @FXML
    private Button showPatronManagementButton;

    @FXML
    private Button showLendingManagementButton;

    @FXML
    private Button showReportGenerationButton;

    @FXML
    private void initialize() {
//        setupTableColumns();
//        loadQuickStats();
//        loadCirculationChart();
        loadRecentActivities();
    }

//    private void setupTableColumns() {
//        patronColumn.setCellValueFactory(new PropertyValueFactory<>("patronName"));
//        bookColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
//        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
//        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//    }

//    private void loadQuickStats() {
//        // Replace with actual logic to fetch stats from your database or service
//        totalBooksLabel.setText("1200");
//        totalPatronsLabel.setText("350");
//        booksCheckedOutLabel.setText("150");
//    }

//    private void loadCirculationChart() {
//        // Replace with actual logic to fetch circulation data
//        circulationChart.getData().add(new PieChart.Data("Available", 1000));
//        circulationChart.getData().add(new PieChart.Data("Checked Out", 150));
//        circulationChart.getData().add(new PieChart.Data("Reserved", 50));
//    }

    private void loadRecentActivities() {
        // Replace with actual logic to fetch recent activities
        List<RecentActivity> activities = List.of(
                new RecentActivity("John Doe", "The Great Gatsby", "Checked Out", LocalDate.now()),
                new RecentActivity("Jane Smith", "1984", "Returned", LocalDate.now().minusDays(1)),
                new RecentActivity("Alice Brown", "To Kill a Mockingbird", "Reserved", LocalDate.now().minusDays(2))
        );

//        recentActivityTable.getItems().setAll(activities);
    }

    @FXML
    private void showBookManagement() {
        // Navigate to the book management screen
        System.out.println("Navigating to Book Management Screen...");
    }

    @FXML
    private void showPatronManagement() {
        // Navigate to the patron management screen
        System.out.println("Navigating to Patron Management Screen...");
    }

    @FXML
    private void showLendingManagement() {
        // Navigate to the lending management screen
        System.out.println("Navigating to Lending Management Screen...");
    }

    @FXML
    private void showReportGeneration() {
        // Navigate to the report generation screen
        System.out.println("Navigating to Report Generation Screen...");
    }

    public static class RecentActivity {
        private final String patronName;
        private final String bookTitle;
        private final String action;
        private final LocalDate date;

        public RecentActivity(String patronName, String bookTitle, String action, LocalDate date) {
            this.patronName = patronName;
            this.bookTitle = bookTitle;
            this.action = action;
            this.date = date;
        }

        public String getPatronName() {
            return patronName;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getAction() {
            return action;
        }

        public LocalDate getDate() {
            return date;
        }
    }
}

