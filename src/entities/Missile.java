package entities;

import java.awt.image.BufferedImage;

public class Missile extends Sprite {
    public Missile(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int width, int height, int rows, int cols) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols);
    }

    public void updateMissileLocation() {
        super.updateLocation(Math.max(0, super.getPositionX() + super.getSpeed() * Constants.MISSILE_SPEED_MULTIPLIER), super.getPositionY());
    }

    public void updateEnemyMissileLocation() {
        super.updateLocation(Math.max(0, super.getPositionX() - super.getSpeed() * Constants.MISSILE_SPEED_MULTIPLIER), super.getPositionY());
    }
}