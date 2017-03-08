package entities.enemies.bosses;

import entities.GameObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.SVGPath;

import java.io.FileNotFoundException;

public abstract class Boss extends GameObject {
    private int health;
    private SVGPath svgPath;

    public Boss(Canvas canvas, double speed, String imagePath, int health) {
        super(canvas, speed, imagePath);
        this.setHealth(health);
    }

    protected abstract void initializeHitbox() throws FileNotFoundException;

    public SVGPath getSvgPath() {
        return svgPath;
    }

    protected void setSvgPath(SVGPath svgPath) {
        this.svgPath = svgPath;
    }

    public int getHealth() {
        return health;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    public abstract void move();
}