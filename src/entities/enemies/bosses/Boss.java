package entities.enemies.bosses;

import entities.GameObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.SVGPath;

import java.io.FileNotFoundException;
import java.util.HashMap;

public abstract class Boss extends GameObject {
    private int health;
    private static HashMap<String, Integer> bosses;
    private SVGPath svgPath;

    static {
        bosses = new HashMap<>();
        bosses.put("Pedobear", 3);
    }

    public Boss(Canvas canvas, double speed, String imagePath) {
        super(canvas, speed, imagePath);
        this.setHealth(bosses.get(this.getClass().getSimpleName()));
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
