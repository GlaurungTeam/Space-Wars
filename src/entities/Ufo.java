package entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Ufo extends GameObject {

    public Ufo(Canvas canvas, double speed) {
        String path = "resources/UFO/ufo_" + String.valueOf(SpawnCoordinates.getRandom(6)) + ".png";
        Image image = new Image(path);

        super.setSpeed(speed);
        super.setImage(image);
        super.setPosition(SpawnCoordinates.getSpawnX(canvas),
                SpawnCoordinates.getSpawnY(canvas), this.getSpeed());
    }

}