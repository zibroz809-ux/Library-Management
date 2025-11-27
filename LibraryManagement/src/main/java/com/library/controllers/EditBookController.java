package com.library.controllers;

import com.library.models.Book;
import com.library.services.BookService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class EditBookController {

    @FXML private TextField txtId;
    @FXML private TextField txtTitle;
    @FXML private TextField txtPublisher;
    @FXML private TextField txtAuthors;

    @FXML private Button btnSave;

    private static int bookIndex;
    private static Book selectedBook;

    private BookService service = BookService.getInstance();

    public static void setInitialData(int index, Book book) {
        bookIndex = index;
        selectedBook = book;
    }

    @FXML
    public void initialize() {
        // Pre-fill existing data
        if (selectedBook != null) {
            txtId.setText(String.valueOf(selectedBook.getBookId()));
            txtTitle.setText(selectedBook.getTitle());
            txtPublisher.setText(selectedBook.getPublisher());
            txtAuthors.setText(String.join(", ", selectedBook.getAuthors()));
        }

        btnSave.setOnAction(e -> saveChanges());
    }

    private void saveChanges() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String title = txtTitle.getText();
            String publisher = txtPublisher.getText();
            List<String> authors = Arrays.asList(txtAuthors.getText().split(","));

            Book updatedBook = new Book(id, title, publisher, authors);

            service.updateBook(bookIndex, updatedBook);

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
