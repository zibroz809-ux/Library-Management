package com.library.controllers;

import com.library.models.Branch;
import com.library.services.BranchService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBranchController {

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtAddress;
    @FXML private Button btnSave;

    private BranchService service = BranchService.getInstance();

    @FXML
    private void initialize() {
        btnSave.setOnAction(e -> saveBranch());
    }

    private void saveBranch() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String address = txtAddress.getText();

            Branch b = new Branch(id, name, address);
            service.add(b);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
