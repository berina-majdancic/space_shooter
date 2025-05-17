package io.github.berinamajdancic.entities;

import java.io.InputStream;
import java.util.ArrayList;

import io.github.berinamajdancic.Game;
import io.github.berinamajdancic.SoundManager;
import io.github.berinamajdancic.controllers.GameController;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private Image shipImage;
    private ImageView shipView;
    private double fireRate = 200_000_000;
    private double x, y;
    private long lastShotTime = 0;
    private int score = 0;
    private int powerUpTreshold = 1000;
    private int health;
    private final double width = 150, height = 150;
    private final double speed = 2.0;
    private final int maxHealth = 1000;
    private final GameController gameController;
    private boolean isDead = false;
    private ArrayList<Projectile> projectiles;
    private SoundManager soundManager;

    public Player(GameController gameController, double x, double y, SoundManager soundManager) {
        this.gameController = gameController;
        this.x = x;
        this.y = y;
        this.soundManager = soundManager;
        projectiles = new ArrayList<>();
        setupImageView();
        startPlayerThread();
        health = maxHealth;
    }

    public ImageView getShipView() {
        return shipView;
    }

    public boolean isDead() {
        return isDead;
    }

    private void setupImageView() {
        InputStream inputStream =
                getClass().getResourceAsStream("/io/github/berinamajdancic/images/ship.png");
        shipImage = new Image(inputStream);
        shipView = new ImageView(shipImage);
        shipView.setFitWidth(width);
        shipView.setFitHeight(height);
        shipView.setTranslateX(x);
        shipView.setTranslateY(y);
    }

    public void move(double dx, double dy) {
        if (shipView.getScene() != null) {
            double newX = x + dx * speed;
            double newY = y + dy * speed;
            if (newX > 0 && newX + width < shipView.getScene().getWidth()) {
                x = newX;
            }
            if (newY > 0 && newY + height < shipView.getScene().getHeight()) {
                y = newY;
            }
        }
    }

    public void shoot() {
        long currentTime = System.nanoTime();
        if (currentTime - lastShotTime >= fireRate) {
            Projectile projectile = new Projectile(x + width / 2 - 7, y, 30, 0, 800, false);
            projectiles.add(projectile);
            lastShotTime = currentTime;
        }
    }

    private void calculateProjectilePosition() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);

            projectile.calculatePosition();

        }
    }

    private void startPlayerThread() {
        Thread playerThread = new Thread(() -> {
            while (!isDead && !Thread.currentThread().isInterrupted()) {
                if (!gameController.isGamePaused()) {
                    gameController.handleContinuousMovement();
                    calculateProjectilePosition();
                    Platform.runLater(() -> {
                        shoot();
                        updatePosition();
                        for (int i = 0; i < projectiles.size(); i++) {
                            Projectile projectile = projectiles.get(i);
                            projectile.updatePosition();
                            if (projectile.outOfBounds()) {
                                Game.getGameWorld().getChildren()
                                        .remove(projectile.getProjectileView());
                                projectiles.remove(i);
                                i--;
                            }
                        }
                    });
                }
                try {
                    Thread.sleep(10); // 60 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        playerThread.setDaemon(true);
        playerThread.start();

    }

    private void updatePosition() {
        shipView.setTranslateX(x);
        shipView.setTranslateY(y);
    }

    public void takeDamage(double damage) {
        health -= damage;
        if (health <= 0) {
            isDead = true;
        }
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void addScore(int score) {
        this.score += score;
        soundManager.playKillSound();
        health = Math.min(maxHealth, health + 25);
        if (this.score % powerUpTreshold == 0) {
            fireRate = Math.max(10_000_000, fireRate - 20_000_000);
            powerUpTreshold = Math.min(powerUpTreshold * 2, 4000);
            soundManager.playAchievementSound();
        }
        gameController.updateScore(this.score);
    }

    public int getScore() {
        return score;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
