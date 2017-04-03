package entities;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public abstract class GameObject extends Sprite {
    private String type;
    protected Image image;

    protected GameObject(double positionX, double positionY, double objectSpeed,
                         BufferedImage spriteSheet, int width, int height, int rows, int cols, String type) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols);
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public Image getCurrentFrame(int index) {
        return super.getSprites().get(index);
    }
}