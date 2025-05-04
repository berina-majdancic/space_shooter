package io.github.berinamajdancic;

import java.io.IOException;

import io.github.berinamajdancic.controllers.GameController;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import static javafx.scene.layout.BackgroundPosition.CENTER;
import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.layout.BackgroundRepeat.REPEAT;
import static javafx.scene.layout.BackgroundSize.DEFAULT;
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
    private static final String BACKGROUND_PATH =
            "/io/github/berinamajdancic/images/stars_background.jpg";

    public Game(Stage stage) throws IOException {
        this.stage = stage;
        root = new StackPane();
        gameWorld = new Pane();
        hud = new Pane();
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
        Image backgroundImage = new Image(getClass().getResourceAsStream(BACKGROUND_PATH));
        root.setBackground(new Background(
                new BackgroundImage(backgroundImage, REPEAT, NO_REPEAT, CENTER, DEFAULT)));
        /*
         * backgroundImage = new Image(
         * getClass().getResourceAsStream("/io/github/berinamajdancic/images/starss.jpg"));
         * backgroundImageView = new ImageView(backgroundImage); if (backgroundImageView.getImage()
         * != null) { backgroundImageView.setFitWidth(1920); backgroundImageView.setFitHeight(1080);
         * //((StackPane) App.getGameRoot()).getChildren().add(backgroundImageView); }
         */
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
        stage.getScene().setRoot(root);
        SetupGameLoop();
        gameLoop.start();
    }

    public static Pane getHud() {
        return hud;
    }

    public static Pane getGameWorld() {
        return gameWorld;
    }

    public static StackPane getGameRoot() {
        return root;
    }

}
