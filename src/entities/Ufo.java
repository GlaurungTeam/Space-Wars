package entities;

import javafx.scene.canvas.Canvas;

public class Ufo extends GameObject {
    public Ufo(Canvas canvas, double speed) {
        super(canvas, speed, Constants.UFO_IMAGE +
                String.valueOf(SpawnCoordinates.getRandom(6)) + ".png");
    }
}