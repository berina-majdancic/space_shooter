package io.github.berinamajdancic.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import io.github.berinamajdancic.Game;
import io.github.berinamajdancic.SoundManager;
import io.github.berinamajdancic.db.DatabaseManager;
import io.github.berinamajdancic.entities.Enemy;
import io.github.berinamajdancic.entities.Player;
import io.github.berinamajdancic.entities.Projectile;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class GameController {
    private final Game game;
    private final Player player;
    private final Stage stage;
    private final DatabaseManager databaseManager;
    private final Set<KeyCode> activeKeys = new HashSet<>();
    private final SoundManager soundManager;
    private final ArrayList<Enemy> enemies;
    private final double movementSpeed = 300;
    private static double deltaTime = 0;
    private double lastUpdateTime = 0;
    private boolean isGamePaused = false;
    private boolean gameIsRunning = true;

    public GameController(Stage stage, Game game, DatabaseManager databaseManager,
            SoundManager soundManager) throws IOException {
        this.game = game;
        this.databaseManager = databaseManager;
        this.soundManager = soundManager;
        this.stage = stage;
        player = new Player(this, stage.getScene().getWidth() / 2,
                stage.getScene().getHeight() - 200, soundManager);
        enemies = new ArrayList<>();
        enemies.add(spawnEnemy());
        enemies.add(spawnEnemy());
        enemies.add(spawnEnemy());
        setupGame();
        startEnemyBehavior();
        startCollisionThread();
    }

    public boolean isGamePaused() {
        return isGamePaused;
    }

    public void setIsGameRunning(boolean isGameRunning) {
        gameIsRunning = isGameRunning;
    }

    public void setGamePaused(boolean isGamePaused) {
        this.isGamePaused = isGamePaused;
    }

    private void setupGame() {
        Game.getGameWorld().getChildren().add(player.getShipView());
        stage.show();
        game.getGameRoot().requestFocus();
        setupInputHandlers(stage.getScene());
    }

    public void update() {
        if (!isGamePaused) {
            updateDeltaTime();
            updateEnemies();
            handleInstantActions();
        }
    }

    public void gameOver() {
        game.pauseGame();
        saveScore();
    }

    private void updateEnemies() {

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDead()) {
                player.addScore(100);
                if (player.getScore() % 1000 == 0 && player.getScore() < 20000) {
                    enemies.add(spawnEnemy());
                }
                enemy.removeEnemy();
                Game.getGameWorld().getChildren().remove(enemy.getShipView());
                enemies.remove(i);
                i--;
                enemies.add(spawnEnemy());
                updateHealth(player.getHealth(), player.getMaxHealth());
            } else if (enemy.isOutOfBounds()) {
                player.takeDamage(200);
                updateHealth(player.getHealth(), player.getMaxHealth());
                enemy.removeEnemy();
                Game.getGameWorld().getChildren().remove(enemy.getShipView());
                enemies.remove(i);
                i--;
                enemies.add(spawnEnemy());
                soundManager.playLoseLifeSound();
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
            isGamePaused = true;
            game.pauseGame();
            game.showPauseMenu();
        }
    }

    public void handleContinuousMovement() {
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
        deltaTime = Math.min(deltaTime, 0.1);
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
                    Platform.runLater(() -> {
                        updateHealth(player.getHealth(), player.getMaxHealth());
                    });
                    projectile.setOutOfBounds(true);

                }
            }
        }

    }

    private void startCollisionThread() {
        Thread thread = new Thread(() -> {
            while (!enemies.isEmpty() && !Thread.currentThread().isInterrupted()) {
                if (!isGamePaused)
                    handleCollisions();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void startEnemyBehavior() {
        Thread thread = new Thread(() -> {
            while (gameIsRunning && !Thread.currentThread().isInterrupted()) {
                if (!isGamePaused) {
                    for (int i = 0; i < enemies.size(); i++) {
                        Enemy enemy = enemies.get(i);
                        enemy.update();
                        Platform.runLater(() -> {
                            enemy.shoot();
                            enemy.updatePosition();
                            enemy.updateProjectilePosition();
                        });
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private Enemy spawnEnemy() {
        double x = Math.random() * (stage.getScene().getWidth() - 100);
        double y = Math.random() * -300;
        return new Enemy(x, y);
    }

    public void updateHealth(double health, double maxHealth) {
        game.updateHealthBar(health, maxHealth);
        if (player.isDead()) {
            isGamePaused = true;
            soundManager.playDeathSound();
            saveScore();
            game.showGameOverScreen();
        }
    }

    public void updateScore(int score) {
        game.updateScore(score);
    }

    public void saveScore() {
        databaseManager.saveScore(player.getScore());
    }
}
