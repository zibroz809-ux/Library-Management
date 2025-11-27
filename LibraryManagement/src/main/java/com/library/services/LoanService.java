package com.library.services;

import com.library.database.JsonDatabase;
import com.library.models.*;

import java.util.List;

public class LoanService {

    private static final LoanService INSTANCE = new LoanService();
    private LoanService() {}
    public static LoanService getInstance() { return INSTANCE; }

    private JsonDatabase db = JsonDatabase.getInstance();

    public List<Loan> getAllLoans() {
        return db.getLoans();
    }

    public int generateId() {
        return getAllLoans().size() + 1;
    }

    public void addLoan(Loan loan) {
        db.getLoans().add(loan);
        db.saveData();
    }

    public void updateLoan(Loan loan) {
        // Find and update the loan in the list by ID
        List<Loan> loans = db.getLoans();
        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getLoanId() == loan.getLoanId()) {
                loans.set(i, loan);
                db.saveData();
                return;
            }
        }
    }

    public void deleteLoan(int index) {
        db.getLoans().remove(index);
        db.saveData();
    }
    
}
