package Entities;

import javafx.scene.image.Image;
import Controllers.MenuController;
import Controllers.GameController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explosion extends Sprite {
    private GameController gameController;
    private int currentFrameIndex;

    public Explosion(GameController gameController, Missile m) {
        this.setCurrentFrameIndex(0);
        this.setGameController(gameController);
        this.setPositionX(m.getPositionX());
        this.setPositionY(m.getPositionY());
        this.setSpeed(2);
        this.explode();
    }

    public GameController getGameController() {
        return this.gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public int getCurrentFrameIndex() {
        return this.currentFrameIndex;
    }

    public void setCurrentFrameIndex(int currentFrameIndex) {
        this.currentFrameIndex = currentFrameIndex;
    }

    public void explode() {
        BufferedImage explosionSpriteSheet = null;

        try {
            explosionSpriteSheet = ImageIO.read(new File(
                    MenuController.PROJECT_PATH + "/src/Resources/explosions/rsz_explosion-spritesheet.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.setSpriteParameters(48, 49, 1, 25);
        this.loadSpriteSheet(explosionSpriteSheet);
        this.splitSprites();
        this.getGameController().addExplosions(this);
    }

    public Image getCurrentExplosionFrame(int index) {
        return this.getSprites()[index];
    }
}