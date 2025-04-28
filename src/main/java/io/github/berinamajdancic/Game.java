package io.github.berinamajdancic;

import io.github.berinamajdancic.controllers.GameController;

import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class Game {
    private AnimationTimer gameLoop;
    GameController gameController;

    public Game(Stage stage) throws IOException {
        App.getGameRoot().getChildren().clear();
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
