package io.github.berinamajdancic.entities;

import io.github.berinamajdancic.Game;
import io.github.berinamajdancic.controllers.GameController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Projectile {
    private final double speedX, speedY;
    private double x, y;
    private boolean outOfBounds;
    private final int damage;
    private static Image enemyBulletImage;
    private static Image playerBulletImage;
    private ImageView bulletImageView;

    public Projectile(double x, double y, int damage, double speedX, double speedY,
            boolean isEnemyBullet) {
        if (isEnemyBullet) {
            if (enemyBulletImage == null) {
                enemyBulletImage = new Image(getClass()
                        .getResourceAsStream("/io/github/berinamajdancic/images/flare.png"));
            }
            if (enemyBulletImage != null) {
                bulletImageView = new ImageView(enemyBulletImage);
            }
        } else {
            if (playerBulletImage == null) {
                playerBulletImage = new Image(getClass()
                        .getResourceAsStream("/io/github/berinamajdancic/images/laser.png"));
            }
            if (playerBulletImage != null) {
                bulletImageView = new ImageView(playerBulletImage);
            }
        }
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.speedX = speedX;
        this.speedY = speedY;
        setupView();
    }

    public int getDamage() {
        return damage;
    }

    public void setOutOfBounds(boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
    }

    public void calculatePosition() {
        y -= speedY * GameController.getDeltaTime();
        x += speedX * GameController.getDeltaTime();
        Scene scene = bulletImageView.getScene();
        if (scene != null) {
            if (y < 0 || y > (scene.getHeight()))
                outOfBounds = true;
            if (x < 0 || x > (scene.getWidth()))
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

    public void updatePosition() {
        if (bulletImageView.getScene() != null) {
            bulletImageView.setTranslateX(x);
            bulletImageView.setTranslateY(y);
        }
    }
}
