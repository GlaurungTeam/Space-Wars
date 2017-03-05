package entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Ufo extends GameObject {
    private static final String UFO_SPRITESHEET_PATH =
            "resources/UFO/ufo_" + String.valueOf(SpawnCoordinates.getRandom(6)) + ".png";

    public Ufo(Canvas canvas, double speed) {
        super(canvas, speed, UFO_SPRITESHEET_PATH);
    }
}