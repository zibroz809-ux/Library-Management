package com.library.controllers;

import com.library.models.Branch;
import com.library.services.BranchService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BranchController implements Initializable {

    @FXML private TableView<Branch> tableBranches;
    @FXML private TableColumn<Branch, Integer> colId;
    @FXML private TableColumn<Branch, String> colName;
    @FXML private TableColumn<Branch, String> colAddress;

    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;

    private BranchService service = BranchService.getInstance();
    private ObservableList<Branch> list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getBranchId()).asObject());
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBranchName()));
        colAddress.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBranchAddress()));

        list = FXCollections.observableArrayList(service.getAll());
        tableBranches.setItems(list);

        btnDelete.setOnAction(e -> deleteBranch());
        btnAdd.setOnAction(e -> openAddForm());
        btnEdit.setOnAction(e -> openEditForm());
    }

    private void deleteBranch() {
        Branch selected = tableBranches.getSelectionModel().getSelectedItem();
        int index = tableBranches.getSelectionModel().getSelectedIndex();
        if (selected == null) {
            showAlert("Choose a branch to delete.");
            return;
        }
        service.delete(index);
        list.remove(index);
    }

    private void openAddForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/add_branch.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Branch");

            stage.setOnHidden(e -> refreshTable());

            stage.show();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void openEditForm() {
        try {
            Branch selected = tableBranches.getSelectionModel().getSelectedItem();
            int index = tableBranches.getSelectionModel().getSelectedIndex();

            if (selected == null) {
                showAlert("Choose a branch to edit.");
                return;
            }

            EditBranchController.setInitialData(index, selected);

            Parent root = FXMLLoader.load(getClass().getResource("/views/edit_branch.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Branch");

            stage.setOnHidden(e -> refreshTable());

            stage.show();

        } catch (Exception e) { e.printStackTrace(); }
    }

    private void refreshTable() {
        list.setAll(service.getAll());
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
