package objectClasses;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

public class Ufo extends Sprite {
    public boolean isHit = false;
    private Double speed;

    public Double getSpeed() {
        return this.speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Ufo(Double speed){
        this.speed = speed;
    }

    public void updateUfoLocation(Canvas canvas) {
        double heightOffset = 37;

        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        this.positionX -= this.speed;

        if (this.positionX < -20) {
            this.positionX = canvas.getWidth();
            this.positionY = rnd.nextInt((int) offset);
            this.isHit = false;
        }
    }


}