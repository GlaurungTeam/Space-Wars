package models.gameObjects;

import contracts.Takeаble;
import enums.SpritesheetParameters;

import java.awt.image.BufferedImage;

public class FuelCan extends BaseGameObject implements Takeаble {

    private static final String TYPE = "fuelcan";

    private boolean isTaken;

    public FuelCan(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet) {
        super(
                positionX,
                positionY,
                objectSpeed,
                spriteSheet,
                SpritesheetParameters.FUEL_CAN.getWidth(),
                SpritesheetParameters.FUEL_CAN.getHeight(),
                SpritesheetParameters.FUEL_CAN.getRows(),
                SpritesheetParameters.FUEL_CAN.getCols(),
                TYPE
        );
        super.setImage(super.getRandomImageFromSpritesheet());
    }

    @Override
    public boolean getTakenStatus() {
        return this.isTaken;
    }

    @Override
    public void setTakenStatus(boolean isTaken) {
        this.isTaken = isTaken;
    }
}