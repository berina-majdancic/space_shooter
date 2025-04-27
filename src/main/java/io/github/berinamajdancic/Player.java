package io.github.berinamajdancic;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.InputStream;
import javafx.scene.image.Image;

public class Player {
    private double speed = 10.0;
    private static ImageView shipView;

    public Player() {
        try {
            shipView = GameController.getPlayerShip();
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }


    }

    public void update() {

    }

    public static ImageView getShipView() {
        return shipView;
    }
}
