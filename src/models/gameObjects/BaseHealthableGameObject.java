package models.gameObjects;

import contracts.HealthableGameObject;

import java.awt.image.BufferedImage;

public abstract class BaseHealthableGameObject extends BaseGameObject implements HealthableGameObject {
    private static final int DECREMENT_COUNT = 1;

    private int health;
    private int defaultHealth;
    private int pointsOnKill;

    protected BaseHealthableGameObject(double positionX, double positionY, double objectSpeed,
                                       BufferedImage spriteSheet, int width, int height,
                                       int rows, int cols, int health,
                                       int defaultHealth, int pointsOnKill, String type) {

        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols, type);

        this.health = health;
        this.defaultHealth = defaultHealth;
        this.pointsOnKill = pointsOnKill;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void revive() {
        this.health = this.defaultHealth;
    }

    @Override
    public void decrementHealth() {
        this.health -= DECREMENT_COUNT;
    }

    @Override
    public int getPointsOnKill() {
        return this.pointsOnKill;
    }
}