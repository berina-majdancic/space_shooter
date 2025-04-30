package io.github.berinamajdancic.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import io.github.berinamajdancic.App;
import io.github.berinamajdancic.entities.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GameController {
    private final Set<KeyCode> activeKeys= new HashSet<>();
        private Player player;
        private double movementSpeed=300;    private double spaceshipSpeed = 20.0;
        private double deltaTime=0, currentTime=0, lastUpdateTime=0;

    public GameController(Stage stage) throws IOException {
        player = new Player();
        ((Group) App.getGameRoot()).getChildren().add(player.getShipView());
        stage.show();
        App.getGameRoot().requestFocus();
        setupInputHandlers(stage.getScene());
    }

    public void update() {
        player.update();
        handleContinuousMovement();
        updateDeltaTime();

    }
    public void setupInputHandlers(Scene scene)
    {
        scene.setOnKeyPressed(e->{
            activeKeys.add(e.getCode());
        });
        scene.setOnKeyReleased(e->activeKeys.remove(e.getCode()));
    }

    private void handleInstantActions()
    {

    }
    private void handleContinuousMovement()
    {
        double dx=0, dy=0;
        if(activeKeys.contains(KeyCode.A)) dx-=movementSpeed*deltaTime;
        if(activeKeys.contains(KeyCode.D)) dx+=movementSpeed*deltaTime;
        if(activeKeys.contains(KeyCode.W)) dy-=movementSpeed* deltaTime;
        if(activeKeys.contains(KeyCode.S)) dy+=movementSpeed*deltaTime;
        
        player.move(dx, dy );

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
    private void updateDeltaTime() {
        long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0;
        lastUpdateTime = currentTime;
    }
}
