package io.github.berinamajdancic.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import io.github.berinamajdancic.App;
import io.github.berinamajdancic.DatabaseManager;
import io.github.berinamajdancic.Game;
import io.github.berinamajdancic.entities.Enemy;
import io.github.berinamajdancic.entities.Player;
import io.github.berinamajdancic.entities.Projectile;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class GameController {
    private final Set<KeyCode> activeKeys = new HashSet<>();
    private final Player player;
    private ArrayList<Enemy> enemies;
    private final double movementSpeed = 300;
    private final Stage stage;
    private static double deltaTime = 0;
    private double lastUpdateTime = 0;
    private DatabaseManager databaseManager;
    private static Label scoreLabel;
    private static ProgressBar healthBar;

    public GameController(Stage stage) throws IOException {
        databaseManager = new DatabaseManager();
        player = new Player();
        enemies = new ArrayList<>();
        enemies.add(new Enemy());
        enemies.add(new Enemy());
        enemies.add(new Enemy());
        this.stage = stage;
        setupGame();
    }

    private void setupGame() {
        Game.getGameWorld().getChildren().add(player.getShipView());
        setupHUD();
        stage.show();
        App.getGameRoot().requestFocus();
        setupInputHandlers(stage.getScene());
    }

    public void update() {
        updateDeltaTime();
        updateEnemies();
        handleContinuousMovement();
        handleInstantActions();

    }

    private void updateEnemies() {

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDead()) {
                player.addScore(100);
                updateScore(player.getScore());
            }
            if (enemy.isOutOfBounds() || enemy.isDead()) {
                enemy.removeEnemy();
                Game.getGameWorld().getChildren().remove(enemy.getShipView());
                enemies.remove(i);
                i--;
                enemies.add(new Enemy());
            }
        }

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

    private void handleCollisions() {

        for (int i = 0; i < player.getProjectiles().size(); i++) {
            Projectile projectile = player.getProjectiles().get(i);
            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                if (projectile.getProjectileView().getBoundsInParent()
                        .intersects(enemy.getShipView().getBoundsInParent())) {
                    enemy.takeDamage(projectile.getDamage());
                    projectile.setOutOfBounds(true);
                }
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            for (int j = 0; j < enemy.getProjectiles().size(); j++) {
                Projectile projectile = enemy.getProjectiles().get(j);
                if (projectile.getProjectileView().getBoundsInParent()
                        .intersects(player.getShipView().getBoundsInParent())) {
                    player.takeDamage(projectile.getDamage());
                    projectile.setOutOfBounds(true);
                    if (enemy.isDead() || enemy.isOutOfBounds()) {
                        enemies.remove(i);
                        i--;
                    }
                }
            }
        }

    }

    private void setupHUD() {
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        scoreLabel.setTranslateX(10);
        scoreLabel.setTranslateY(10);
        scoreLabel.setText("Score: " + player.getScore());

        Game.getHud().getChildren().add(scoreLabel);

        Scene scene = App.getGameRoot().getScene();

        healthBar = new ProgressBar();
        healthBar.setPrefWidth(200);
        healthBar.setStyle("-fx-accent: purple; -fx-border-style: none;");
        if (scene != null)
            healthBar.setTranslateX(scene.getWidth() - 220);
        healthBar.setTranslateY(20);
        healthBar.setProgress(player.getHealth() / player.getMaxHealth());
        Game.getHud().getChildren().add(healthBar);

    }

    public static void updateHealth(double health, double maxHealth) {
        healthBar.setProgress(health / maxHealth);
    }

    public static void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    private void killEnemy() {
        // enemy = null;
        player.addScore(100);
        updateScore(player.getScore());
    }

    public void startCOllisionThread() {
        Thread thread = new Thread(() -> {
            while (!enemies.isEmpty() && !Thread.currentThread().isInterrupted()) {
                handleCollisions();
                try {
                    Thread.sleep(10); // Adjust the sleep time as needed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}
