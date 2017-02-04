package objectClasses;

import javafx.scene.canvas.Canvas;

import java.util.Random;

public class Asteroid extends Sprite {

    public void updateAsteroidLocation(Canvas canvas) {

        //Offset so that asteroids don't spawn outside of boundaries
        double heightOffset = 37;

        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        this.positionX -= this.speed;

        if (this.positionX < -20) {
            this.positionX = canvas.getWidth();
            this.positionY = rnd.nextInt((int) offset);
        }
    }
}