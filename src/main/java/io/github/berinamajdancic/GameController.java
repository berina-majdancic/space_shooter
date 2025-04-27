package io.github.berinamajdancic;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class GameController {

    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.setOnKeyPressed(this::handleKeyPress);
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
