package managers;

import entities.Explosion;
import entities.GameObject;
import entities.Missile;
import entities.Player;
import entities.enemies.Asteroid;
import entities.enemies.bosses.Boss;
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

                //Here we check if the player collides with a missile
                if (currentMissile.intersects(level.getPlayer())) {
                    level.getPlayerManager().resetPlayerPosition(level.getCanvas(), level.getFuelManager());
                    level.getPlayer().setLives(level.getPlayer().getLives() - 1);
                    level.getPlayerManager().playerHit();

                    EffectsManager.playAsteroidHit(new Explosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                    level.getMissiles().remove(currentMissile);
                }

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

                            if (!level.isActiveBoss()) {
                                level.getPlayer().setPoints(level.getPlayer().getPoints() + 1);
                            }
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

                if (level.isActiveBoss()) {
                    for (Boss boss : level.getBosses()) {
                        if (currentMissile.intersects(boss)) {
                            boss.setHealth(boss.getHealth() - 1);
                            level.getMissiles().remove(currentMissile);
                            EffectsManager.playUfoHit(new Explosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                        }
                    }
                }
            }
        }
    }
}