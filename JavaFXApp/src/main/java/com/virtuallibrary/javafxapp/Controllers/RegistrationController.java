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


public class RegistrationController {

    @FXML
    private AnchorPane registrationAnchorPane;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Button registerButton;

    @FXML
    private Button signInRedirectButton;

    @FXML
    private Label infoLabel;
    private final ViewChanger viewChanger;
    private final MessageLabel messageLabel;


    public RegistrationController() {
        messageLabel = new MessageLabel();
        viewChanger = new ViewChanger();
    }


    @FXML
    public void onSignInRedirectClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(registrationAnchorPane, "log-in.fxml");
    }


    public void onRegisterClick(ActionEvent event) throws IOException, InterruptedException {
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
            messageLabel.showUnsuccessfulMessage(infoLabel, "User already exist!");
        }
        else {
            registerButton.setDisable(true);
            messageLabel.showSuccessfulMessage(infoLabel,"User was successfully added! Redirecting to sign in...");
            viewChanger.switchSceneWithDelay(registrationAnchorPane, "log-in.fxml", 2);
        }
    }
}

