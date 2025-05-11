package io.github.berinamajdancic.entities;

import java.util.ArrayList;

import io.github.berinamajdancic.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {

    private final double speed = 1;
    private static Image shipImage;
    private ImageView shipView;
    private int health = 120;
    private double x = 600, y = 100;
    private final double width = 100, height = 100;
    private final double fireRate = 2_000_000_000;
    private final double moveRate = 1_000_000;
    private long lastShotTime = 0;
    private boolean isOutOfBounds = false;
    private boolean isDead = false;
    private long lastMoveTime = 0;
    private final ArrayList<Projectile> projectiles;
    private final double shipCenter = width / 2 - 7;
    private double screenWidth = 1920.0 - width;

    public Enemy() {
        randomizePosition();
        setupImageView();
        projectiles = new ArrayList<>();
        Game.getGameWorld().getChildren().add(shipView);
    }

    private void randomizePosition() {
        x = Math.random() * screenWidth;
        y = Math.random() * -300;

    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isOutOfBounds() {
        return isOutOfBounds;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            isDead = true;
        }
    }

    public void removeEnemy() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            if (getShipView().getScene() != null)
                Game.getGameWorld().getChildren().remove(projectile.getProjectileView());
        }
        if (getShipView().getScene() != null)
            Game.getGameWorld().getChildren().remove(shipView);
    }

    public void update() {
        calculatePosition();
        calculateProjectilePosition();
    }

    public ImageView getShipView() {
        return shipView;
    }

    private void setupImageView() {
        if (shipImage == null)
            shipImage = new Image(getClass()
                    .getResourceAsStream("/io/github/berinamajdancic/images/enemy_ship.png"));
        if (shipImage != null) {
            shipView = new ImageView(shipImage);
            shipView.setFitWidth(width);
            shipView.setFitHeight(height);
            shipView.setTranslateX(x);
            shipView.setTranslateY(y);

        }
    }

    private void calculatePosition() {
        long currentTime = System.nanoTime();
        if (currentTime - lastMoveTime >= moveRate) {
            y = y + speed;
            if (shipView.getScene() != null) {
                if (x < 0 || x > shipView.getScene().getWidth()
                        || y > shipView.getScene().getHeight()) {
                    isOutOfBounds = true;
                }
            }
            lastMoveTime = currentTime;
        }
    }

    public void updatePosition() {
        shipView.setTranslateY(y);
    }

    public void shoot() {
        long currentTime = System.nanoTime();
        if ((currentTime - lastShotTime) >= fireRate * 3) {
            Projectile projectile2 =
                    new Projectile(x + shipCenter + 8, y + height, 30, 150, -150, true);
            Projectile projectile3 =
                    new Projectile(x + shipCenter - 8, y + height, 30, -150, -150, true);

            projectiles.add(projectile2);
            projectiles.add(projectile3);
            lastShotTime = currentTime;
        }
    }

    public void updateProjectilePosition() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            if (projectile.outOfBounds()) {
                Game.getGameWorld().getChildren().remove(projectile.getProjectileView());
                projectiles.remove(i);
                System.err.println("ProjectileRemoved");
                i--;
            }
            projectile.updatePosition();
        }
    }

    private void calculateProjectilePosition() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            projectile.calculatePosition();
        }
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
