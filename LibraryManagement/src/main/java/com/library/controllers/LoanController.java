package com.library.controllers;

import com.library.models.Loan;
import com.library.services.LoanService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LoanController implements Initializable {

    @FXML private TableView<Loan> tableLoans;
    @FXML private TableColumn<Loan, Integer> colLoanId;
    @FXML private TableColumn<Loan, String> colBook;
    @FXML private TableColumn<Loan, String> colBorrower;
    @FXML private TableColumn<Loan, String> colBranch;
    @FXML private TableColumn<Loan, String> colDateOut;
    @FXML private TableColumn<Loan, String> colDueDate;
    @FXML private TableColumn<Loan, String> colDateIn;
    @FXML private TableColumn<Loan, String> colStatus;

    @FXML private Button btnAdd;
    @FXML private Button btnReturn;
    @FXML private Button btnDelete;
    @FXML private TextField searchField;
    @FXML private Button btnClear;
    @FXML private Label labelStats;
    @FXML private Label labelShowingResults;
    @FXML private Label labelLastAction;

    private final LoanService loanService = LoanService.getInstance();
    private ObservableList<Loan> loanList;
    private ObservableList<Loan> filteredList;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadAllLoans();
        setupSearchAndFilters();
        setupButtonActions();
        updateStats();
    }

    private void setupTableColumns() {
        colLoanId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getLoanId()).asObject());

        colBook.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getBook() != null ? data.getValue().getBook().getTitle() : "N/A"));

        colBorrower.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getBorrower() != null ? data.getValue().getBorrower().getName() : "N/A"));

        colBranch.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getBranch() != null ? data.getValue().getBranch().getName() : "N/A"));

        colDateOut.setCellValueFactory(data -> {
            LocalDate dateOut = data.getValue().getDateOut();
            return new javafx.beans.property.SimpleStringProperty(
                    dateOut != null ? dateOut.format(DATE_FORMAT) : "-");
        });

        colDueDate.setCellValueFactory(data -> {
            LocalDate dueDate = data.getValue().getDueDate();
            return new javafx.beans.property.SimpleStringProperty(
                    dueDate != null ? dueDate.format(DATE_FORMAT) : "-");
        });

        colDateIn.setCellValueFactory(data -> {
            LocalDate dateIn = data.getValue().getDateIn();
            return new javafx.beans.property.SimpleStringProperty(
                    dateIn != null ? dateIn.format(DATE_FORMAT) : "-");
        });

        colStatus.setCellValueFactory(data -> {
            Loan loan = data.getValue();
            String status;
            if (loan.isReturned()) {
                status = "✓ Returned";
            } else if (loan.isOverdue()) {
                status = "⚠ OVERDUE";
            } else {
                status = "📚 Active";
            }
            return new javafx.beans.property.SimpleStringProperty(status);
        });

        // Color code status column
        colStatus.setCellFactory(col -> new TableCell<Loan, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.contains("Returned")) {
                        setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                    } else if (item.contains("OVERDUE")) {
                        setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #2196F3; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }

    private void loadAllLoans() {
        loanList = FXCollections.observableArrayList(loanService.getAllLoans());
        filteredList = FXCollections.observableArrayList(loanList);
        tableLoans.setItems(filteredList);
    }

    private void setupSearchAndFilters() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterLoans(newVal));
        btnClear.setOnAction(e -> {
            searchField.clear();
            labelLastAction.setText("");
        });
    }

    private void filterLoans(String query) {
        if (query == null || query.trim().isEmpty()) {
            filteredList.setAll(loanList);
        } else {
            String lowerQuery = query.toLowerCase();
            ObservableList<Loan> filtered = FXCollections.observableArrayList(
                    loanList.stream()
                            .filter(loan -> {
                                String loanId = String.valueOf(loan.getLoanId());
                                String book = loan.getBook() != null ? loan.getBook().getTitle().toLowerCase() : "";
                                String borrower = loan.getBorrower() != null ? loan.getBorrower().getName().toLowerCase() : "";
                                String branch = loan.getBranch() != null ? loan.getBranch().getName().toLowerCase() : "";

                                return loanId.contains(lowerQuery) ||
                                        book.contains(lowerQuery) ||
                                        borrower.contains(lowerQuery) ||
                                        branch.contains(lowerQuery);
                            })
                            .collect(Collectors.toList())
            );
            filteredList.setAll(filtered);
        }
        labelShowingResults.setText("Showing " + filteredList.size() + " of " + loanList.size() + " loans");
    }

    private void setupButtonActions() {
        btnAdd.setOnAction(e -> openAddLoan());
        btnReturn.setOnAction(e -> returnLoan());
        btnDelete.setOnAction(e -> deleteLoan());
    }

    private void openAddLoan() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/add_loan.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 500, 600));
            stage.setTitle("Create New Loan");
            stage.setResizable(false);

            stage.setOnHidden(e -> {
                refresh();
                labelLastAction.setText("✓ Loan created successfully!");
            });
            stage.show();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to open Add Loan dialog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void returnLoan() {
        Loan selected = tableLoans.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a loan to return.");
            return;
        }

        if (selected.isReturned()) {
            showAlert(Alert.AlertType.WARNING, "Already Returned", "This loan has already been returned.");
            return;
        }

        // Show confirmation dialog with date picker
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Return Book");
        dialog.setHeaderText("Enter the return date for: " + selected.getBook().getTitle());

        DatePicker datePickerReturn = new DatePicker();
        datePickerReturn.setValue(LocalDate.now());

        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 20;");
        content.getChildren().addAll(
                new Label("Return Date:"),
                datePickerReturn
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> btn == ButtonType.OK ? datePickerReturn.getValue() : null);

        var result = dialog.showAndWait();
        if (result.isPresent()) {
            LocalDate returnDate = result.get();

            if (returnDate.isAfter(LocalDate.now())) {
                showAlert(Alert.AlertType.WARNING, "Invalid Date", "Return date cannot be in the future.");
                return;
            }

            if (returnDate.isBefore(selected.getDateOut())) {
                showAlert(Alert.AlertType.WARNING, "Invalid Date", "Return date cannot be before the loan date.");
                return;
            }

            selected.setDateIn(returnDate);
            loanService.updateLoan(selected);
            refresh();
            labelLastAction.setText("✓ Loan returned on " + returnDate.format(DATE_FORMAT));
            showAlert(Alert.AlertType.INFORMATION, "Success", "Loan returned successfully! ✓");
        }
    }

    private void deleteLoan() {
        Loan selected = tableLoans.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a loan to delete.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete");
        confirmation.setHeaderText("Delete this loan?");
        confirmation.setContentText("Book: " + selected.getBook().getTitle() + "\n" +
                "Borrower: " + selected.getBorrower().getName() + "\n\n" +
                "This action cannot be undone.");

        var result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int index = tableLoans.getSelectionModel().getSelectedIndex();
            loanService.deleteLoan(index);
            refresh();
            labelLastAction.setText("✓ Loan deleted");
            showAlert(Alert.AlertType.INFORMATION, "Success", "Loan deleted successfully.");
        }
    }

    private void refresh() {
        loanList.setAll(loanService.getAllLoans());
        filterLoans(searchField.getText());
        updateStats();
    }

    private void updateStats() {
        long activeLoans = loanList.stream().filter(l -> !l.isReturned()).count();
        long overdueLoans = loanList.stream().filter(Loan::isOverdue).count();
        labelStats.setText("Total Active Loans: " + activeLoans + " | Overdue: " + overdueLoans);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
