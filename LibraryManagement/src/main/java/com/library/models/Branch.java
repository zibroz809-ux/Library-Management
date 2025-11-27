package com.library.models;

public class Branch {
    private int branchId;
    private String branchName;
    private String branchAddress;
    private String Name;

    public Branch(int branchId, String branchName, String branchAddress) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
    }

    public int getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
