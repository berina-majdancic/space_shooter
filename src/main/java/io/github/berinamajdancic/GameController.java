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
    private double spaceshipSpeed = 20.0; // Speed of the spaceship
    @FXML
    private ImageView spaceship;

    @FXML
    public void initialize() {
        System.out.println("GameController initialized.");
        System.out.println("Spaceship ImageView: " + spaceship);
        try {
            Image spaceshipImage =
                    new Image(getClass().getResourceAsStream("images/spaceship.png"));
            spaceship.setImage(spaceshipImage);
            // spaceship.setLayoutX((scene.getWidth() - spaceship.getFitWidth()) / 2);
            // spaceship.setLayoutY((scene.getHeight() - spaceship.getFitHeight()) / 20);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.setOnKeyPressed(this::handleKeyPress);

        this.scene.getRoot().requestFocus();
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
            /*
             * case LEFT: case A: if (spaceship.getLayoutX() > 0) {
             * spaceship.setLayoutX(spaceship.getLayoutX() - spaceshipSpeed);
             * System.out.println("Moving left");
             * 
             * } break;
             * 
             * case RIGHT: case D: if (spaceship.getLayoutX() < scene.getWidth() -
             * spaceship.getFitWidth()) { spaceship.setLayoutX(spaceship.getLayoutX() +
             * spaceshipSpeed); } break; case UP: // Move up case W: if (spaceship.getLayoutY() > 0)
             * { // Prevent moving out of bounds spaceship.setLayoutY(spaceship.getLayoutY() -
             * spaceshipSpeed); } break;
             * 
             * case DOWN: // Move down case S: if (spaceship.getLayoutY() < scene.getHeight() -
             * spaceship.getFitHeight()) { spaceship.setLayoutY(spaceship.getLayoutY() +
             * spaceshipSpeed); } break;
             */
            default:
                break;
        }
    }
}
