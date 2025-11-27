package com.library.services;

import com.library.database.JsonDatabase;
import com.library.models.BookCopy;

import java.util.ArrayList;
import java.util.List;

public class BookCopyService {

    private static final String FILE = "data/book_copies.json";

    private static BookCopyService instance;

    private List<BookCopy> copies;

    private BookCopyService() {
        copies = new ArrayList<>(JsonDatabase.loadList(FILE, BookCopy[].class));
    }

    public static BookCopyService getInstance() {
        if (instance == null) instance = new BookCopyService();
        return instance;
    }

    public List<BookCopy> getAll() {
        return copies;
    }

    public void add(BookCopy bc) {
        copies.add(bc);
        save();
    }

    public void update(int index, BookCopy bc) {
        copies.set(index, bc);
        save();
    }

    public void delete(int index) {
        copies.remove(index);
        save();
    }

    private void save() {
        JsonDatabase.saveList(FILE, copies);
    }

    public int getCopies(int bookId, int branchId) {
        for (BookCopy bc : copies) {
            if (bc.getBookId() == bookId && bc.getBranchId() == branchId) {
                return bc.getCopies();
            }
        }
        return 0;
    }

    public void setCopies(int bookId, int branchId, int newCopies) {
        for (BookCopy bc : copies) {
            if (bc.getBookId() == bookId && bc.getBranchId() == branchId) {
                bc.setCopies(newCopies);
                save();
                return;
            }
        }
    }
}
