package entities;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explosion extends Sprite {
    private int currentFrameIndex;

    public Explosion(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int width, int height, int rows, int cols) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols);
    }

    public int getCurrentFrameIndex() {
        return this.currentFrameIndex;
    }

    public void setCurrentFrameIndex(int currentFrameIndex) {
        this.currentFrameIndex = currentFrameIndex;
    }

    public Image getCurrentExplosionFrame(int index) {
        return super.getSprites().get(index);
    }
}