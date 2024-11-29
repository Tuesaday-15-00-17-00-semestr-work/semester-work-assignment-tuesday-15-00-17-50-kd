package com.virtuallibrary.springbootapi.service;

import com.virtuallibrary.springbootapi.api.model.Transaction;
import java.sql.SQLException;
import java.util.List;

public class TransactionService {
    private final DatabaseService db;

    public TransactionService() {
        db = new DatabaseService();
    }

    public boolean takeBook(int userId, int bookId) throws SQLException {
        return db.takeBook(userId, bookId);
    }

    public boolean returnBook(int userId, int bookId) throws SQLException {
        return db.returnBook(userId, bookId);
    }

    public List<Transaction> getTransactionsByUser(int userId) throws SQLException {
        return db.getTransactionsByUser(userId);
    }

    public List<Transaction> getTransactionsByBook(int bookId) throws SQLException {
        return db.getTransactionsByBook(bookId);
    }
}
