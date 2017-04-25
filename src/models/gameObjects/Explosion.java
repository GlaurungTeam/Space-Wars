package models.gameObjects;

import enums.SpritesheetParameters;

import java.awt.image.BufferedImage;

public class Explosion extends BaseGameObject {

    private static final String TYPE = "explosion";

    public Explosion(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet) {
        super(
                positionX,
                positionY,
                objectSpeed,
                spriteSheet,
                SpritesheetParameters.EXPLOSION.getWidth(),
                SpritesheetParameters.EXPLOSION.getHeight(),
                SpritesheetParameters.EXPLOSION.getRows(),
                SpritesheetParameters.EXPLOSION.getCols(),
                TYPE
        );
    }
}