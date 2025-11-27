package com.library.controllers;

import com.library.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Button btnHome, btnBooks, btnBorrowers, btnBranches, btnLoans;
    @FXML private Label labelDate;
    @FXML private Label statTotalBooks, statTotalBorrowers, statActiveLoans, statOverdueLoans;
    @FXML private Label infoJavaVersion;
    @FXML private TableView<String> tableRecentActivity;

    private BookService bookService = BookService.getInstance();
    private BorrowerService borrowerService = BorrowerService.getInstance();
    private LoanService loanService = LoanService.getInstance();
    private BranchService branchService = BranchService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupDate();
        updateStatistics();
        setupNavigation();
        loadSystemInfo();
    }

    private void setupDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
        labelDate.setText(today.format(formatter));
    }

    private void updateStatistics() {
        // Update statistics
        statTotalBooks.setText(String.valueOf(bookService.getAllBooks().size()));
        statTotalBorrowers.setText(String.valueOf(borrowerService.getAll().size()));
        
        long activeLoans = loanService.getAllLoans().stream()
                .filter(l -> !l.isReturned())
                .count();
        statActiveLoans.setText(String.valueOf(activeLoans));
        
        long overdueLoans = loanService.getAllLoans().stream()
                .filter(com.library.models.Loan::isOverdue)
                .count();
        statOverdueLoans.setText(String.valueOf(overdueLoans));
    }

    private void setupNavigation() {
        btnHome.setOnAction(e -> setActiveButton(btnHome));
        btnBooks.setOnAction(e -> {
            setActiveButton(btnBooks);
            switchToBooks();
        });
        btnBorrowers.setOnAction(e -> {
            setActiveButton(btnBorrowers);
            switchToBorrowers();
        });
        btnBranches.setOnAction(e -> {
            setActiveButton(btnBranches);
            switchToBranches();
        });
        btnLoans.setOnAction(e -> {
            setActiveButton(btnLoans);
            switchToLoans();
        });
    }

    private void setActiveButton(Button active) {
        btnHome.getStyleClass().remove("active");
        btnBooks.getStyleClass().remove("active");
        btnBorrowers.getStyleClass().remove("active");
        btnBranches.getStyleClass().remove("active");
        btnLoans.getStyleClass().remove("active");
        
        active.getStyleClass().add("active");
    }

    private void loadSystemInfo() {
        infoJavaVersion.setText(System.getProperty("java.version"));
    }

    @FXML
    public void switchToDashboard() {
        setActiveButton(btnHome);
        // Already on dashboard
    }

    @FXML
    public void switchToBooks() {
        openWindow("/views/books.fxml", "Books Management");
    }

    @FXML
    public void switchToBorrowers() {
        openWindow("/views/borrowers.fxml", "Borrowers Management");
    }

    @FXML
    public void switchToBranches() {
        openWindow("/views/branches.fxml", "Branches Management");
    }

    @FXML
    public void switchToLoans() {
        openWindow("/views/loan.fxml", "Loans Management");
    }

    @FXML
    public void openAddLoan() {
        openWindow("/views/add_loan.fxml", "Create New Loan");
    }

    @FXML
    public void openAddBook() {
        openWindow("/views/add_book.fxml", "Add New Book");
    }

    @FXML
    public void openAddBorrower() {
        openWindow("/views/add_borrower.fxml", "Add New Borrower");
    }

    @FXML
    public void openReturnBook() {
        openWindow("/views/loan.fxml", "Return Book");
    }

    @FXML
    public void logout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Logout");
        confirm.setHeaderText("Are you sure?");
        confirm.setContentText("Do you want to logout from the system?");
        
        var result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Close application
            Stage stage = (Stage) btnHome.getScene().getWindow();
            stage.close();
        }
    }

    private void openWindow(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
            
            stage.setOnHidden(e -> updateStatistics());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to open window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
