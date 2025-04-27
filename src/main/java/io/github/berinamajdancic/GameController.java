package io.github.berinamajdancic;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class GameController {

    private Scene scene;
    private double spaceshipSpeed = 20.0;
    @FXML
    private ImageView spaceship;
    private static ImageView spaceshipView;


    @FXML
    public void initialize() {
        try {
            Image spaceshipImage =
                    new Image(getClass().getResourceAsStream("images/spaceship.png"));

            spaceship.setImage(spaceshipImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.setOnKeyPressed(this::handleKeyPress);

        scene.getRoot().requestFocus();
    }

    public static ImageView getPlayerShip() {
        return spaceshipView;
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case ESCAPE:
                try {
                    if (scene.getRoot() == App.getGameRoot()) {
                        App.setRoot("main_menu");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
