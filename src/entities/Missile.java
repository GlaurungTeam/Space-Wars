package entities;

import enums.SpriteSheetParameters;

import java.awt.image.BufferedImage;

public class Missile extends GameObject {

    public Missile(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, String type) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.MISSILE.getWidth(),
                SpriteSheetParameters.MISSILE.getHeight(),
                SpriteSheetParameters.MISSILE.getRows(),
                SpriteSheetParameters.MISSILE.getCols(),
                type);
    }

    public void updateMissileLocation() {
        if (super.getType().equals("player")) {
            super.updateLocation(Math.max(0, super.getPositionX() +
                    super.getSpeed() * Constants.MISSILE_SPEED_MULTIPLIER), super.getPositionY());
        } else {
            super.updateLocation(Math.max(0, super.getPositionX() -
                    super.getSpeed() * Constants.MISSILE_SPEED_MULTIPLIER), super.getPositionY());
        }
    }
}