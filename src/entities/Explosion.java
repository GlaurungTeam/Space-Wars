package entities;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explosion extends Sprite {
    private int currentFrameIndex;

    public Explosion(double x, double y) {
        this.setCurrentFrameIndex(0);
        super.setPositionX(x);
        super.setPositionY(y);
        super.setSpeed(Constants.EXPLOSION_SPEED);

        BufferedImage explosionSpriteSheet = null;

        try {
            explosionSpriteSheet = ImageIO.read(new File(
                    Constants.PROJECT_PATH + Constants.EXPLOSION_SPRITESHEET_IMAGE));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.setSpriteParameters(48, 49, 1, 25);
        this.setSpriteSheet(explosionSpriteSheet);
        this.splitSprites();
    }

    public int getCurrentFrameIndex() {
        return this.currentFrameIndex;
    }

    public void setCurrentFrameIndex(int currentFrameIndex) {
        this.currentFrameIndex = currentFrameIndex;
    }

    public Image getCurrentExplosionFrame(int index) {
        return super.getSprites()[index];
    }
}