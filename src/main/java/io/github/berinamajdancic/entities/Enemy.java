package io.github.berinamajdancic.entities;

import java.util.LinkedList;

import io.github.berinamajdancic.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Enemy {

    private double speed = 1;
    private Image shipImage;
    private ImageView shipView;
    private int health = 100;
    private double x = 400, y = 400;
    private final double width = 100, height = 100;
    private final double fireRate = 100_000_000;
    private final double moveRate = 10_000_000;
    private long lastShotTime = 0;
    private boolean isOutOfBounds = false;
    private long lastMoveTime = 0;
    LinkedList<Projectile> projectiles;

    public Enemy() {
        setupImageView();
        projectiles = new LinkedList<>();
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
            if (((Pane) Game.getGameWorld()).getScene() != null)
                ((Pane) Game.getGameWorld()).getChildren().remove(shipView);

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
        shipImage = new Image(getClass().getResourceAsStream("/io/github/berinamajdancic/images/spaceship.png"));
        shipView = new ImageView(shipImage);
        shipView.setFitWidth(width);
        shipView.setFitHeight(height);
        shipView.setTranslateX(x);
        shipView.setTranslateY(y);
    }

    private void move() {
        long currentTime = System.nanoTime();
        if (currentTime - lastMoveTime >= moveRate) {
            y = y + speed;
            System.err.println("Moving");
            shipView.setTranslateY(y);
            if (((Pane) Game.getGameWorld()).getScene() != null) {
                if (y > ((Pane) Game.getGameWorld()).getScene().getHeight()) {
                    isOutOfBounds = true;
                }
            }
            lastMoveTime = currentTime;
        }
    }

    public void shoot() {
        long currentTime = System.nanoTime();
        if (currentTime - lastShotTime >= fireRate) {
            Projectile projectile = new Projectile(x + width / 2, y, 30, -800);
            projectiles.add(projectile);
            lastShotTime = currentTime;
        }
    }

    private void updateProjectiles() {
        if (!projectiles.isEmpty()) {
            projectiles.removeIf(projectile -> {
                projectile.update();
                if (projectile.outOfBounds()) {
                    if (((Pane) Game.getGameWorld()).getScene() != null)
                        ((Pane) Game.getGameWorld()).getChildren().remove(projectile.getProjectileView());
                    System.out.println("Removing projectile");
                    return true;
                }
                return false;
            });

        }

    }

}
