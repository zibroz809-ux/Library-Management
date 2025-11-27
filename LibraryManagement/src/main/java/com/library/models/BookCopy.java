package com.library.models;

public class BookCopy {
    private int bookId;
    private int branchId;
    private int copies;

    public BookCopy(int bookId, int branchId, int copies) {
        this.bookId = bookId;
        this.branchId = branchId;
        this.copies = copies;
    }

    public int getBookId() {
        return bookId;
    }

    public int getBranchId() {
        return branchId;
    }

    public int getCopies() {
        return copies;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
