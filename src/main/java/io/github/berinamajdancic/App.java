package io.github.berinamajdancic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage primaryStage; // Store the primary stage
    private static Parent gameRoot;
    private static Game game;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        game = new Game(); // Initialize the game instance
        App.setRoot("main_menu"); // Set the initial root to main menu
        stage.setScene(game.getScene());
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); // Disable full-screen exit
        stage.setTitle("Space Shooter");
        // key
        game.start();
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Pane root = fxmlLoader.load();

        // Store the game root if switching to the game screen
        if (fxml.equals("game")) {
            gameRoot = root;
        }

        Game.setRoot(root); // Set the new root on the existing scene
    }

    public static Parent getGameRoot() {
        return gameRoot;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }

}
