package com.library.controllers;

import com.library.models.Borrower;
import com.library.services.BorrowerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditBorrowerController {

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtAddress;
    @FXML private TextField txtPhone;
    @FXML private Button btnSave;

    private static int borrowerIndex;
    private static Borrower selectedBorrower;

    private BorrowerService service = BorrowerService.getInstance();

    // dipanggil dari BorrowerController sebelum form dibuka
    public static void setInitialData(int index, Borrower borrower) {
        borrowerIndex = index;
        selectedBorrower = borrower;
    }

    @FXML
    public void initialize() {
        if (selectedBorrower != null) {
            txtId.setText(String.valueOf(selectedBorrower.getCardNo()));
            txtName.setText(selectedBorrower.getName());
            txtAddress.setText(selectedBorrower.getAddress());
            txtPhone.setText(selectedBorrower.getPhone());
        }

        btnSave.setOnAction(e -> saveChanges());
    }

    private void saveChanges() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();

            Borrower updated = new Borrower(id, name, address, phone);
            service.update(borrowerIndex, updated);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
