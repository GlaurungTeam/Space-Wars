package entities;

import enums.SpriteSheetParameters;

import java.awt.image.BufferedImage;

public class Missile extends GameObject {

    public Missile(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.MISSILE.getWidth(),
                SpriteSheetParameters.MISSILE.getHeight(),
                SpriteSheetParameters.MISSILE.getRows(),
                SpriteSheetParameters.MISSILE.getCols());
    }

    public void updateMissileLocation() {
        super.updateLocation(Math.max(0, super.getPositionX() + super.getSpeed() * Constants.MISSILE_SPEED_MULTIPLIER), super.getPositionY());
    }

    public void updateEnemyMissileLocation() {
        super.updateLocation(Math.max(0, super.getPositionX() - super.getSpeed() * Constants.MISSILE_SPEED_MULTIPLIER), super.getPositionY());
    }
}