package models.enemies.bosses;

import helpers.Reader;
import utils.Constants;
import enums.SpriteSheetParameters;
import helpers.SVGPathReader;
import javafx.scene.shape.SVGPath;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Random;

public class Pedobear extends BaseBoss {

    private double randomY;

    public Pedobear(double positionX, double positionY, double objectSpeed,
                    BufferedImage spriteSheet, int health) throws FileNotFoundException {

        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.BOSS_PEDOBEAR.getWidth(),
                SpriteSheetParameters.BOSS_PEDOBEAR.getHeight(),
                SpriteSheetParameters.BOSS_PEDOBEAR.getRows(),
                SpriteSheetParameters.BOSS_PEDOBEAR.getCols(),
                health);
        this.initializeHitbox();
    }

    private double getRandomY() {
        return randomY;
    }

    private void setRandomY(double randomY) {
        this.randomY = randomY;
    }

    private void moveUp() {
        this.updateLocation(this.getPositionX(), this.getPositionY() - this.getSpeed());
    }

    private void moveDown() {
        this.updateLocation(this.getPositionX(), this.getPositionY() + this.getSpeed());
    }

    @Override
    public void initializeHitbox() throws FileNotFoundException {
        Reader SVGPathReader = new SVGPathReader();
        SVGPath svgPath = new SVGPath();

        svgPath.setContent(SVGPathReader.read(Constants.BOSS_PEDOBEAR_SVGPATH_LOCATION));
        super.setSvgPath(svgPath);
    }

    @Override
    public void move() {
        Random random = new Random();

        if (this.getPositionY() > this.getRandomY()) {
            this.moveUp();
            return;
        }

        this.setRandomY(random.nextInt(Constants.BOSS_POSITION_Y_BOUND));

        if (this.getPositionY() < this.getRandomY()) {
            this.moveDown();
        }
    }

    @Override
    public void resetHealth() {
        this.setHealth(Constants.BOSS_PEDOBEAR_HEALTH);
    }
}