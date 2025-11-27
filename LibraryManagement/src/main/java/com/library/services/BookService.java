package com.library.services;

import com.library.database.JsonDatabase;
import com.library.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private static final String BOOKS_FILE = "data/books.json";

    private static BookService instance;

    private List<Book> books;

    private BookService() {
        books = new ArrayList<>(JsonDatabase.loadList(BOOKS_FILE, Book[].class));
    }

    public static BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
        save();
    }

    public void updateBook(int index, Book updatedBook) {
        books.set(index, updatedBook);
        save();
    }

    public void deleteBook(int index) {
        books.remove(index);
        save();
    }

    private void save() {
        JsonDatabase.saveList(BOOKS_FILE, books);
    }
}
