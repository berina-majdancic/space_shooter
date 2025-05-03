package io.github.berinamajdancic.entities;

import io.github.berinamajdancic.Game;
import io.github.berinamajdancic.controllers.GameController;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Projectile {
    private final double speed;
    private double x, y;
    private final double radius = 10;
    private boolean outOfBounds;
    private final int damage;
    Circle bullet;

    public Projectile(double x, double y, int damage, double speed) {
        bullet = new Circle(radius / 2);
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
        bullet.setTranslateY(y);
        if (((Pane) Game.getGameWorld()).getScene() != null)
            if (y < 0 || y > ((Pane) Game.getGameWorld()).getScene().getHeight())
                outOfBounds = true;
    }

    private void setupView() {
        bullet.setTranslateX(x);
        bullet.setTranslateY(y);
        ((Pane) Game.getGameWorld()).getChildren().add(bullet);
    }

    public Circle getProjectileView() {
        return bullet;
    }

    public boolean outOfBounds() {
        return outOfBounds;
    }
}
