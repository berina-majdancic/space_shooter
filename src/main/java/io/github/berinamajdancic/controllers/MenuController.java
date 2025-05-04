package io.github.berinamajdancic.controllers;

import java.io.IOException;

import io.github.berinamajdancic.App;
import io.github.berinamajdancic.DatabaseManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MenuController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void resumeGame() throws IOException {
        App.resumeGame();
    }

    @FXML

    private void startGame() throws IOException {
        App.startGame();
    }

    @FXML
    private void exitApplication() {
        Platform.exit();
    }

    @FXML
    private void showRegisterPage() throws IOException {
        App.showRegisterPage();
    }

    @FXML
    private void showLoginPage() throws IOException {
        App.showLoginPage();
    }

    @FXML
    private void showMainMenu() throws IOException {
        App.showMainMenu();
    }

    @FXML
    private void handleRegister() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");

            alert.setHeaderText(null);
            alert.setContentText("Username or password cannot be empty.");
            alert.showAndWait();
        } else {
            DatabaseManager.Register(username, password);
            App.showMainMenu();

        }
    }

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");

            alert.setHeaderText(null);
            alert.setContentText("Username or password cannot be empty.");
            alert.showAndWait();
        } else {
            DatabaseManager.login(username, password);
            App.showMainMenu();

        }
    }

    @FXML
    private void handleCancel() throws IOException {
        App.showMainMenu();
    }
}
