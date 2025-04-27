package io.github.berinamajdancic;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToGame() throws IOException {
        App.setRoot("game");
    }

    @FXML
    private void exitApplication() {
        Platform.exit(); // Closes the application
    }
}
