package io.github.berinamajdancic.controllers;

import io.github.berinamajdancic.entities.Player;
import java.io.IOException;
import io.github.berinamajdancic.App;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;

public class GameController {
    private final Set<KeyCode> activeKeys= new HashSet<>();
        private Player player;
        private double movementSpeed=300;    private double spaceshipSpeed = 20.0;

    public GameController(Stage stage) throws IOException {
        player = new Player();
        App.getGameRoot().getChildren().add(player.getShipView());
        App.getGameRoot().setOnKeyPressed(this::handleKeyPress);
        App.getGameRoot().requestFocus();
        setupInputHandlers();
    }

    public void update() {
        player.update();
        handleContinuousMovement();

    }
    public void setupInputHandlers(Scene scene)
    {
        scene.setOnKeyPressed(e->{
            activeKeys.add(e.getCode();
            handleInstantActions(e.getCode());
        });
        scene.setOnKeyReleased(e->activeKeys.remove(e.getCode()));
    }

    private void handleInstantActions()
    {

    }
    private void handleContinuousMovement()
    {
        double dx=0, dy=0;
        if(activeKeys.contains(KeyCode.A)) dx-=movementSpeed;
        if(activeKeys.contains(KeyCode.D)) dx+=movementSpeed;
        if(activeKeys.contains(KeyCode.W)) dy-=movementSpeed;
        if(activeKeys.contains(KeyCode.S)) dy+=movementSpeed;
        if (dx != 0 && dy != 0) {
            double factor = movementSpeed / Math.sqrt(dx*dx + dy*dy);
            dx *= factor;
            dy *= factor;
        }
        
        player.move(dx * deltaTime, dy * deltaTime);

    }

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
