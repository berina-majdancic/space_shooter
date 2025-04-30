package io.github.berinamajdancic;

import java.io.IOException;

import io.github.berinamajdancic.controllers.GameController;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Game {
    private AnimationTimer gameLoop;
    Parent root;
    GameController gameController;

    public Game(Stage stage) throws IOException {
        Parent root = new Group();
        stage.getScene().setRoot(root);
        gameController = new GameController(stage);
    }

    private void SetupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameController.update();
            }
        };
    }

    public void start() {
        SetupGameLoop();
        gameLoop.start();
    }

}
