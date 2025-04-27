package io.github.berinamajdancic;

import javafx.scene.image.Image;
import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Game {
    private Player player;
    private static Scene scene;
    private static Pane gameRoot;
    private AnimationTimer gameLoop;

    public Game() {
        player = new Player();
        gameRoot = new Pane();
        scene = new Scene(gameRoot, 640, 480);
        gameRoot.getChildren().add(player);
    }

    public static void setRoot(Pane newRoot) {
        gameRoot = newRoot;
        scene.setRoot(newRoot); // Update the Scene's root
        // setupInputHandling(); // Reattach input handlers to new scene
    }

    private void SetupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update game state and render
                player.update();
            }
        };
    }

    public void start() {
        SetupGameLoop();
        gameLoop.start();
    }

    public Scene getScene() {
        return scene;
    }
}
