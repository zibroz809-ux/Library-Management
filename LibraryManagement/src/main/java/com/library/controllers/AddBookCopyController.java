package com.library.controllers;

import com.library.models.BookCopy;
import com.library.services.BookCopyService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBookCopyController {

    @FXML private TextField txtBookId;
    @FXML private TextField txtBranchId;
    @FXML private TextField txtCopies;

    @FXML private Button btnSave;

    private BookCopyService service = BookCopyService.getInstance();

    @FXML
    private void initialize() {
        btnSave.setOnAction(e -> saveCopy());
    }

    private void saveCopy() {
        try {
            int bookId = Integer.parseInt(txtBookId.getText());
            int branchId = Integer.parseInt(txtBranchId.getText());
            int copies = Integer.parseInt(txtCopies.getText());

            BookCopy bc = new BookCopy(bookId, branchId, copies);
            service.add(bc);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
