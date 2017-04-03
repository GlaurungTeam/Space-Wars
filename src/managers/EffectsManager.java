package managers;

import entities.Constants;
import entities.Explosion;
import entities.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EffectsManager {
    private static List<GameObject> explosions;

    public EffectsManager() {
        explosions = new ArrayList<>();
    }

    private void addExplosion(Explosion e) {
        explosions.add(e);
    }

    private List<GameObject> getExplosions() {
        return explosions;
    }

    public static void playAsteroidHit(Explosion e) {
        EffectsManager.explosions.add(e);
        AudioClip audioClip = new AudioClip(Paths.get(Constants.ASTEROID_HIT_SOUND).toUri().toString());
        audioClip.play();
    }

    public static void playUfoHit(Explosion e) {
        EffectsManager.explosions.add(e);
        AudioClip audioClip = new AudioClip(Paths.get(Constants.UFO_HIT_SOUND).toUri().toString());
        audioClip.play();
    }

    public void manageExplosions(GraphicsContext graphicsContext) {
        if (this.getExplosions().size() != 0) {
            for (int i = 0; i < this.getExplosions().size(); i++) {
                GameObject explosion = this.getExplosions().get(i);
                Image currentFrame = explosion.getCurrentExplosionFrame(explosion.getCurrentFrameIndex());

                if (explosion.getCurrentFrameIndex() < explosion.getSprites().size() - 1) {
                    explosion.setImage(currentFrame);
                    explosion.render(graphicsContext);
                    explosion.setCurrentFrameIndex(explosion.getCurrentFrameIndex() + 1);
                } else {
                    explosions.remove(i);
                }
            }
        }
    }
}