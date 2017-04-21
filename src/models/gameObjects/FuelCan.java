package models.gameObjects;

import enums.SpriteSheetParameters;

import java.awt.image.BufferedImage;

public class FuelCan extends BaseGameObject implements TakeAble{
    private static final String TYPE = "fuelcan";

    private boolean isTaken;

    public FuelCan(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.FUEL_CAN.getWidth(),
                SpriteSheetParameters.FUEL_CAN.getHeight(),
                SpriteSheetParameters.FUEL_CAN.getRows(),
                SpriteSheetParameters.FUEL_CAN.getCols(),
                TYPE
        );
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