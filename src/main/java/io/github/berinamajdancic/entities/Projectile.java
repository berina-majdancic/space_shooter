package io.github.berinamajdancic.entities;

import java.io.InputStream;

import io.github.berinamajdancic.Game;
import io.github.berinamajdancic.controllers.GameController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Projectile {
    private final double speed;
    private double x, y;
    private boolean outOfBounds;
    private final int damage;
    Image bulletImage;
    private ImageView bulletImageView;

    public Projectile(double x, double y, int damage, double speed) {
        InputStream inputStream = getClass().getResourceAsStream("/io/github/berinamajdancic/images/flare.png");
        if (inputStream != null) {
            bulletImage = new Image(inputStream);
            if (bulletImage != null) {
                bulletImageView = new ImageView(bulletImage);
            }
        }
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.speed = speed;
        setupView();
    }

    public int getDamage() {
        return damage;
    }

    public void setOutOfBounds(boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
    }

    public void update() {
        y -= speed * GameController.getDeltaTime();
        bulletImageView.setTranslateY(y);
        Scene scene = ((Pane) Game.getGameWorld()).getScene();
        if (scene != null) {
            if (y < 0 || y > (scene.getHeight()))
                outOfBounds = true;
        }
    }

    private void setupView() {
        if (bulletImageView != null) {
            bulletImageView.setFitHeight(15);
            bulletImageView.setFitWidth(15);
            bulletImageView.setTranslateX(x);
            bulletImageView.setTranslateY(y);
            ((Pane) Game.getGameWorld()).getChildren().add(bulletImageView);
        }
    }

    public ImageView getProjectileView() {
        return bulletImageView;
    }

    public boolean outOfBounds() {
        return outOfBounds;
    }
}
