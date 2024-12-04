package com.virtuallibrary.javafxapp.Controllers;

import com.virtuallibrary.javafxapp.LibraryApplication;
import com.virtuallibrary.javafxapp.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SampleController implements Initializable{

    @FXML
    private AnchorPane sampleAnchorPane;

    @FXML
    private Button accountRedirectButton;

    @FXML
    private VBox pageVBox;

    @FXML
    private FlowPane pageFlowPane;

    private ViewChanger viewChanger;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewChanger = new ViewChanger();
        new Session();

        try {
            loadAllBooks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onAccountRedirectClick(ActionEvent event) throws IOException {
        viewChanger.switchScenes(sampleAnchorPane, "account.fxml");
    }


    private void loadAllBooks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/books"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        List<Book> allBooks = mapper.readValue(response.body(), new TypeReference<List<Book>>() {});

        if (allBooks != null) {
            displayBooksOnScreen(allBooks);
        }
    }


    private void displayBooksOnScreen(List<Book> books) {
        for (var book : books) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(LibraryApplication.class.getResource("book-card.fxml"));
                pageVBox.getChildren().add(fxmlLoader.load());
                BookCardController bookCardController = fxmlLoader.getController();
                bookCardController.setBookCardInfo(book);
            } catch (IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void removeAllBooksFromScreen() {
        pageVBox.getChildren().clear();
    }
}