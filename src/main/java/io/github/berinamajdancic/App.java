package io.github.berinamajdancic;

import java.io.IOException;

import io.github.berinamajdancic.controllers.MenuController;
import io.github.berinamajdancic.db.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private Stage primaryStage;
    private Scene scene;
    private Pane gameRoot;
    private Game game;
    private DatabaseManager databaseManager;
    private SoundManager soundManager;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        databaseManager = new DatabaseManager();
        soundManager = new SoundManager();
        setupStage();
        gameRoot = new Pane();
        scene = new Scene(gameRoot);
        primaryStage.setScene(scene);
        showMainMenu();
        primaryStage.show();
        MenuController.setApp(this);
        MenuController.setDataBaseManager(databaseManager);
        scene.getStylesheets().add(
                getClass().getResource("/io/github/berinamajdancic/ui/menu.css").toExternalForm());
    }

    public void showMainMenu() throws IOException {
        setRoot("ui/main_menu");
    }

    public void showLoginPage() throws IOException {
        setRoot("ui/login");
    }

    public void showRegisterPage() throws IOException {
        setRoot("ui/register");
    }

    public void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        gameRoot = fxmlLoader.load();
        primaryStage.getScene().setRoot(gameRoot);
    }

    public Parent getGameRoot() {
        return primaryStage.getScene().getRoot();
    }

    private void setupStage() {
        primaryStage.setTitle("Space Shooter");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public void startGame() throws IOException {
        game = new Game(primaryStage, this, databaseManager, soundManager);
        game.start();
        soundManager.playLevelUpSound();
    }

    public void showPauseMenu() throws IOException {
        try {
            setRoot("ui/pause_menu");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLeaderboard() throws IOException {
        try {
            setRoot("ui/leaderboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restartGame() throws IOException {
        game.deleteGame();
        game = null;
        startGame();
    }

    public void resumeGame() throws IOException {
        game.resumeGame();
    }

    public static void main(String[] args) {
        launch();
    }

}
