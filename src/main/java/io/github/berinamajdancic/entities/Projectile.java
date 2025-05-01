package io.github.berinamajdancic.entities;

import io.github.berinamajdancic.App;
import io.github.berinamajdancic.controllers.GameController;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class Projectile {
    private final double speed = 800.0;
    private double x, y;
    private final double radius = 10;
    private boolean outOfBounds;
    private final int damage;
    Circle bullet;

    public Projectile(double x, double y, int damage) {
        bullet = new Circle(radius / 2);
        this.x = x;
        this.y = y;
        this.damage = damage;
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
        if (y < 0 || y > ((Group) App.getGameRoot()).getScene().getHeight())
            outOfBounds = true;
    }

    private void setupView() {
        bullet.setTranslateX(x);
        bullet.setTranslateY(y);
        ((Group) App.getGameRoot()).getChildren().add(bullet);
    }

    public Circle getProjectileView() {
        return bullet;
    }

    public boolean outOfBounds() {
        return outOfBounds;
    }
}
