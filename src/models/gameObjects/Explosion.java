package models.gameObjects;

import enums.SpriteSheetParameters;

import java.awt.image.BufferedImage;

public class Explosion extends BaseGameObject {

    private static final String TYPE = "explosion";

    public Explosion(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.EXPLOSION.getWidth(),
                SpriteSheetParameters.EXPLOSION.getHeight(),
                SpriteSheetParameters.EXPLOSION.getRows(),
                SpriteSheetParameters.EXPLOSION.getCols(),
                TYPE
        );
    }
}