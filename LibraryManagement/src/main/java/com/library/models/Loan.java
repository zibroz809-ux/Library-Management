package com.library.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loan {
    private int loanId;
    private Book book;
    private Branch branch;
    private Borrower borrower;
    private LocalDate dateOut;
    private LocalDate dueDate;
    private LocalDate dateIn;  // null if not yet returned

    public Loan(int loanId, Book book, Branch branch, Borrower borrower, LocalDate dateOut, LocalDate dueDate) {
        this.loanId = loanId;
        this.book = book;
        this.branch = branch;
        this.borrower = borrower;
        this.dateOut = dateOut;
        this.dueDate = dueDate;
        this.dateIn = null;
    }

    public int getLoanId() {
        return loanId;
    }

    public Book getBook() {
        return book;
    }

    public Branch getBranch() {
        return branch;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }

    public boolean isOverdue() {
        return dateIn == null && LocalDate.now().isAfter(dueDate);
    }

    public boolean isReturned() {
        return dateIn != null;
    }

    public String formatDate(LocalDate date) {
        if (date == null) return "-";
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", book=" + book.getTitle() +
                ", borrower=" + borrower.getName() +
                '}';
    }
}
