package org.example.zyropos;

import java.sql.Timestamp;

public class Sale {
    private int saleId;
    private double totalAmount;
    private int branchId;
    private String cashierUsername;
    private Timestamp saleDate;

    public Sale(int saleId, double totalAmount, int branchId, String cashierUsername, Timestamp saleDate) {
        this.saleId = saleId;
        this.totalAmount = totalAmount;
        this.branchId = branchId;
        this.cashierUsername = cashierUsername;
        this.saleDate = saleDate;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getCashierUsername() {
        return cashierUsername;
    }

    public void setCashierUsername(String cashierUsername) {
        this.cashierUsername = cashierUsername;
    }

    public Timestamp getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Timestamp saleDate) {
        this.saleDate = saleDate;
    }
}
