package entities.enemies.bosses;

import entities.Constants;
import entities.Missile;
import entities.level.Level;
import enums.SpriteSheetParameters;
import helpers.SVGPathReader;
import javafx.scene.shape.SVGPath;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class Pedobear extends Boss {
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

    @Override
    protected void initializeHitbox() throws FileNotFoundException {
        SVGPathReader SVGPathReader = new SVGPathReader();
        SVGPath svgPath = new SVGPath();

        svgPath.setContent(SVGPathReader.readString(Constants.BOSS_PEDOBEAR_SVGPATH_LOCATION));
        super.setSvgPath(svgPath);
    }

    @Override
    public void fire(Level level) {
        Missile missile;
        BufferedImage missileSpriteSheet = null;

        double missileX = this.getPositionX();
        double missileY = this.getPositionY() + Constants.MISSILE_POSITION_Y_OFFSET;

        try {
            missileSpriteSheet = ImageIO.read(
                    new File(Constants.PROJECT_PATH + Constants.MISSILE_SPRITESHEET_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        missile = new Missile(missileX, missileY, Constants.MISSILE_SPEED, missileSpriteSheet, "enemy");

        level.addMissile(missile);
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