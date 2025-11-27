package com.library.models;

import java.util.List;

public class Book {
    private int bookId;
    private String title;
    private String publisher;
    private List<String> authors;

    public Book(int bookId, String title, String publisher, List<String> authors) {
        this.bookId = bookId;
        this.title = title;
        this.publisher = publisher;
        this.authors = authors;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getPublisher() { return publisher; }
    public List<String> getAuthors() { return authors; }

    public void setBookId(int bookId) { this.bookId = bookId; }
    public void setTitle(String title) { this.title = title; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setAuthors(List<String> authors) { this.authors = authors; }
}
