package objectClasses;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

public class Ufo extends Sprite {
    private boolean isHit = false;

    public Ufo(Double speed) {
        this.setSpeed(speed);
    }

    public boolean getHitStatus() {
        return this.isHit;
    }

    public void setHitStatus(boolean isHit) {
        this.isHit = isHit;
    }

    public void updateUfoLocation(Canvas canvas) {
        double heightOffset = 37;

        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        this.setPositionX(this.getPositionX() - this.getSpeed());

        if (this.getPositionX() < -20) {
            this.setPositionX(canvas.getWidth());
            this.setPositionY(rnd.nextInt((int) offset));
            this.setHitStatus(false);
        }
    }
}