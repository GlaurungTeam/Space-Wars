package models.gameObjects;

import java.awt.image.BufferedImage;

public abstract class BaseHealthableGameObject extends BaseGameObject implements HealthableGameObject {

    private int health;
    private int defaultHealth;
    private int pointsOnKill;

    protected BaseHealthableGameObject(double positionX, double positionY, double objectSpeed,
                                       BufferedImage spriteSheet, int width, int height,
                                       int rows, int cols, int health,
                                       int defaultHealth, int pointsOnKill, String type) {

        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols, type);

        this.setHealth(health);
        this.defaultHealth = defaultHealth;
        this.pointsOnKill = pointsOnKill;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getDefaultHealth() {
        return defaultHealth;
    }

    @Override
    public int getPointsOnKill() {
        return this.pointsOnKill;
    }
}