package io.github.berinamajdancic;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.InputStream;
import javafx.scene.image.Image;

public class Player extends ImageView {
    private double speed = 10.0;

    public Player() {
        try {
            InputStream is = getClass().getResourceAsStream("images/spaceship.png");
            if (is != null) {
                setImage(new Image(is));
            } else {
                System.err.println("Could not load image: /images/spaceship1.png");
                // Fallback to a rectangle
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }
        setX(300);
        setY(300);
    }

    public void update() {

    }
}
