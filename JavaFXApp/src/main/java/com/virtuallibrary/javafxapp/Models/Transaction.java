package com.virtuallibrary.javafxapp.Models;

public class Transaction {
    private int transactionId;
    private int userId;
    private int bookId;
    private String action;
    private String date;

    public Transaction() {

    }

    public Transaction(int transactionId, int userId, int bookId, String action, String date) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.action = action;
        this.date = date;
    }

    public Transaction(int userId, int bookId, String action, String date) {
        this.userId = userId;
        this.bookId = bookId;
        this.action = action;
        this.date = date;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", action='" + action + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
