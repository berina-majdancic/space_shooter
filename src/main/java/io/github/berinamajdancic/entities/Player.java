package io.github.berinamajdancic.entities;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private double speed = 10.0;
    private Image shipImage;
    private ImageView shipView;
    private double x = 100, y = 100;
    private final double width = 100, height = 100;

    public Player() {
        setupImageView();
    }

    public void update() {
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
    }

    public void move(double dx, double dy) {

        x += dx;
        y += dy;
        shipView.setTranslateX(x);
        shipView.setTranslateY(y);

    }
}
