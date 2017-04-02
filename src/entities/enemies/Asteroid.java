package entities.enemies;

import entities.HealthAbleGameObject;
import enums.SpriteSheetParameters;
import java.awt.image.BufferedImage;

public class Asteroid extends HealthAbleGameObject {
    private static final String TYPE = "asteroid";

    public Asteroid(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int health, int defaultHealth) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.ASTEROID.getWidth(),
                SpriteSheetParameters.ASTEROID.getHeight(),
                SpriteSheetParameters.ASTEROID.getRows(),
                SpriteSheetParameters.ASTEROID.getCols(),
                health, defaultHealth, TYPE);
    }

}