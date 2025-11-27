package com.library.services;

import com.library.database.JsonDatabase;
import com.library.models.Borrower;

import java.util.ArrayList;
import java.util.List;

public class BorrowerService {

    private static final String FILE = "data/borrowers.json";

    private static BorrowerService instance;

    private List<Borrower> borrowers;

    private BorrowerService() {
        borrowers = new ArrayList<>(JsonDatabase.loadList(FILE, Borrower[].class));
    }

    public static BorrowerService getInstance() {
        if (instance == null) instance = new BorrowerService();
        return instance;
    }

    public List<Borrower> getAll() {
        return borrowers;
    }

    public void add(Borrower b) {
        borrowers.add(b);
        save();
    }

    public void update(int index, Borrower b) {
        borrowers.set(index, b);
        save();
    }

    public void delete(int index) {
        borrowers.remove(index);
        save();
    }

    private void save() {
        JsonDatabase.saveList(FILE, borrowers);
    }
}
