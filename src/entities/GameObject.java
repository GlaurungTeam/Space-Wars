package entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class GameObject extends Sprite {
    private boolean isHit;
    protected Image image;
    //protected Canvas canvas;
    //protected String imagePath;
    private Random rnd;

    protected GameObject(double positionX, double positionY, double objectSpeed,
                         BufferedImage spriteSheet, int width, int height, int rows, int cols) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols);

        this.setHitStatus(false);
        //TODO create setters
        this.rnd = new Random();

        //super.setPosition(SpawnCoordinates.getSpawnX(this.canvas),
         //       SpawnCoordinates.getSpawnY(this.canvas));
    }

    /*private void move(Canvas canvas) {
        double heightOffset = 37;
        double offset = canvas.getHeight() - heightOffset;

        this.move(this.getPositionX() - this.getSpeed(), this.getPositionY());

        if (this.getPositionX() < -20) {
            this.move(canvas.getWidth(), super.generateRandomPosition((int) offset));
            this.setHitStatus(false);
        }
    }*///TODO maybe?



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
