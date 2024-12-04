package com.virtuallibrary.javafxapp.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuallibrary.javafxapp.LibraryApplication;
import com.virtuallibrary.javafxapp.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    private AnchorPane accountAnchorPane;

    @FXML
    private Button logOutRedirectButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button backRedirectButton;

    @FXML
    private Button addBookRedirectButton;

    @FXML
    private Button userListRedirectButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label noBooksFoundLabel;

    @FXML
    private VBox yourBooksVBox;

    @FXML
    private Label infoLabel;

    @FXML
    private Label yourBooksLabel;

    @FXML
    private ScrollPane yourBooksScrollPane;

    private ViewChanger viewChanger;

    private MessageLabel messageLabel;

    private User user;

    public AccountController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabel = new MessageLabel();
        viewChanger = new ViewChanger();
        new Session();
        user = Session.getUser();
        hideScrollBar();
        disableAdminPanel();
        showUserInfo();
        showAdminPanelIfAdmin();
        try {
            loadRentedBooks();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onBackRedirectClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(accountAnchorPane, "sample.fxml");
    }

    @FXML
    private void onLogOutRedirectClick(ActionEvent event) throws IOException {
        Session.logout();
        viewChanger.switchScenes(accountAnchorPane, "log-in.fxml");
    }

    @FXML
    private void onAddBookRedirectClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(accountAnchorPane, "add-book.fxml");
    }

    @FXML
    private void onReturnAllBooksClick(ActionEvent event) throws IOException {
        /*Result<String> bookResult = bookService.returnAllByOwner(user.getId());
        if (!bookResult.getSuccess()) {
            messageLabel.showUnsuccessfulMessage(infoLabel, bookResult.getMessage());
        } else {
            viewChanger.switchScenes(accountAnchorPane, "account.fxml");
        }*/
    }

    @FXML
    private void addUserClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(accountAnchorPane, "add-user.fxml");
    }

    @FXML
    private void onRefreshClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(accountAnchorPane, "account.fxml");
    }

    @FXML
    private void onUserListRedirectClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(accountAnchorPane, "user-list.fxml");
    }

    private void showUserInfo() {
        User user = Session.getUser();

        nameLabel.setText(user.getUsername());
    }

    private void disableAdminPanel() {
        addUserButton.setVisible(false);
        addUserButton.setDisable(true);
        addBookRedirectButton.setVisible(false);
        addBookRedirectButton.setDisable(true);
        userListRedirectButton.setVisible(false);
        userListRedirectButton.setDisable(true);
    }

    private void showAdminPanelIfAdmin() {
        if (user.getRoleId() == 2) {
            addUserButton.setVisible(true);
            addUserButton.setDisable(false);
            addBookRedirectButton.setVisible(true);
            addBookRedirectButton.setDisable(false);
            userListRedirectButton.setVisible(true);
            userListRedirectButton.setDisable(false);
        }
    }

    private void loadRentedBooks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/transactions/user/" + Session.getUser().getUserId()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 404) {
            noBooksFoundLabel.setText("It looks like you haven't rented any books yet.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Transaction> rentedBooksTransactions  = mapper.readValue(response.body(), new TypeReference<List<Transaction>>() {});
        List<Book> rentedBooks = new ArrayList<>();
        for (Transaction transaction : rentedBooksTransactions) {
            if (transaction.getAction().equals("borrowed")) {
                request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/books/" + transaction.getBookId()))
                        .GET()
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 404) {
                    System.out.println("Cannot find the book | Account controller");
                    break;
                }
                Book book = mapper.readValue(response.body(), new TypeReference<>() {});
                rentedBooks.add(book);
            }
        }

        showRentedBooks(rentedBooks);
    }

    private void hideScrollBar() {
        yourBooksScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        yourBooksScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void showRentedBooks(List<Book> rentedBooks) {
        for (var book : rentedBooks) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(LibraryApplication.class.getResource("rented-book-card.fxml"));
                yourBooksVBox.getChildren().add(fxmlLoader.load());
                RentedBookCardController rentedBookCardController = fxmlLoader.getController();
                rentedBookCardController.setRentedBookCardInfo(book);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}