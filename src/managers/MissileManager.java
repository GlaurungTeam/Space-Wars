package managers;

import entities.*;
import entities.level.Level;

public class MissileManager {
    public void manageMissiles(Level level) {
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
                    if (asteroidToCheck.getHealth() == 0) {
                        continue;
                    }
                    if (currentMissile.intersects(asteroidToCheck)) {
                        asteroidToCheck.setHealth(asteroidToCheck.getHealth() - 1);

                        if (asteroidToCheck.getHealth() > 0) {
                            level.getMissiles().remove(currentMissile);
                            EffectsManager.playAsteroidHit(new Explosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                        } else if (asteroidToCheck.getHealth() == 0) {
                            EffectsManager.playAsteroidHit(new Explosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                            level.getMissiles().remove(currentMissile);
                            level.getPlayer().setPoints(level.getPlayer().getPoints() + 1);
                        }
                    }
                }

                for (GameObject ufoToCheck : level.getEnemies()) {
                    if (ufoToCheck.getHitStatus()) {
                        continue;
                    }
                    if (currentMissile.intersects(ufoToCheck)) {
                        ufoToCheck.setHitStatus(true);

                        EffectsManager.playUfoHit(new Explosion(currentMissile.getPositionX(), currentMissile.getPositionY()));

                        level.getMissiles().remove(currentMissile);
                        level.getPlayer().setPoints(level.getPlayer().getPoints() + 3);
                    }
                }
            }
        }
    }
}