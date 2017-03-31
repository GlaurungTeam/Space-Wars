package entities;

import enums.SpriteSheetParameters;
import java.awt.image.BufferedImage;

public class Explosion extends GameObject {
    private static final String type = "explosion";

    public Explosion(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.EXPLOSION.getWidth(),
                SpriteSheetParameters.EXPLOSION.getHeight(),
                SpriteSheetParameters.EXPLOSION.getRows(),
                SpriteSheetParameters.EXPLOSION.getCols(),
                type);
    }

}