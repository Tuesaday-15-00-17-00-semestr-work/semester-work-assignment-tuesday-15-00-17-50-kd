package com.virtuallibrary.javafxapp.Controllers;

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
import java.util.ResourceBundle;

public class UserCardController implements Initializable {

    @FXML
    private Button deleteUserButton;

    @FXML
    private Label emailLabel;

    @FXML
    private Label uidLabel;

    @FXML
    private Label usernameLabel;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void onDeleteUserClick(ActionEvent event) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/users/" + user.getUserId()))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            deleteUserButton.setDisable(true);
            uidLabel.setText(" - ");
            usernameLabel.setText(" - ");
            emailLabel.setText(" - ");
        }
    }

    public void setUserInfo(User user) {
        this.user = user;
        uidLabel.setText(Integer.toString(user.getUserId()));
        usernameLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
        if (user.getRoleId() == 2) {
            hideDeleteUserButtonIfUserAdmin();
        }
    }


    private void hideDeleteUserButtonIfUserAdmin() {
        deleteUserButton.setDisable(true);
        deleteUserButton.setVisible(false);
    }
}
