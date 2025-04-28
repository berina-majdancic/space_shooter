package io.github.berinamajdancic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
        showMainMenu();
    }

    private void showMainMenu() throws IOException {
        setRoot("ui/main_menu");
        setupStage(primaryStage);
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        gameRoot = fxmlLoader.load();
        scene = new Scene(gameRoot);
        primaryStage.setScene(scene);
    }

    public static Pane getGameRoot() {
        return gameRoot;
    }

    private void setupStage(Stage stage) {
        stage.setTitle("Space Shooter");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public static void startGame() throws IOException {
        game = new Game(primaryStage);
        game.start();
    }

    public static void main(String[] args) {
        launch();
    }

}
