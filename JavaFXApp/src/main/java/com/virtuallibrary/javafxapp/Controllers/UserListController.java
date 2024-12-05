package com.virtuallibrary.javafxapp.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuallibrary.javafxapp.LibraryApplication;
import com.virtuallibrary.javafxapp.Models.User;
import com.virtuallibrary.javafxapp.Models.ViewChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;

public class UserListController implements Initializable {

    @FXML
    private AnchorPane userListAnchorPane;

    @FXML
    private VBox pageVBox;

    @FXML
    private TextField searchField;
    private ViewChanger viewChanger;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewChanger = new ViewChanger();
        List<User> users = null;

        try {
            users = loadUsersFromDb();
        } catch (Exception e) {
            System.out.println("Cannot load users from DB!");
        }
        displayUsers(users);
    }

    @FXML
    public void onBackRedirectClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(userListAnchorPane, "account.fxml");
    }


    private List<User> loadUsersFromDb() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/users"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(response.body(), new TypeReference<List<User>>() {});

        return users;
    }

    private void displayUsers(List<User> users) {
        for (var user : users) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(LibraryApplication.class.getResource("user-card.fxml"));
                pageVBox.getChildren().add(fxmlLoader.load());
                UserCardController userCardController = fxmlLoader.getController();
                userCardController.setUserInfo(user);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void removeAllBooksFromScreen() {
        pageVBox.getChildren().clear();
    }
}
