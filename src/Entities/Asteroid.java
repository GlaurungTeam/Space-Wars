package Entities;

import javafx.scene.canvas.Canvas;

import java.util.Random;

public class Asteroid extends Sprite {
    private boolean isHit;

    public Asteroid(double speed) {
        this.setSpeed(speed);
        this.setHitStatus(false);
    }

    public boolean getHitStatus() {
        return this.isHit;
    }

    public boolean setHitStatus(boolean isHit) {
        return this.isHit = isHit;
    }
}