package models.enemies;

import utils.Constants;
import models.gameObjects.BaseHealthableGameObject;
import enums.SpriteSheetParameters;

import java.awt.image.BufferedImage;

public class Ufo extends BaseHealthableGameObject {

    private static final String TYPE = "ufo";

    public Ufo(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int health, int defaultHealth) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.UFO.getWidth(),
                SpriteSheetParameters.UFO.getHeight(),
                SpriteSheetParameters.UFO.getRows(),
                SpriteSheetParameters.UFO.getCols(),
                health, defaultHealth, Constants.UFO_POINTS_ON_KILL, TYPE);
    }
}