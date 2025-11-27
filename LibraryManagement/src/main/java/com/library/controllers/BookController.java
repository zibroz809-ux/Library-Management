package com.library.controllers;

import com.library.models.Book;
import com.library.services.BookService;
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
import java.util.stream.Collectors;

public class BookController implements Initializable {

    @FXML private TableView<Book> tableBooks;
    @FXML private TableColumn<Book, Integer> colId;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colPublisher;
    @FXML private TableColumn<Book, String> colAuthors;

    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;
    @FXML private TextField searchField;
    @FXML private Label labelStats;
    @FXML private Label labelShowingResults;
    @FXML private Label labelLastAction;

    private BookService service = BookService.getInstance();
    private ObservableList<Book> bookList;
    private ObservableList<Book> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadAllBooks();
        setupSearchAndFilters();
        setupButtonActions();
        updateStats();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getBookId()).asObject());
        colTitle.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        colPublisher.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPublisher()));
        colAuthors.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                String.join(", ", data.getValue().getAuthors())
        ));
    }

    private void loadAllBooks() {
        bookList = FXCollections.observableArrayList(service.getAllBooks());
        filteredList = FXCollections.observableArrayList(bookList);
        tableBooks.setItems(filteredList);
    }

    private void setupSearchAndFilters() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterBooks(newVal));
        btnClear.setOnAction(e -> {
            searchField.clear();
            labelLastAction.setText("");
        });
    }

    private void filterBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            filteredList.setAll(bookList);
        } else {
            String lowerQuery = query.toLowerCase();
            ObservableList<Book> filtered = FXCollections.observableArrayList(
                    bookList.stream()
                            .filter(book -> {
                                String title = book.getTitle().toLowerCase();
                                String publisher = book.getPublisher().toLowerCase();
                                String authors = String.join(", ", book.getAuthors()).toLowerCase();
                                
                                return title.contains(lowerQuery) ||
                                        publisher.contains(lowerQuery) ||
                                        authors.contains(lowerQuery);
                            })
                            .collect(Collectors.toList())
            );
            filteredList.setAll(filtered);
        }
        labelShowingResults.setText("Showing " + filteredList.size() + " of " + bookList.size() + " books");
    }

    private void setupButtonActions() {
        btnDelete.setOnAction(e -> deleteBook());
        btnAdd.setOnAction(e -> openAddForm());
        btnEdit.setOnAction(e -> openEditForm());
        
        tableBooks.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
             newScene.windowProperty().addListener((obs2, oldWindow, newWindow) -> {
                if (newWindow != null) {
                    newWindow.setOnShown(e -> refreshTable());
                 }
             });
         }
        });
    }

    private void deleteBook() {
        Book selected = tableBooks.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to delete.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete");
        confirmation.setHeaderText("Delete this book?");
        confirmation.setContentText("Title: " + selected.getTitle() + "\n" +
                "Author: " + String.join(", ", selected.getAuthors()) + "\n\n" +
                "This action cannot be undone.");

        var result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int index = tableBooks.getSelectionModel().getSelectedIndex();
            service.deleteBook(index);
            refreshTable();
            labelLastAction.setText("✓ Book deleted successfully");
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book deleted successfully.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void openAddForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/add_book.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 500));
            stage.setTitle("Add Book");
            stage.setResizable(false);

            stage.setOnHidden(e -> {
                refreshTable();
                labelLastAction.setText("✓ Book added successfully");
            });

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        bookList.setAll(service.getAllBooks());
        filterBooks(searchField.getText());
        updateStats();
    }

    private void openEditForm() {
        try {
            Book selected = tableBooks.getSelectionModel().getSelectedItem();
            int index = tableBooks.getSelectionModel().getSelectedIndex();

            if (selected == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to edit.");
                return;
            }
            
            EditBookController.setInitialData(index, selected);

            Parent root = FXMLLoader.load(getClass().getResource("/views/edit_book.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 500));
            stage.setTitle("Edit Book");
            stage.setResizable(false);
            stage.show();

            stage.setOnHidden(e -> {
                refreshTable();
                labelLastAction.setText("✓ Book updated successfully");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStats() {
        labelStats.setText("Total Books: " + bookList.size());
    }
}
