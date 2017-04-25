package models.enemies.genericEnemies;

import utils.Constants;
import models.gameObjects.BaseHealthableGameObject;
import enums.SpritesheetParameters;

import java.awt.image.BufferedImage;

public class Ufo extends BaseHealthableGameObject {

    private static final String TYPE = "ufo";

    public Ufo(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int health, int defaultHealth) {
        super(
                positionX,
                positionY,
                objectSpeed,
                spriteSheet,
                SpritesheetParameters.UFO.getWidth(),
                SpritesheetParameters.UFO.getHeight(),
                SpritesheetParameters.UFO.getRows(),
                SpritesheetParameters.UFO.getCols(),
                health,
                defaultHealth,
                Constants.UFO_POINTS_ON_KILL, TYPE
        );
        super.setImage(super.getRandomImageFromSpritesheet());
    }
}