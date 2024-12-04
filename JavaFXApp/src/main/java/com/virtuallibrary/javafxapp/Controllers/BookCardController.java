package com.virtuallibrary.javafxapp.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuallibrary.javafxapp.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;
import java.net.http.HttpRequest.BodyPublishers;

public class BookCardController implements Initializable {

    @FXML
    private Label authorLabel;

    @FXML
    private Label availableCopiesLabel;

    @FXML
    private Label isbnLabel;

    @FXML
    private Button rentThisBookButton;

    @FXML
    private Button deleteBookButton;

    @FXML
    private Label titleLabel;

    private Book book;

    public BookCardController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableAdminPanel();
        showAdminPanelIfAdmin();
    }

    @FXML
    private void onRentThisBookClick(ActionEvent event) throws IOException, InterruptedException {
        rentThisBookButton.setDisable(true);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/transactions/book/borrow?userId=" + Session.getUser().getUserId() + "&bookId=" + book.getBookId()))
                .POST(BodyPublishers.noBody())
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 201) {
            System.out.println("Cannot borrow this book");
        }
        if (book.getAvailableCopies() > 0) {
            availableCopiesLabel.setText(Integer.toString(book.getAvailableCopies() - 1));
        }
    }

    @FXML
    private void onDeleteBookClick(ActionEvent event) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books/" + book.getBookId()))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        disableAdminPanel();
        rentThisBookButton.setDisable(true);
        setDeletedBookLabelInfo();
    }

    private void showAdminPanelIfAdmin() {
        if (Session.getUser().getRoleId() == 2) {
            deleteBookButton.setDisable(false);
            deleteBookButton.setVisible(true);
        }
    }

    private void disableAdminPanel() {
        deleteBookButton.setDisable(true);
        deleteBookButton.setVisible(false);
    }

    private void setDeletedBookLabelInfo() {
        titleLabel.setText("Book was deleted!");
        authorLabel.setText(" - ");
        availableCopiesLabel.setText(" - ");
        isbnLabel.setText(" - ");
        availableCopiesLabel.setText(" - ");
    }

    public void setBookCardInfo(Book book) throws IOException, InterruptedException {
        this.book = book;
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        availableCopiesLabel.setText(Integer.toString(book.getAvailableCopies()));
        isbnLabel.setText(book.getIsbnNumber());
        if (book.getAvailableCopies() < 1) {
            rentThisBookButton.setDisable(true);
        }


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/transactions/user/" + Session.getUser().getUserId()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 404) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Transaction> userTransactions  = mapper.readValue(response.body(), new TypeReference<List<Transaction>>() {});

        for (Transaction transaction : userTransactions) {
            if (transaction.getBookId() == book.getBookId() && transaction.getAction().equals("borrowed")) {
                rentThisBookButton.setDisable(true);
                return;
            }
        }
    }
}
