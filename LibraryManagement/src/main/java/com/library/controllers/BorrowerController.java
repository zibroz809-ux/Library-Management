package com.library.controllers;

import com.library.models.Borrower;
import com.library.services.BorrowerService;
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

public class BorrowerController implements Initializable {

    @FXML private TableView<Borrower> tableBorrowers;
    @FXML private TableColumn<Borrower, Integer> colId;
    @FXML private TableColumn<Borrower, String> colName;
    @FXML private TableColumn<Borrower, String> colAddress;
    @FXML private TableColumn<Borrower, String> colPhone;

    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;

    private BorrowerService service = BorrowerService.getInstance();
    private ObservableList<Borrower> list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCardNo()).asObject());
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        colAddress.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAddress()));
        colPhone.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPhone()));

        tableBorrowers.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obs2, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        newWindow.setOnShown(e -> refreshTable());
                    }
                });
            }
        });


        list = FXCollections.observableArrayList(service.getAll());
        tableBorrowers.setItems(list);

        btnDelete.setOnAction(e -> deleteBorrower());
        btnAdd.setOnAction(e -> openAddForm());
        btnEdit.setOnAction(e -> openEditForm());
    }

    private void deleteBorrower() {
        Borrower selected = tableBorrowers.getSelectionModel().getSelectedItem();
        int index = tableBorrowers.getSelectionModel().getSelectedIndex();
        if (selected == null) {
            showAlert("Choose a borrower to delete.");
            return;
        }
        service.delete(index);
        list.remove(index);
    }

    private void openAddForm() {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/views/add_borrower.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Borrower");

        stage.setOnHidden(e -> refreshTable());  // <<--- Tambahkan ini

        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        }
    }


    private void openEditForm() {
    try {
        Borrower selected = tableBorrowers.getSelectionModel().getSelectedItem();
        int index = tableBorrowers.getSelectionModel().getSelectedIndex();

        if (selected == null) {
            showAlert("Choose a borrower to edit.");
            return;
        }

        EditBorrowerController.setInitialData(index, selected);

        Parent root = FXMLLoader.load(getClass().getResource("/views/edit_borrower.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Edit Borrower");

        stage.setOnHidden(e -> refreshTable()); // <<--- Tambahkan ini

        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }


    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void refreshTable() {
    list.setAll(service.getAll());
    }

}
