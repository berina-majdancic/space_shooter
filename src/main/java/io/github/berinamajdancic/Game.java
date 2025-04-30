package io.github.berinamajdancic;

import java.io.IOException;

import io.github.berinamajdancic.controllers.GameController;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Game {
    private AnimationTimer gameLoop;
    Parent root;
    GameController gameController;
    private Image backgroundImage;
    private ImageView backgroundImageView;

    public Game(Stage stage) throws IOException {
        Parent root = new Group();
        stage.getScene().setRoot(root);
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
       backgroundImage=new Image(getClass().getResourceAsStream("images/stars_background.jpg"));
         backgroundImageView=new ImageView(backgroundImage);
         if(backgroundImageView.getImage() != null) {
                backgroundImageView.setFitWidth(1920);
                backgroundImageView.setFitHeight(1080);
             ((Group)App.getGameRoot()).getChildren().add(backgroundImageView);
         }
    }
    public void start() {
        SetupGameLoop();
        gameLoop.start();
    }

}
