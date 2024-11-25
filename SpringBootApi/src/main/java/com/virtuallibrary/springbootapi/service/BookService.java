package com.virtuallibrary.springbootapi.service;

import com.virtuallibrary.springbootapi.api.model.Book;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final DatabaseService db;

    public BookService() {
        db = new DatabaseService();
    }

    public Book createBook(Book book) throws SQLException {
        return db.createBook(book);
    }

    public Book getBookById(int bookId) throws SQLException {
        return db.getBookById(bookId);
    }

    public Book getBookByIsbn(String isbnNumber) throws SQLException {
        return db.getBookByIsbn(isbnNumber);
    }

    public List<Book> getAllBooks() throws SQLException {
        return db.getAllBooks();
    }

    public boolean updateBook(Book book) throws SQLException {
        return db.updateBook(book);
    }

    public boolean deleteBook(int bookId) throws SQLException {
        return db.deleteBook(bookId);
    }
}