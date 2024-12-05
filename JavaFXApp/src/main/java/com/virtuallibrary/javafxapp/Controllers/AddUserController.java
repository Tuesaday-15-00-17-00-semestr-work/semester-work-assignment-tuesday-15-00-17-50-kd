package com.virtuallibrary.javafxapp.Controllers;

import com.virtuallibrary.javafxapp.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AddUserController {

    @FXML
    private AnchorPane addUserAnchorPain;

    @FXML
    private Button addUserButton;

    @FXML
    private Button backRedirectButton;

    @FXML
    private TextField emailField;


    @FXML
    private Label infoLabel;


    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    private ViewChanger viewChanger;
    private MessageLabel messageLabel;

    public AddUserController() {
        viewChanger = new ViewChanger();
        messageLabel = new MessageLabel();
    }

    @FXML
    void onAddUserClick(ActionEvent event) throws IOException, InterruptedException {
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        String json = String.format("""
            {
                "username": "%s",
                "roleId": %d,
                "email": "%s",
                "password": "%s"
            }
            """, username, 1, email, password);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/users/create"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 406) {
            messageLabel.showUnsuccessfulMessage(infoLabel, "User already exist");
        }
        else {
            messageLabel.showSuccessfulMessage(infoLabel,"User was successfully added!");
        }
    }

    @FXML
    void onBackRedirectClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(addUserAnchorPain, "account.fxml");
    }

}
