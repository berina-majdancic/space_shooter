package io.github.berinamajdancic.entities;

import io.github.berinamajdancic.App;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;

public class Enemy {

    private double speed = 10.0;
    private Image shipImage;
    private ImageView shipView;
    private int health = 100;
    private double x = 400, y = 400;
    private final double width = 100, height = 100;

    public Enemy() {
        setupImageView();
    }

    public boolean isDead() {
        return health < 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            ((Group) App.getGameRoot()).getChildren().remove(shipView);

        }
    }

    public void update() {
        // Update enemy position or behavior here
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
}
