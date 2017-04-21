package models.enemies.bosses;

import contracts.Boss;
import utils.Constants;
import models.gameObjects.BaseHealthableGameObject;
import javafx.scene.shape.SVGPath;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Random;

public abstract class BaseBoss extends BaseHealthableGameObject implements Boss {
    private static final String TYPE = "boss";

    private SVGPath svgPath;
    private boolean visible;
    private double randomY;
    private Random random;

    protected BaseBoss(double positionX, double positionY, double objectSpeed,
                       BufferedImage spriteSheet, int width, int height,
                       int rows, int cols, int health) {

        super(positionX, positionY, objectSpeed, spriteSheet, width, height,
                rows, cols, health, health, Constants.BOSS_POINTS_ON_KILL, TYPE);
        this.setVisible(false);
        this.random = new Random();
    }

    public SVGPath getSvgPath() {
        return svgPath;
    }

    public void setSvgPath(SVGPath svgPath) {
        this.svgPath = svgPath;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    protected double getRandomY() {
        return this.randomY;
    }

    protected void setRandomY(double getRandomY) {
        this.randomY = getRandomY;
    }

    protected Random getRandom() {
        return this.random;
    }

    protected void moveUp() {
        this.updateLocation(this.getPositionX(), this.getPositionY() - this.getSpeed());
    }

    protected void moveDown() {
        this.updateLocation(this.getPositionX(), this.getPositionY() + this.getSpeed());
    }

    public abstract void move();

    public abstract void initializeHitbox() throws FileNotFoundException;
}