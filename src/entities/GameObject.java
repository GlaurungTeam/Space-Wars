package entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class GameObject extends Sprite {
    private String type;
    private boolean isHit;
    protected Image image;
    private Random rnd;

    protected GameObject(double positionX, double positionY, double objectSpeed,
                         BufferedImage spriteSheet, int width, int height, int rows, int cols, String type) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols);

        this.setHitStatus(false);
        this.rnd = new Random();
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void move(Canvas canvas, GameObject gameObject) {
        double heightOffset = 37;
        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        gameObject.updateLocation(gameObject.getPositionX() - gameObject.getSpeed(), gameObject.getPositionY());

        if (gameObject.getPositionX() < -20) {
            gameObject.updateLocation(canvas.getWidth(),this.rnd.nextInt((int) offset));
        }
    }

    public void resetLocation(double x, double y){//TODO Refactor/duplicated code
        super.updateLocation(x,y);
    }

    protected double generateRandomPosition(int offset){
        return this.rnd.nextInt(offset);
    }

    public boolean getHitStatus() {
        return this.isHit;
    }

    public void setHitStatus(boolean isHit) {
        this.isHit = isHit;
    }

    public Image getCurrentFrame(int index) {
        return super.getSprites().get(index);
    }
}
