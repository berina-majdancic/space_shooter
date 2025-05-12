package io.github.berinamajdancic.controllers;

import java.io.IOException;

import io.github.berinamajdancic.App;
import io.github.berinamajdancic.db.DatabaseManager;
import io.github.berinamajdancic.db.LeaderboardEntry;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MenuController {
    private static DatabaseManager databaseManager;
    private static App app;

    @FXML
    private TableView<LeaderboardEntry> leaderboardTable = new TableView<>();
    @FXML
    private TableColumn<LeaderboardEntry, Integer> rankColumn;
    @FXML
    private TableColumn<LeaderboardEntry, String> usernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> scoreColumn;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public static void setDataBaseManager(DatabaseManager databaseManager) {
        MenuController.databaseManager = databaseManager;
    }

    public static void setApp(App app) {
        MenuController.app = app;
    }

    @FXML
    private void resumeGame() throws IOException {
        app.resumeGame();
    }

    @FXML
    private void startGame() throws IOException {
        app.startGame();
    }

    @FXML
    private void restartGame() throws IOException {
        app.restartGame();
    }

    @FXML
    private void exitApplication() {
        Platform.exit();
    }

    @FXML
    private void showRegisterPage() throws IOException {
        app.showRegisterPage();
    }

    @FXML
    private void showLoginPage() throws IOException {
        app.showLoginPage();
    }

    @FXML
    private void showMainMenu() throws IOException {
        app.showMainMenu();
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
            databaseManager.Register(username, password);
            app.showMainMenu();

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
            databaseManager.login(username, password);
            app.showMainMenu();

        }
    }

    @FXML
    private void handleCancel() throws IOException {
        app.showMainMenu();
    }

    @FXML
    private void showLeaderboard() throws IOException {
        app.showLeaderboard();
    }

    @FXML
    public void initialize() {

        //rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        // usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        // scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        if (databaseManager != null)
            leaderboardTable.setItems(databaseManager.getLeaderboardData());

    }
}
