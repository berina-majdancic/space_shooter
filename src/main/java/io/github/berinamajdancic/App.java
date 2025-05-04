package io.github.berinamajdancic;

import java.io.IOException;

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

    private static Stage primaryStage;
    private static Scene scene;
    private static Pane gameRoot;
    private static Game game;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        setupStage();
        gameRoot = new Pane();
        scene = new Scene(gameRoot);
        primaryStage.setScene(scene);
        showMainMenu();
        primaryStage.show();
    }

    public static void showMainMenu() throws IOException {
        setRoot("ui/main_menu");
    }

    public static void showLoginPage() throws IOException {
        setRoot("ui/login");
    }

    public static void showRegisterPage() throws IOException {
        setRoot("ui/register");
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        gameRoot = fxmlLoader.load();
        primaryStage.getScene().setRoot(gameRoot);
    }

    public static Parent getGameRoot() {
        return primaryStage.getScene().getRoot();
    }

    private void setupStage() {
        primaryStage.setTitle("Space Shooter");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public static void startGame() throws IOException {

        game = new Game(primaryStage);
        game.start();
    }

    public static void showPauseMenu() throws IOException {
        try {
            App.setRoot("ui/pause_menu");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resumeGame() throws IOException {
        game.resumeGame();
    }

    public static void main(String[] args) {
        launch();
    }

}
