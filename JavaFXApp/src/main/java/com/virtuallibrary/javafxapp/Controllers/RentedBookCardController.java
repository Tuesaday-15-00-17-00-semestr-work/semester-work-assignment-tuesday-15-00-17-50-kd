package com.virtuallibrary.javafxapp.Controllers;

import com.virtuallibrary.javafxapp.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RentedBookCardController {

    @FXML
    private Label authorLabel;

    @FXML
    private Label isbnNumberLabel;

    @FXML
    private Label availableCopiesLabel;

    @FXML
    private Button returnBookButton;

    @FXML
    private Label rentedAtLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label yearLabel;
    private final User user;
    private Book book;

    public RentedBookCardController() {
        new Session();
        user = Session.getUser();
    }

    @FXML
    private void onReturnBookClick(ActionEvent event) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/transactions/book/return?userId=" + Session.getUser().getUserId() + "&bookId=" + book.getBookId()))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 201) {
            System.out.println("Cannot return this book: " + book.getBookId());
            return;
        }
        returnBookButton.setDisable(true);
        returnBookButton.setText("Book returned");
    }

    public void setRentedBookCardInfo(Book book) {
        this.book = book;
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        isbnNumberLabel.setText(book.getIsbnNumber());
        availableCopiesLabel.setText(Integer.toString(book.getAvailableCopies()));
    }
}