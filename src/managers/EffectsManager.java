package managers;


import entities.Explosion;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EffectsManager {
    //Implements manageExplosions() from Level class
    //Renders explosion from the list afterwards
    //Plays sound for explosion
    static List<Explosion> explosions = new ArrayList<>();

    public EffectsManager() {
    }

    private void addExplosion(Explosion e){
        explosions.add(e);
    }

    private List<Explosion> getExplosions() {
        return explosions;
    }

    public static void playAsteroidHit(Explosion e) {
        EffectsManager.explosions.add(e);
        AudioClip audioClip = new AudioClip(Paths.get("src/Resources/sound/explosion2.mp3").toUri().toString());
        audioClip.play();
    }

    public static void playUfoHit(Explosion e){
        EffectsManager.explosions.add(e);
        AudioClip audioClip = new AudioClip(Paths.get("src/Resources/sound/explossion.mp3").toUri().toString());
        audioClip.play();
    }

        public void manageExplosions(GraphicsContext graphicsContext) {
        if (this.getExplosions().size() != 0) {
            for (int i = 0; i < this.getExplosions().size(); i++) {
                Explosion explosion = this.getExplosions().get(i);
                Image currentFrame = explosion.getCurrentExplosionFrame(explosion.getCurrentFrameIndex());

                if (explosion.getCurrentFrameIndex() < explosion.getSprites().length - 1) {
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