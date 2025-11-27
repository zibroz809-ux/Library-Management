package com.library.controllers;

import com.library.models.Borrower;
import com.library.services.BorrowerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBorrowerController {

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtAddress;
    @FXML private TextField txtPhone;
    @FXML private Button btnSave;

    private BorrowerService service = BorrowerService.getInstance();

    @FXML
    private void initialize() {
        btnSave.setOnAction(e -> saveBorrower());
    }

    private void saveBorrower() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();

            Borrower b = new Borrower(id, name, address, phone);
            service.add(b);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
