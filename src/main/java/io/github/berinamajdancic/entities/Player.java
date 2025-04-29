package io.github.berinamajdancic.entities;

import javafx.scene.image.ImageView;
import java.io.InputStream;
import javafx.scene.image.Image;

public class Player {
    private double speed = 10.0;
    private Image shipImage;
    private ImageView shipView;
    privat double x=0,y=0;

    public Player() {
        setupImageView();
    }

    public void update() {

    }

    public ImageView getShipView() {
        return shipView;
    }

    private void setupImageView() {
        InputStream inputStream =
                getClass().getResourceAsStream("/io/github/berinamajdancic/images/spaceship.png");
        shipImage = new Image(inputStream);
        shipView = new ImageView(shipImage);
        shipView.setFitWidth(50);
        shipView.setFitHeight(50);
        shipView.setX(100);
        shipView.setY(100);
    }
    public void move(double dx,double dy)
    {
        x+=dx;
        y+=dy;
        shipView.setLayoutX(x);
        shipView.setLayoutY(y);

    }
}
