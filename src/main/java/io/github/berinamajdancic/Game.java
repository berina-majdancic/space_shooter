package io.github.berinamajdancic;

import java.io.IOException;

import io.github.berinamajdancic.controllers.GameController;
import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import static javafx.scene.layout.BackgroundPosition.CENTER;
import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.layout.BackgroundRepeat.REPEAT;
import static javafx.scene.layout.BackgroundSize.DEFAULT;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Game {
    private AnimationTimer gameLoop;
    private final GameController gameController;
    private final App app;
    private final Stage stage;
    private static StackPane root;
    private static Pane gameWorld;
    private static Pane hud;
    private Label scoreLabel;
    private ProgressBar healthBar;
    private static final String BACKGROUND_PATH = "/io/github/berinamajdancic/images/stars_background.jpg";

    public Game(Stage stage, App app, DatabaseManager databaseManager) throws IOException {
        this.stage = stage;
        this.app = app;
        root = new StackPane();
        gameWorld = new Pane();
        hud = new Pane();
        root.getChildren().addAll(gameWorld, hud);
        gameController = new GameController(stage, this, databaseManager);
        setupGame();
    }

    public Parent getGameRoot() {
        return root;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void restartGame() {
        try {
            app.restartGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        setupHUD();

    }

    public void showPauseMenu() {
        try {
            app.showPauseMenu();

        } catch (Exception e) {
        }
    }

    public void resumeGame() {
        gameController.setGamePaused(false);
        stage.getScene().setRoot(root);
        gameLoop.start();

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

    public void deleteGame() {
        gameLoop.stop();
        gameWorld.getChildren().clear();
        hud.getChildren().clear();
        root.getChildren().clear();
    }

    public void pauseGame() {

        gameLoop.stop();
        gameController.setGamePaused(true);
        showGameOverScreen();
    }

    private void setupHUD() {

        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-style: -fx-font-size: 20px; -fx-text-fill: white;");
        scoreLabel.setTranslateX(10);
        scoreLabel.setTranslateY(10);
        scoreLabel.setText("Score: " + 0);
        Game.getHud().getChildren().add(scoreLabel);

        healthBar = new ProgressBar();
        healthBar.setPrefWidth(200);
        healthBar.setStyle("-fx-accent: purple; -fx-border-style: none;");
        healthBar.setTranslateX(stage.getScene().getWidth() - 220);
        healthBar.setTranslateY(20);
        healthBar.setProgress(1.0);
        Game.getHud().getChildren().add(healthBar);

    }

    private void showGameOverScreen() {
        Rectangle overlay = new Rectangle();
        overlay.setWidth(stage.getScene().getWidth());
        overlay.setHeight(stage.getScene().getHeight());
        overlay.setFill(Color.rgb(0, 0, 0, 0.5));
        gameWorld.getChildren().add(overlay);

        Label gameOverLabel = new Label("Game Over");
        gameOverLabel.getStyleClass().add("game-over-label");
        gameOverLabel.setTranslateX(stage.getScene().getWidth() / 2 - 100);
        gameOverLabel.setTranslateY(stage.getScene().getHeight() / 2 - 50);
        hud.getChildren().add(gameOverLabel);

        Button restartButton = new Button("Restart");
        restartButton.getStyleClass().add("button");
        restartButton.setTranslateX(stage.getScene().getWidth() / 2 - 50);
        restartButton.setTranslateY(stage.getScene().getHeight() / 2 + 10);

        restartButton.setOnAction(e -> {
            System.err.println("Restarting game...");
            restartGame();
        });
        hud.getChildren().add(restartButton);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateHealthBar(double health, double maxHealth) {
        healthBar.setProgress(health / maxHealth);
    }
}