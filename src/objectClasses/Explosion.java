package objectClasses;

import javafx.scene.image.Image;
import sample.Controller;
import sample.GameController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Explosion extends Sprite {
    private final GameController gameController;
    public int currentFrameIndex;

    public Explosion(GameController gameController, Missile m) {
        this.currentFrameIndex = 0;
        this.gameController = gameController;
        this.positionX = m.positionX;
        this.positionY = m.positionY;
        this.speed = 2;
        explode();
    }

    public void explode() {
        BufferedImage explosionSpriteSheet = null;
        try {
            explosionSpriteSheet = ImageIO.read(new File(
                    Controller.PROJECT_PATH + "/src/resources/explosions/rsz_explosion-spritesheet.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.setSpriteParameters(48, 49, 1, 25);
        this.loadSpriteSheet(explosionSpriteSheet);
        this.splitSprites();
        gameController.setExplosions(this);
    }

    public Image getCurrentExplosionFrame(int index) {
        return this.sprites[index];
    }
}