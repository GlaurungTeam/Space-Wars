package entities.enemies.bosses;

import entities.HealthAbleGameObject;
import entities.level.Level;
import javafx.scene.shape.SVGPath;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public abstract class Boss extends HealthAbleGameObject {
    private static final String TYPE = "boss";

    private SVGPath svgPath;

    protected Boss(double positionX, double positionY, double objectSpeed,
                   BufferedImage spriteSheet, int width, int height, int rows, int cols, int health) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols, health, health, TYPE);
    }

    public SVGPath getSvgPath() {
        return svgPath;
    }

    protected void setSvgPath(SVGPath svgPath) {
        this.svgPath = svgPath;
    }

    public abstract void move();

    public abstract void resetHealth();

    protected abstract void initializeHitbox() throws FileNotFoundException;

    public abstract void fire(Level level);
}