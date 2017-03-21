package entities.enemies;

import entities.GameObject;
import entities.Sprite;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class Asteroid extends GameObject {
    private int health;
    private int defaultHealth;

    public Asteroid(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet,
                       int width, int height, int rows, int cols, int health, int defaultHealth) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols);

        this.setHealth(health);
        this.setDefaultHealth(defaultHealth);
    }

    public Image getCurrentAsteroidFrame(int index) {
        return super.getSprites().get(index);
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

}