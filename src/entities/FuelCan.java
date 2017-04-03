package entities;

import enums.SpriteSheetParameters;
import javafx.scene.canvas.Canvas;

import java.awt.image.BufferedImage;
import java.util.Random;

public class FuelCan extends GameObject {
    private static final String TYPE = "fuelcan";

    private boolean isTaken;

    public FuelCan(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.FUEL_CAN.getWidth(),
                SpriteSheetParameters.FUEL_CAN.getHeight(),
                SpriteSheetParameters.FUEL_CAN.getRows(),
                SpriteSheetParameters.FUEL_CAN.getCols(),
                TYPE);
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

        if (this.getPositionX() < Constants.FUELCAN_RESTART_LEFT_COORDINATE) {
            int randomX = rnd.nextInt((int) canvas.getWidth());
            int randomY = rnd.nextInt((int) offset);
            this.setPosition(randomX, randomY);
            this.setTakenStatus(false);
        }
    }
}