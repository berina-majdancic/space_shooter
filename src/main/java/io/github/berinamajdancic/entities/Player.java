package io.github.berinamajdancic.entities;

import java.io.InputStream;
import java.util.ArrayList;

import io.github.berinamajdancic.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private final double width = 150, height = 150;
    private final double fireRate = 100_000_000;
    private final double speed = 3.0;
    private Image shipImage;
    private ImageView shipView;
    private double x = 160, y = 128;
    private long lastShotTime = 0;
    private int score = 0;
    private int health = 100;
    private int maxHealth = 100;

    ArrayList<Projectile> projectiles;

    public Player() {
        projectiles = new ArrayList<>();
        setupImageView();
    }

    public void update() {
        updateProjectiles();
    }

    public ImageView getShipView() {
        System.out.println("SHipView Exists");
        return shipView;
    }

    private void setupImageView() {
        InputStream inputStream = getClass().getResourceAsStream("/io/github/berinamajdancic/images/ship.png");
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
        shipView.setTranslateX(x);
        shipView.setTranslateY(y);
    }

    public void shoot() {
        long currentTime = System.nanoTime();
        if (currentTime - lastShotTime >= fireRate) {
            Projectile projectile = new Projectile(x + width / 2 - 7, y, 30, 0, 800);
            projectiles.add(projectile);
            lastShotTime = currentTime;
        }
    }

    private void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            if (projectile.outOfBounds()) {
                Game.getGameWorld().getChildren().remove(projectile.getProjectileView());
                projectiles.remove(i);
                i--;
            }
            projectile.update();

        }

    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void addScore(int score) {
        this.score += score;
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
