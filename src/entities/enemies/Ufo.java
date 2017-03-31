package entities.enemies;
import entities.HealthAbleGameObject;
import enums.SpriteSheetParameters;

import java.awt.image.BufferedImage;

public class Ufo extends HealthAbleGameObject {
    private static final String type = "ufo";

    public Ufo(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int health, int defaultHealth) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.UFO.getWidth(),
                SpriteSheetParameters.UFO.getHeight(),
                SpriteSheetParameters.UFO.getRows(),
                SpriteSheetParameters.UFO.getCols(),
                health,
                defaultHealth,
                type);
    }
}