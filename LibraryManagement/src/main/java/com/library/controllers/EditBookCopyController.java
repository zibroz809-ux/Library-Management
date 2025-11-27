package com.library.controllers;

import com.library.models.BookCopy;
import com.library.services.BookCopyService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditBookCopyController {

    @FXML private TextField txtBookId;
    @FXML private TextField txtBranchId;
    @FXML private TextField txtCopies;

    @FXML private Button btnSave;

    private static int copyIndex;
    private static BookCopy selectedCopy;

    private BookCopyService service = BookCopyService.getInstance();

    // dipanggil dari BookCopyController
    public static void setInitialData(int index, BookCopy bc) {
        copyIndex = index;
        selectedCopy = bc;
    }

    @FXML
    public void initialize() {

        if (selectedCopy != null) {
            txtBookId.setText(String.valueOf(selectedCopy.getBookId()));
            txtBranchId.setText(String.valueOf(selectedCopy.getBranchId()));
            txtCopies.setText(String.valueOf(selectedCopy.getCopies()));
        }

        btnSave.setOnAction(e -> saveChanges());
    }

    private void saveChanges() {
        try {
            int bookId = Integer.parseInt(txtBookId.getText());
            int branchId = Integer.parseInt(txtBranchId.getText());
            int copies = Integer.parseInt(txtCopies.getText());

            BookCopy updated = new BookCopy(bookId, branchId, copies);
            service.update(copyIndex, updated);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
