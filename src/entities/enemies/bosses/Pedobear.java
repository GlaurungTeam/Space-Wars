package entities.enemies.bosses;

import entities.Constants;
import entities.SpawnCoordinates;
import helpers.Reader;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.SVGPath;

import java.io.FileNotFoundException;
import java.util.Random;

public class Pedobear extends Boss {
    private double randomY;

    public Pedobear(Canvas canvas, double speed) {
        super(canvas, speed, Constants.BOSS_PEDOBEAR_IMAGE, Constants.BOSS_PEDOBEAR_HEALTH);

        try {
            this.initializeHitbox();
        } catch (FileNotFoundException e) {
            e.getMessage();
        }

        this.setRandomY(SpawnCoordinates.getRandom(720));
    }

    @Override
    protected void initializeHitbox() throws FileNotFoundException {
        Reader reader = new Reader();
        SVGPath svgPath = new SVGPath();

        svgPath.setContent(reader.readString(Constants.BOSS_PEDOBEAR_HITBOX));
        super.setSvgPath(svgPath);
    }

    private double getRandomY() {
        return randomY;
    }

    private void setRandomY(double randomY) {
        this.randomY = randomY;
    }

    private void moveUp() {
        this.setPositionY(this.getPositionY() - this.getSpeed());
    }

    private void moveDown() {
        this.setPositionY(this.getPositionY() + this.getSpeed());
    }

    @Override
    public void move() {
        Random random = new Random();
        Pedobear pedobear = this;

        if (this.getPositionY() > this.getRandomY()) {
            pedobear.moveUp();
            return;
        }

        this.setRandomY(random.nextInt(20000));

        if (this.getPositionY() < this.getRandomY()) {
            pedobear.moveDown();
            return;
        }
    }
}