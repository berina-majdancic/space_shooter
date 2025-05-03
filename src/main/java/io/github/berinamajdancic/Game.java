package io.github.berinamajdancic;

import java.io.IOException;

import io.github.berinamajdancic.controllers.GameController;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game {
    private AnimationTimer gameLoop;
    GameController gameController;
    private static Stage stage;
    private static StackPane root;
    private static Pane gameWorld;
    private static Pane hud;
    private Image backgroundImage;
    private ImageView backgroundImageView;

    public Game(Stage stage) throws IOException {
        this.stage = stage;
        root = new StackPane();
        gameWorld = new Pane();
        hud = new Pane();
        stage.getScene().setRoot(root);
        root.getChildren().addAll(gameWorld, hud);
        gameController = new GameController(stage);
        setupGame();
    }

    private void SetupGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameController.update();
            }
        };
    }

    private void setupGame() {
        backgroundImage = new Image(getClass().getResourceAsStream("images/stars_background.jpg"));
        backgroundImageView = new ImageView(backgroundImage);
        if (backgroundImageView.getImage() != null) {
            backgroundImageView.setFitWidth(1920);
            backgroundImageView.setFitHeight(1080);
            ((StackPane) App.getGameRoot()).getChildren().add(backgroundImageView);
        }
    }

    public void showPauseMenu() {
        try {
            App.showPauseMenu();

        } catch (Exception e) {
        }
    }

    public void resumeGame() {
        stage.getScene().setRoot(root);
        start();
    }

    public void start() {
        SetupGameLoop();
        gameLoop.start();
    }

    public static Pane getHud() {
        return hud;
    }

    public static Pane getGameWorld() {
        return gameWorld;
    }

}
