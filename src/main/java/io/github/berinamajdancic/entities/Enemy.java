package io.github.berinamajdancic.entities;

import java.util.ArrayList;

import io.github.berinamajdancic.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {

    private double speed = 1;
    private static Image shipImage;
    private ImageView shipView;
    private int health = 100;
    private double x = 600, y = 100;
    private final double width = 100, height = 100;
    private final double fireRate = 1_000_000_000;
    private final double moveRate = 10_000_000;
    private long lastShotTime = 0;
    private boolean isOutOfBounds = false;
    private long lastMoveTime = 0;
    private ArrayList<Projectile> projectiles;
    private final double shipCenter = width / 2 - 7;

    public Enemy() {
        randomizePosition();
        setupImageView();
        projectiles = new ArrayList<>();
    }

    private void randomizePosition() {
        x = Math.random() * 1920;
        y = Math.random() * -300;

    }

    public boolean isDead() {
        return health < 0;
    }

    public boolean isOutOfBounds() {
        return isOutOfBounds;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile projectile = projectiles.get(i);
                if (getShipView().getScene() != null)
                    Game.getGameWorld().getChildren()
                            .remove(projectile.getProjectileView());
            }
            if (getShipView().getScene() != null)
                Game.getGameWorld().getChildren().remove(shipView);

        }
    }

    public void update() {
        move();
        shoot();
        updateProjectiles();
    }

    public ImageView getShipView() {
        return shipView;
    }

    private void setupImageView() {
        if (shipImage == null)
            shipImage = new Image(
                    getClass().getResourceAsStream("/io/github/berinamajdancic/images/enemy_ship.png"));
        if (shipImage != null) {
            shipView = new ImageView(shipImage);
            shipView.setFitWidth(width);
            shipView.setFitHeight(height);
            shipView.setTranslateX(x);
            shipView.setTranslateY(y);

        }
    }

    private void move() {
        long currentTime = System.nanoTime();
        if (currentTime - lastMoveTime >= moveRate) {
            y = y + speed;
            shipView.setTranslateY(y);
            if (shipView.getScene() != null) {
                if (x < 0 || x > shipView.getScene().getWidth()
                        || y > shipView.getScene().getHeight()) {
                    isOutOfBounds = true;
                }
            }
            lastMoveTime = currentTime;
        }
    }

    public void shoot() {
        long currentTime = System.nanoTime();
        if (currentTime - lastShotTime >= fireRate) {
            Projectile projectile = new Projectile(x + shipCenter, y + height, 30, 0, -800);
            Projectile projectile2 = new Projectile(x + shipCenter + 8, y + height, 30, 700, -800);
            Projectile projectile3 = new Projectile(x + shipCenter - 8, y + height, 30, -700, -800);

            projectiles.add(projectile);
            projectiles.add(projectile2);
            projectiles.add(projectile3);
            lastShotTime = currentTime;
        }
    }

    private void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            if (projectile.outOfBounds()) {
                Game.getGameWorld().getChildren()
                        .remove(projectile.getProjectileView());
                projectiles.remove(i);
                i--;
            }
            projectile.update();

        }

    }

}
