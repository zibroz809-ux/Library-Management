package com.library.controllers;

import com.library.models.Branch;
import com.library.services.BranchService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditBranchController {

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtAddress;
    @FXML private Button btnSave;

    private static int branchIndex;
    private static Branch selectedBranch;

    private BranchService service = BranchService.getInstance();

    // menerima data dari BranchController
    public static void setInitialData(int index, Branch branch) {
        branchIndex = index;
        selectedBranch = branch;
    }

    @FXML
    public void initialize() {

        if (selectedBranch != null) {
            txtId.setText(String.valueOf(selectedBranch.getBranchId()));
            txtName.setText(selectedBranch.getBranchName());
            txtAddress.setText(selectedBranch.getBranchAddress());
        }

        btnSave.setOnAction(e -> saveChanges());
    }

    private void saveChanges() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            String address = txtAddress.getText();

            Branch updated = new Branch(id, name, address);
            service.update(branchIndex, updated);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
