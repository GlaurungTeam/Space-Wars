package entities;

import java.awt.image.BufferedImage;

public abstract class HealthAbleGameObject extends GameObject {
    private int health;
    private int defaultHealth;
    private int pointsOnKill;

    protected HealthAbleGameObject(double positionX, double positionY, double objectSpeed,
                                   BufferedImage spriteSheet, int width, int height, int rows,
                                   int cols, int health, int defaultHealth, int pointsOnKill, String type) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols, type);

        this.setHealth(health);
        this.setDefaultHealth(defaultHealth);
        this.setPointsOnKill(pointsOnKill);
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDefaultHealth() {
        return defaultHealth;
    }

    private void setDefaultHealth(int defaultHealth) {
        this.defaultHealth = defaultHealth;
    }

    public int getPointsOnKill() {
        return this.pointsOnKill;
    }

    private void setPointsOnKill(int pointsOnKill) {
        this.pointsOnKill = pointsOnKill;
    }
}