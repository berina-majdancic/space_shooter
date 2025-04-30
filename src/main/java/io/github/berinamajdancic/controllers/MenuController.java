package io.github.berinamajdancic.controllers;

import java.io.IOException;

import io.github.berinamajdancic.App;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void resumeGame() throws IOException {
        App.resumeGame();
    }

    @FXML

    private void startGame() throws IOException {
        App.startGame();
    }

    @FXML
    private void exitApplication() {
        Platform.exit();
    }
}
