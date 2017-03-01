package managers;

import entities.*;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

public class MissileManager {
    public void manageMissiles(Level level) {
        //TODO Remove method so that the missile is removed in the manageUfos() and manageAsteroids() methods

        if (level.getMissiles().size() != 0) {
            for (int i = 0; i < level.getMissiles().size(); i++) {
                Missile currentMissile = level.getMissiles().get(i);

                if (currentMissile.getPositionX() > level.getCanvas().getWidth()) {
                    level.getMissiles().remove(currentMissile);
                    return;
                }

                currentMissile.setImage(currentMissile.getFrame(currentMissile.getSprites(),
                        level.getCurrentFrame(), 0.100));
                currentMissile.render(level.getGc());
                currentMissile.updateMissileLocation();

                //Collision detection missile hits asteroid and removes it from canvas
                for (Asteroid asteroidToCheck : level.getAsteroids()) {
                    if (asteroidToCheck.getHitStatus()) {
                        continue;
                    }

                    if (currentMissile.intersects(asteroidToCheck)) {
                        asteroidToCheck.setHitStatus(true);

                        AudioClip explode = new AudioClip
                                (Paths.get("src/resources/sound/explosion2.mp3").toUri().toString());
                        explode.play(0.6);

                        //Remove missile from missiles array and explode
                        Explosion explosion = new Explosion(level.getGameController(), currentMissile);

                        level.getMissiles().remove(currentMissile);
                        level.getExplosions().add(explosion);

                        //TODO Move that one to the killing aliens method to display score

                        level.getPlayer().setPoints(level.getPlayer().getPoints() + 1);

                        //String score = String.format("Score: %d", level.getPlayer().getPoints());
                        //scoreLine.setText(score)
                        //TODO create class to work with all text fields in the game scene
                        //TODO Implement score tracker
                    }
                }

                for (Ufo ufoToCheck : level.getUfos()) {
                    if (ufoToCheck.getHitStatus()) {
                        continue;
                    }

                    if (currentMissile.intersects(ufoToCheck)) {
                        ufoToCheck.setHitStatus(true);

                        AudioClip explode = new AudioClip
                                (Paths.get("src/resources/sound/explossion.mp3").toUri().toString());
                        explode.play(0.2);

                        Explosion explosion = new Explosion(level.getGameController(), currentMissile);

                        level.getMissiles().remove(currentMissile);
                        level.getExplosions().add(explosion);

                        level.getPlayer().setPoints(level.getPlayer().getPoints() + 3);
                    }
                }
            }
        }
    }
}