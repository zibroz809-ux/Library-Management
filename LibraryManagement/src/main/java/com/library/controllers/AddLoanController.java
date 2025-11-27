package com.library.controllers;

import com.library.models.*;
import com.library.services.*;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AddLoanController {

    @FXML private ComboBox<Book> comboBook;
    @FXML private ComboBox<Branch> comboBranch;
    @FXML private ComboBox<Borrower> comboBorrower;
    @FXML private DatePicker datePickerOut;
    @FXML private DatePicker datePickerDue;

    // singleton services
    private final BookService bookService = BookService.getInstance();
    private final BranchService branchService = BranchService.getInstance();
    private final BorrowerService borrowerService = BorrowerService.getInstance();
    private final LoanService loanService = LoanService.getInstance();

    @FXML
    public void initialize() {
        // Load combo box data
        comboBook.getItems().setAll(bookService.getAllBooks());
        comboBook.setStyle("-fx-font-size: 12;");

        comboBranch.getItems().setAll(branchService.getAll());
        comboBranch.setStyle("-fx-font-size: 12;");

        comboBorrower.getItems().setAll(borrowerService.getAll());
        comboBorrower.setStyle("-fx-font-size: 12;");

        // Set default dates
        datePickerOut.setValue(LocalDate.now());
        datePickerDue.setValue(LocalDate.now().plusDays(14)); // 14 day default loan period

        // Make ComboBoxes display book titles, borrower names, and branch names
        comboBook.setCellFactory(param -> new javafx.scene.control.ListCell<Book>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitle());
            }
        });

        comboBook.setButtonCell(new javafx.scene.control.ListCell<Book>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitle());
            }
        });

        comboBorrower.setCellFactory(param -> new javafx.scene.control.ListCell<Borrower>() {
            @Override
            protected void updateItem(Borrower item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getName());
            }
        });

        comboBorrower.setButtonCell(new javafx.scene.control.ListCell<Borrower>() {
            @Override
            protected void updateItem(Borrower item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getName());
            }
        });

        comboBranch.setCellFactory(param -> new javafx.scene.control.ListCell<Branch>() {
            @Override
            protected void updateItem(Branch item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getName());
            }
        });

        comboBranch.setButtonCell(new javafx.scene.control.ListCell<Branch>() {
            @Override
            protected void updateItem(Branch item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getName());
            }
        });
    }

    @FXML
    public void borrow() {

        // Validation: Check all fields are selected
        if (comboBook.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select a book.");
            return;
        }
        if (comboBranch.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select a branch.");
            return;
        }
        if (comboBorrower.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select a borrower.");
            return;
        }
        if (datePickerOut.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select a date out.");
            return;
        }
        if (datePickerDue.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select a due date.");
            return;
        }

        LocalDate dateOut = datePickerOut.getValue();
        LocalDate dateDue = datePickerDue.getValue();

        // Validation: Due date must be after date out
        if (dateDue.isBefore(dateOut) || dateDue.isEqual(dateOut)) {
            showAlert(Alert.AlertType.WARNING, "Invalid Dates", "Due date must be after the date out.");
            return;
        }

        // Validation: Loan period should be reasonable (1-90 days)
        long days = ChronoUnit.DAYS.between(dateOut, dateDue);
        if (days < 1 || days > 90) {
            showAlert(Alert.AlertType.WARNING, "Invalid Loan Period", "Loan period must be between 1 and 90 days.");
            return;
        }

        try {
            Loan loan = new Loan(
                    loanService.generateId(),
                    comboBook.getValue(),
                    comboBranch.getValue(),
                    comboBorrower.getValue(),
                    dateOut,
                    dateDue
            );

            loanService.addLoan(loan);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Loan created successfully! ✓\n\nBook: " + 
                    comboBook.getValue().getTitle() + "\nBorrower: " + comboBorrower.getValue().getName() + 
                    "\nDue Date: " + dateDue);
            close();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create loan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void cancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) comboBook.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
