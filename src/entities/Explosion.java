package entities;

import enums.SpriteSheetParameters;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explosion extends GameObject {

    public Explosion(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.EXPLOSION.getWidth(),
                SpriteSheetParameters.EXPLOSION.getHeight(),
                SpriteSheetParameters.EXPLOSION.getRows(),
                SpriteSheetParameters.EXPLOSION.getCols());
    }

}