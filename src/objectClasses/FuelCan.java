package objectClasses;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

public class FuelCan extends Sprite {
    public boolean isTaken = false;

    public void initializeFuelCan(Canvas canvas) {
        String path = "resources/fuelcan/fuelCan.png";
        Image image = new Image(path);

        this.setImage(image);
        this.setPosition(SpawnCoordinates.getRandom((int) canvas.getWidth()), SpawnCoordinates.getSpawnY(canvas), this.speed);
    }

    public void updateFuelCanLocation(Canvas canvas) {
        //Offset so that asteroids don't spawn outside of boundaries
        double heightOffset = this.height;

        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        this.positionX -= this.speed;

        if (this.positionX < -200) {
            this.positionX = rnd.nextInt((int) canvas.getWidth());
            this.positionY = rnd.nextInt((int) offset);
            this.isTaken = false;
        }
    }
}