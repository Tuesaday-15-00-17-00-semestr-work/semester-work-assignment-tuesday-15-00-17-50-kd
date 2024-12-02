module com.virtuallibrary.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens com.virtuallibrary.javafxapp to javafx.fxml;
    exports com.virtuallibrary.javafxapp;
    exports com.virtuallibrary.javafxapp.Controllers;
    exports com.virtuallibrary.javafxapp.Models;
    opens com.virtuallibrary.javafxapp.Controllers to javafx.fxml;
}