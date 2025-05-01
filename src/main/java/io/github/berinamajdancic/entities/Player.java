package io.github.berinamajdancic.entities;

import java.io.InputStream;
import java.util.LinkedList;

import io.github.berinamajdancic.App;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private final double width = 100, height = 100;
    private final double fireRate = 100_000_000;
    private final double speed = 3.0;
    private Image shipImage;
    private ImageView shipView;
    private double x = 100, y = 100;
    private long lastShotTime = 0;
    private int score = 0;

    LinkedList<Projectile> projectiles;

    public Player() {
        projectiles = new LinkedList<>();
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
        InputStream inputStream = getClass().getResourceAsStream("/io/github/berinamajdancic/images/spaceship.png");
        shipImage = new Image(inputStream);
        shipView = new ImageView(shipImage);
        shipView.setFitWidth(width);
        shipView.setFitHeight(height);
        shipView.setTranslateX(x);
        shipView.setTranslateY(y);
    }

    public void move(double dx, double dy) {
        if (x + dx < 0 || x + width + dx > ((Group) App.getGameRoot()).getScene().getWidth()) {
            dx = 0;
        }
        if (y + dy < 0 && y + height + dy > ((Group) App.getGameRoot()).getScene().getHeight()) {
            dy = 0;
        }
        x += dx * speed;
        y += dy * speed;
        shipView.setTranslateX(x);
        shipView.setTranslateY(y);

    }

    public void shoot() {
        long currentTime = System.nanoTime();
        if (currentTime - lastShotTime >= fireRate) {
            Projectile projectile = new Projectile(x + width / 2, y, 30);
            projectiles.add(projectile);
            lastShotTime = currentTime;
        }
    }

    private void updateProjectiles() {
        if (!projectiles.isEmpty()) {
            projectiles.removeIf(projectile -> {
                if (projectile.outOfBounds()) {
                    ((Group) App.getGameRoot()).getChildren().remove(projectile.getProjectileView());
                    return true;
                }
                return false;
            });
            for (Projectile projectile : projectiles) {
                projectile.update();
            }
        }

    }

    public LinkedList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

}
