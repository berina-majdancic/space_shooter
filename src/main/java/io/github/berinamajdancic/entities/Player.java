package io.github.berinamajdancic.entities;

import java.io.InputStream;
import java.util.LinkedList;

import io.github.berinamajdancic.App;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;

public class Player {
    private double speed = 10.0;
    private Image shipImage;
    private ImageView shipView;
    private double x = 100, y = 100;
    private final double width = 100, height = 100;
    LinkedList<Projectile> projectiles;

    public Player() {
        projectiles = new LinkedList<Projectile>();
        setupImageView();
    }

    public void update() {
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
        x += dx;
        y += dy;
        shipView.setTranslateX(x);
        shipView.setTranslateY(y);

    }

    public void shoot() {
        Projectile projectile = new Projectile(x + width / 2, y);
        projectiles.add(projectile);
    }
}
