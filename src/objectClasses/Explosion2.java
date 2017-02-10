package objectClasses;

import javafx.scene.image.Image;
import sample.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Explosion2 extends Sprite {
    public static ArrayList<Explosion2> explosions2 = new ArrayList<>();
    public int currentFrameIndex;

    public Explosion2() {
        this.currentFrameIndex = 0;
    }

    public void explode(Ufo p) {

        Explosion2 explosion2 = new Explosion2();
        explosion2.setPosition(p.positionX, p.positionY, 2);
        BufferedImage explosionSpriteSheet = null;

        try {
            explosionSpriteSheet = ImageIO.read(new File(
                    Controller.PROJECT_PATH + "/src/resources/explosions/rsz_explosion-spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        explosion2.setSpriteParameters(48, 49, 1, 25);
        explosion2.loadSpriteSheet(explosionSpriteSheet);
        explosion2.splitSprites();
        Explosion2.explosions2.add(explosion2);
    }

    public Image getCurrentExplosionFrame(int index) {
        return this.sprites[index];
    }
}
