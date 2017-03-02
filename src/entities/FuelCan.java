package entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

public class FuelCan extends Sprite {
    private boolean isTaken;

    public FuelCan(Canvas canvas, double speed) {
        String path = "resources/fuelcan/fuelCan.png";
        Image image = new Image(path);

        super.setImage(image);
        super.setPosition(SpawnCoordinates.getRandom((int) canvas.getWidth()), SpawnCoordinates.getSpawnY(canvas), this.getSpeed());
        super.setSpeed(speed);
    }

    public boolean getTakenStatus() {
        return this.isTaken;
    }

    public void setTakenStatus(boolean taken) {
        isTaken = taken;
    }

    public void updateFuelCanLocation(Canvas canvas) {
        //Offset so that asteroids don't spawn outside of boundaries
        double heightOffset = this.getHeight();

        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        this.setPositionX(this.getPositionX() - this.getSpeed());

        if (this.getPositionX() < -200) {
            this.setPositionX(rnd.nextInt((int) canvas.getWidth()));
            this.setPositionY(rnd.nextInt((int) offset));
            this.isTaken = false;
        }
    }
}