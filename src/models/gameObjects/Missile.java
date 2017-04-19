package models.gameObjects;

import enums.SpriteSheetParameters;

import java.awt.image.BufferedImage;

public class Missile extends BaseGameObject {

    public Missile(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, String type) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.MISSILE.getWidth(),
                SpriteSheetParameters.MISSILE.getHeight(),
                SpriteSheetParameters.MISSILE.getRows(),
                SpriteSheetParameters.MISSILE.getCols(),
                type
        );
    }
}