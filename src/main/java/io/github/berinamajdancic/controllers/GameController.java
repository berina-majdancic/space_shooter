package io.github.berinamajdancic.controllers;

import io.github.berinamajdancic.entities.Player;
import java.io.IOException;
import io.github.berinamajdancic.App;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;

public class GameController {

    Player player;

    public GameController(Stage stage) throws IOException {
        player = new Player();
        App.getGameRoot().getChildren().add(player.getShipView());
        App.getGameRoot().setOnKeyPressed(this::handleKeyPress);
        App.getGameRoot().requestFocus();
    }

    public void update() {
        player.update();
    }

    private double spaceshipSpeed = 20.0;

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case ESCAPE:
                try {

                    App.setRoot("ui/main_menu");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                event.consume();
                break;
            default:
                break;
        }
    }
}
