package com.library.controllers;

import com.library.models.BookCopy;
import com.library.services.BookCopyService;
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

public class BookCopyController implements Initializable {

    @FXML private TableView<BookCopy> tableCopies;
    @FXML private TableColumn<BookCopy, Integer> colBookId;
    @FXML private TableColumn<BookCopy, Integer> colBranchId;
    @FXML private TableColumn<BookCopy, Integer> colCopies;

    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;

    private BookCopyService service = BookCopyService.getInstance();
    private ObservableList<BookCopy> list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colBookId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getBookId()).asObject());
        colBranchId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getBranchId()).asObject());
        colCopies.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCopies()).asObject());

        list = FXCollections.observableArrayList(service.getAll());
        tableCopies.setItems(list);

        btnAdd.setOnAction(e -> openAddForm());
        btnEdit.setOnAction(e -> openEditForm());
        btnDelete.setOnAction(e -> deleteCopy());
    }

    private void deleteCopy() {
        int index = tableCopies.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            showAlert("Select a row to delete.");
            return;
        }

        service.delete(index);
        list.remove(index);
    }

    private void openAddForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/add_book_copy.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Book Copy");

            stage.setOnHidden(e -> refresh());

            stage.show();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void openEditForm() {
        int index = tableCopies.getSelectionModel().getSelectedIndex();
        BookCopy selected = tableCopies.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Select a row to edit.");
            return;
        }

        EditBookCopyController.setInitialData(index, selected);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/edit_book_copy.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Book Copy");

            stage.setOnHidden(e -> refresh());

            stage.show();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void refresh() {
        list.setAll(service.getAll());
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
