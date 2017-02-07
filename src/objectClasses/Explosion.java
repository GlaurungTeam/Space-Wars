package objectClasses;

import javafx.scene.image.Image;
import sample.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Explosion extends Sprite {
    public static ArrayList<Explosion> explosions = new ArrayList<>();
    public int currentFrameIndex;

    public Explosion() {
        this.currentFrameIndex = 0;
    }

    public void explode(Missile m) {

        Explosion explosion = new Explosion();
        explosion.setPosition(m.positionX, m.positionY, 2);
        BufferedImage explosionSpriteSheet = null;

        try {
            explosionSpriteSheet = ImageIO.read(new File(
                    Controller.PROJECT_PATH + "/src/resources/explosions/rsz_explosion-spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        explosion.setSpriteParameters(48, 49, 1, 25);
        explosion.loadSpriteSheet(explosionSpriteSheet);
        explosion.splitSprites();
        Explosion.explosions.add(explosion);
    }

    public Image getCurrentExplosionFrame(int index) {
        return this.sprites[index];
    }
}