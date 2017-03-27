package entities.enemies.bosses;

import entities.Constants;
import entities.SpawnCoordinates;
import enums.SpriteSheetParameters;
import helpers.SVGPathReader;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.SVGPath;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Random;

public class Pedobear extends Boss {
    private double randomY;

    public Pedobear(double positionX, double positionY, double objectSpeed,
                       BufferedImage spriteSheet, int health) throws FileNotFoundException {
        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.BOSS_PEDO_BEAR.getWidth(),
                SpriteSheetParameters.BOSS_PEDO_BEAR.getHeight(),
                SpriteSheetParameters.BOSS_PEDO_BEAR.getRows(),
                SpriteSheetParameters.BOSS_PEDO_BEAR.getCols(),
                health);
        this.initializeHitbox();
    }

    @Override
    protected void initializeHitbox() throws FileNotFoundException {
        SVGPathReader SVGPathReader = new SVGPathReader();
        SVGPath svgPath = new SVGPath();

        svgPath.setContent(SVGPathReader.readString(Constants.BOSS_PEDOBEAR_SVGPATH_LOCATION));
        super.setSvgPath(svgPath);
    }

    @Override
    protected void fire() {
        //TODO create missiles
    }

    private double getRandomY() {
        return randomY;
    }

    private void setRandomY(double randomY) {
        this.randomY = randomY;
    }

    private void moveUp() {
        this.updateLocation(this.getPositionX(),this.getPositionY() - this.getSpeed());
    }

    private void moveDown() {
        this.updateLocation(this.getPositionX(),this.getPositionY() + this.getSpeed());
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
        }
    }

    @Override
    public void resetHealth() {
        this.setHealth(Constants.BOSS_PEDOBEAR_HEALTH);
    }
}