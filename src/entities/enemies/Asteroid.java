package entities.enemies;

import entities.GameObject;
import enums.SpriteSheetParameters;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;

public class Asteroid extends GameObject {
    private int health;
    private int defaultHealth;

    public Asteroid(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int health, int defaultHealth) {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.ASTEROID.getWidth(),
                SpriteSheetParameters.ASTEROID.getHeight(),
                SpriteSheetParameters.ASTEROID.getRows(),
                SpriteSheetParameters.ASTEROID.getCols());

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