package io.github.berinamajdancic.controllers;

import java.io.IOException;
import io.github.berinamajdancic.App;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void switchToGame() throws IOException {
        App.startGame();
    }

    @FXML
    private void exitApplication() {
        Platform.exit();
    }
}
