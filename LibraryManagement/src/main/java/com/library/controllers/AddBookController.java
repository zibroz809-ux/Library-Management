package com.library.controllers;

import com.library.models.Book;
import com.library.services.BookService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class AddBookController {

    @FXML private TextField txtId;
    @FXML private TextField txtTitle;
    @FXML private TextField txtPublisher;
    @FXML private TextField txtAuthors;

    @FXML private Button btnSave;

    private BookService service = BookService.getInstance();

    @FXML
    private void initialize() {
        btnSave.setOnAction(e -> saveBook());
    }

    private void saveBook() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String title = txtTitle.getText();
            String publisher = txtPublisher.getText();

            List<String> authors = Arrays.asList(txtAuthors.getText().split(","));

            Book newBook = new Book(id, title, publisher, authors);
            service.addBook(newBook);

            // close window
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
