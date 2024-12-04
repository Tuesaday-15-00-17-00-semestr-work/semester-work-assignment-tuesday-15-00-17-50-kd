package com.virtuallibrary.javafxapp.Controllers;

import com.virtuallibrary.javafxapp.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;


public class AddBookController implements Initializable {

    @FXML
    private AnchorPane addBookAnchorPain;

    @FXML
    private TextField authorField;

    @FXML
    private Button backRedirectButton;

    @FXML
    private TextField isbnNumberField;

    @FXML
    private Label infoLabel;

    @FXML
    private Label toUserLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField pagesField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField availableCopiesField;

    @FXML
    private Button addBookButton;

    private ViewChanger viewChanger;

    private MessageLabel messageLabel;

    private User user;

    public AddBookController() {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewChanger = new ViewChanger();
        messageLabel = new MessageLabel();
        new Session();
    }

    @FXML
    private void onBackRedirectClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(addBookAnchorPain, "account.fxml");
    }

    @FXML
    private void onAddBookClick(ActionEvent event) throws IOException, InterruptedException {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbnNumber = isbnNumberField.getText();
        int availableCopies = tryParseInt(availableCopiesField.getText(), 0);

        String json = String.format("""
            {
                "title": "%s",
                "author": "%s",
                "isbnNumber": "%s",
                "availableCopies": %d
            }
            """, title, author, isbnNumber, availableCopies);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books/create"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 406) {
            messageLabel.showUnsuccessfulMessage(infoLabel, "Cannot add the book!");
        }
        else {
            messageLabel.showSuccessfulMessage(infoLabel, "Book was successfully added!");
        }
    }

    private int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

}
