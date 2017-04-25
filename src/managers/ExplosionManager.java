package managers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import models.gameObjects.Explosion;
import contracts.GameObject;
import utils.Constants;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExplosionManager {

    private static final int CURRENT_FRAME_INDEX_INCREMENT_VALUE = 1;

    private List<GameObject> explosions;

    public ExplosionManager() {
        explosions = new ArrayList<>();
    }

    public void playAsteroidHit(Explosion e) {
        this.explosions.add(e);
        AudioClip audioClip = new AudioClip(Paths.get(Constants.ASTEROID_HIT_SOUND).toUri().toString());
        audioClip.play();
    }

    public void playUfoHit(Explosion e) {
        this.explosions.add(e);
        AudioClip audioClip = new AudioClip(Paths.get(Constants.UFO_HIT_SOUND).toUri().toString());
        audioClip.play();
    }

    public void manageExplosions(GraphicsContext graphicsContext) {
        if (this.explosions.size() == 0) {
            return;
        }

        for (int i = 0; i < this.explosions.size(); i++) {
            GameObject explosion = this.explosions.get(i);
            Image currentFrame = explosion.getImageFromSpritesheet(explosion.getCurrentFrameIndex());

            if (explosion.getCurrentFrameIndex() < explosion.getSprites().size() - 1) {
                explosion.setImage(currentFrame);
                explosion.render(graphicsContext);
                explosion.setCurrentFrameIndex(explosion.getCurrentFrameIndex() + CURRENT_FRAME_INDEX_INCREMENT_VALUE);
            } else {
                explosions.remove(i);
            }
        }
    }
}