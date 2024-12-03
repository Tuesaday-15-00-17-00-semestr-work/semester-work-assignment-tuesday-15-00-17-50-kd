package com.virtuallibrary.javafxapp.Models;

public class Book {

    private int bookId;
    private String title;
    private String author;
    private String isbnNumber;
    private int availableCopies;

    public Book() {

    }

    public Book(int bookId, String title, String author, String isbnNumber, int availableCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbnNumber = isbnNumber;
        this.availableCopies = availableCopies;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}