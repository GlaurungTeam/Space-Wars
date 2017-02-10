package objectClasses;


import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

public class Ufo extends Sprite {
    public boolean isHit = false;

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

    public static void initializeUfos(Ufo[] ufos, Canvas canvas, double ufoSpeed) {
        for (int i = 0; i < ufos.length; i++) {
            Ufo ufo = new Ufo();
            String path = "resources/UFO/ufo_" + String.valueOf(SpawnCoordinates.getRandom(6)) + ".png";
            Image image = new Image(path);

            ufo.setImage(image);
            ufo.setPosition(SpawnCoordinates.getSpawnX(canvas), SpawnCoordinates.getSpawnY(canvas), ufoSpeed);
            ufos[i] = ufo;
        }
    }
}