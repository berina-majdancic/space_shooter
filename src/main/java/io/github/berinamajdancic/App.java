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

    private static Stage primaryStage;
    private static Parent gameRoot;
    private static Game game;

    @Override
    public void start(Stage stage) throws IOException {
        GameController gameController = new GameController();
        primaryStage = stage;

        game = new Game();
        App.setRoot("main_menu");
        stage.setScene(game.getScene());
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setTitle("Space Shooter");
        // key
        gameController.setScene(game.getScene());
        game.start();
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Pane root = fxmlLoader.load();
        if (fxml.equals("game")) {
            gameRoot = root;
        }
        Game.setRoot(root);
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
