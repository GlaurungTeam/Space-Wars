package entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.Random;

public class FuelCan extends GameObject {
    private boolean isTaken;

    public FuelCan(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int width, int height, int rows, int cols) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols);
    }

    /*public FuelCan(Canvas canvas, double speed) {
        Image image = new Image(Constants.FUELCAN_IMAGE);

        super.setImage(image);
        super.setPosition(SpawnCoordinates.getRandom((int) canvas.getWidth()), SpawnCoordinates.getSpawnY(canvas));
        super.setSpeed(speed);
    }*/

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

        this.updateLocation(this.getPositionX() - this.getSpeed(), this.getPositionY());

        if (this.getPositionX() < -200) {
            int randomX = rnd.nextInt((int) canvas.getWidth());
            int randomY = rnd.nextInt((int) offset);
            this.setPosition(randomX, randomY);
            this.setTakenStatus(false);
        }
    }
}