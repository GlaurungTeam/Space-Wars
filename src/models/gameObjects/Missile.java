package models.gameObjects;

import enums.SpritesheetParameters;

import java.awt.image.BufferedImage;

public class Missile extends BaseGameObject {

    public Missile(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, String type) {
        super(
                positionX,
                positionY,
                objectSpeed,
                spriteSheet,
                SpritesheetParameters.MISSILE.getWidth(),
                SpritesheetParameters.MISSILE.getHeight(),
                SpritesheetParameters.MISSILE.getRows(),
                SpritesheetParameters.MISSILE.getCols(),
                type
        );
    }
}