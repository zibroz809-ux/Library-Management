package com.library.services;

import com.library.database.JsonDatabase;
import com.library.models.Branch;

import java.util.ArrayList;
import java.util.List;

public class BranchService {

    private static final String FILE = "data/branches.json";

    private static BranchService instance;

    private List<Branch> branches;

    private BranchService() {
        branches = new ArrayList<>(JsonDatabase.loadList(FILE, Branch[].class));
    }

    public static BranchService getInstance() {
        if (instance == null) instance = new BranchService();
        return instance;
    }

    public List<Branch> getAll() {
        return branches;
    }

    public void add(Branch b) {
        branches.add(b);
        save();
    }

    public void update(int index, Branch b) {
        branches.set(index, b);
        save();
    }

    public void delete(int index) {
        branches.remove(index);
        save();
    }

    private void save() {
        JsonDatabase.saveList(FILE, branches);
    }
}
