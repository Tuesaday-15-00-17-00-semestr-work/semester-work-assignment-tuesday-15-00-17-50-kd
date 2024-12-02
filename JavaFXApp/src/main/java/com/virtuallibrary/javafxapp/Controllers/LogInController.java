package com.virtuallibrary.javafxapp.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuallibrary.javafxapp.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class LogInController {

    @FXML
    private AnchorPane logInAnchorPane;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerRedirectButton;

    @FXML
    private Button signInButton;

    @FXML
    private Label infoLabel;

    private final ViewChanger viewChanger;
    private final MessageLabel messageLabel;


    public LogInController() {
        messageLabel = new MessageLabel();
        viewChanger = new ViewChanger();
    }


    @FXML
    public void onRegisterRedirectClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(logInAnchorPane, "registration.fxml");
    }


    @FXML
    public void onSignInClick(ActionEvent event) throws IOException, InterruptedException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login?username=" + username + "&password=" + password))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {

            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/users/@" + username))
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(response.body(), new TypeReference<User>() {});
            new Session(user);
            //Session.login(username, password);
            viewChanger.switchScenes(logInAnchorPane, "sample.fxml");
        }
        else {
            messageLabel.showUnsuccessfulMessage(infoLabel, "Incorrect username or password.");
        }
    }
}
