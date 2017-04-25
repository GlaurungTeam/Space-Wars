package models.enemies.genericEnemies;

import utils.Constants;
import models.gameObjects.BaseHealthableGameObject;
import enums.SpritesheetParameters;

import java.awt.image.BufferedImage;

public class Asteroid extends BaseHealthableGameObject {

    private static final String TYPE = "asteroid";

    public Asteroid(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int health, int defaultHealth) {
        super(
                positionX,
                positionY,
                objectSpeed,
                spriteSheet,
                SpritesheetParameters.ASTEROID.getWidth(),
                SpritesheetParameters.ASTEROID.getHeight(),
                SpritesheetParameters.ASTEROID.getRows(),
                SpritesheetParameters.ASTEROID.getCols(),
                health,
                defaultHealth,
                Constants.ASTEROID_POINTS_ON_KILL, TYPE
        );
        super.setImage(super.getRandomImageFromSpritesheet());
    }
}