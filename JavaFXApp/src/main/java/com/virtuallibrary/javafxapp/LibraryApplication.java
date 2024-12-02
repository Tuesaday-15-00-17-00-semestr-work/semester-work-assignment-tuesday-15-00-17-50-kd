package com.virtuallibrary.javafxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;

public class LibraryApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("log-in.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("VirtualLibrary");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        /*String serverUri = "http://localhost:8080";
        try {
            InetAddress inet = InetAddress.getByName(serverUri);
            if (inet.isReachable(5)) {
                System.exit(0);
            }
        } catch (RuntimeException e) {
            System.err.println("Server is not running");
            System.exit(0);
        }catch (Exception e) {
            System.err.println("Wrong server URI");
            System.exit(0);
        }*/

        launch();
    }
}