package entities.enemies.bosses;

import entities.GameObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.SVGPath;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public abstract class Boss extends GameObject {
    private int health;
    private SVGPath svgPath;

    protected Boss(double positionX, double positionY, double objectSpeed,
                   BufferedImage spriteSheet, int width, int height, int rows, int cols, int health) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols);
        this.setHealth(health);
    }

    public SVGPath getSvgPath() {
        return svgPath;
    }

    protected void setSvgPath(SVGPath svgPath) {
        this.svgPath = svgPath;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract void move();

    public abstract void resetHealth();

    protected abstract void initializeHitbox() throws FileNotFoundException;

    protected abstract void fire();
}