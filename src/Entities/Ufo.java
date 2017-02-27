package Entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

public class Ufo extends Sprite {
    private boolean isHit = false;

    public Ufo(Canvas canvas, double speed) {
        String path = "Resources/UFO/ufo_" + String.valueOf(SpawnCoordinates.getRandom(6)) + ".png";
        Image image = new Image(path);

        this.setSpeed(speed);
        this.setImage(image);
        this.setPosition(SpawnCoordinates.getSpawnX(canvas),
                SpawnCoordinates.getSpawnY(canvas), this.getSpeed());
    }

    public boolean getHitStatus() {
        return this.isHit;
    }

    public void setHitStatus(boolean isHit) {
        this.isHit = isHit;
    }
}