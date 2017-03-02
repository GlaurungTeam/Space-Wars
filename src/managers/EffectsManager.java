package managers;



import controllers.GameController;
import entities.Explosion;
import entities.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

public class EffectsManager {
    //Implements manageExplosions() from Level class
    //Renders explosion from the list afterwards
    //Plays sound for explosion
    private GameController gc;
    private Sprite sprite;
    private Explosion explosion;
    private AudioClip explosionSoundAsteroid;
    private AudioClip explosionSoundUfo;
    private GraphicsContext graphicsContext;

    public EffectsManager(GameController gc, Sprite sprite, GraphicsContext graphicsContext) {
        this.setExplosionSoundAsteroid(explosionSoundAsteroid);
        this.setExplosionSoundUfo(explosionSoundUfo);
        this.setExplosion(gc,sprite);
        this.setGc(gc);
        this.setSprite(sprite);
        this.setGraphicsContext(graphicsContext);
    }
    private void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }
    private void setGc(GameController gc) {
        this.gc = gc;
    }
    private void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    private void setExplosionSoundAsteroid(AudioClip explosionSound) {
        this.explosionSoundAsteroid = new AudioClip(Paths.get("src/Resources/sound/explosion2.mp3").toUri().toString());
    }
    private void setExplosionSoundUfo(AudioClip explosionUfo){
        this.explosionSoundUfo = new AudioClip(Paths.get("src/Resources/sound/explossion.mp3").toUri().toString());
    }
    private void setExplosion(GameController gc, Sprite sprite) {
        this.explosion = new Explosion(gc, sprite);
    }
    //Make any type of explosion

    int counter = 0;
    public void explosionAsteroid(){
        if (explosionSoundAsteroid != null){
            explosionSoundAsteroid.play();
        }

        Image currentFrame = explosion.getCurrentExplosionFrame(explosion.getCurrentFrameIndex());
        if (explosion.getCurrentFrameIndex() < explosion.getSprites().length - 1) {
            
            explosion.setImage(currentFrame);
            explosion.render(graphicsContext);
            explosion.setCurrentFrameIndex(explosion.getCurrentFrameIndex() + counter);
        }
        this.explosion = new Explosion(gc, sprite);
        //explosion.explode();
    }
    public void explosionUfo(){
        if (explosionSoundUfo != null){
            explosionSoundUfo.play();
        }
        Image currentFrame = explosion.getCurrentExplosionFrame(explosion.getCurrentFrameIndex());
        if (explosion.getCurrentFrameIndex() < explosion.getSprites().length - 1) {
            explosion.setImage(currentFrame);
            explosion.render(graphicsContext);
            explosion.setCurrentFrameIndex(explosion.getCurrentFrameIndex() + 1);
        }
        this.explosion = new Explosion(gc, sprite);
        //explosion.explode();
    }

    public void explode() {

        explosionAsteroid();

    }
}