package io.github.berinamajdancic.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import io.github.berinamajdancic.App;
import io.github.berinamajdancic.DatabaseManager;
import io.github.berinamajdancic.entities.Enemy;
import io.github.berinamajdancic.entities.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import io.github.berinamajdancic.entities.Projectile;

public class GameController {
    private final Set<KeyCode> activeKeys = new HashSet<>();
    private final Player player;
    private Enemy enemy;
    private final double movementSpeed = 300;
    private final Stage stage;
    private static double deltaTime = 0;
    private double lastUpdateTime = 0;
    private DatabaseManager databaseManager;

    public GameController(Stage stage) throws IOException {
        databaseManager = new DatabaseManager();
        player = new Player();
        enemy = new Enemy();
        this.stage = stage;
        setupGame();
    }

    private void setupGame() {
        ((Group) App.getGameRoot()).getChildren().add(player.getShipView());
        ((Group) App.getGameRoot()).getChildren().add(enemy.getShipView());
        stage.show();
        App.getGameRoot().requestFocus();
        setupInputHandlers(stage.getScene());
    }

    public void update() {
        player.update();
        handleContinuousMovement();
        handleInstantActions();
        updateDeltaTime();
        checkCollisions();
    }

    public void setupInputHandlers(Scene scene) {
        scene.setOnKeyPressed(e -> {
            activeKeys.add(e.getCode());
        });
        scene.setOnKeyReleased(e -> activeKeys.remove(e.getCode()));
    }

    private void handleInstantActions() {
        if (activeKeys.contains(KeyCode.ESCAPE)) {
            try {
                App.showPauseMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (activeKeys.contains(KeyCode.SPACE)) {
            player.shoot();
        }
    }

    private void handleContinuousMovement() {
        double dx = 0, dy = 0;
        if (activeKeys.contains(KeyCode.A) || activeKeys.contains(KeyCode.LEFT))
            dx -= movementSpeed * deltaTime;
        if (activeKeys.contains(KeyCode.D) || activeKeys.contains(KeyCode.RIGHT))
            dx += movementSpeed * deltaTime;
        if (activeKeys.contains(KeyCode.W) || activeKeys.contains(KeyCode.UP))
            dy -= movementSpeed * deltaTime;
        if (activeKeys.contains(KeyCode.S) || activeKeys.contains(KeyCode.DOWN))
            dy += movementSpeed * deltaTime;

        player.move(dx, dy);

    }

    private void updateDeltaTime() {
        long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0;
        lastUpdateTime = currentTime;
    }

    public static double getDeltaTime() {
        return deltaTime;
    }

    private void checkCollisions() {

        for (Projectile projectile : player.getProjectiles()) {
            if (enemy != null) {
                if (projectile.getProjectileView().getBoundsInParent()
                        .intersects(enemy.getShipView().getBoundsInParent())) {
                    enemy.takeDamage(projectile.getDamage());
                    projectile.setOutOfBounds(true);
                    if (enemy.isDead()) {
                        enemy = null;
                        player.addScore(100);
                        databaseManager.saveScore("playe1", 100);
                    }
                }
            }
        }
    }

}