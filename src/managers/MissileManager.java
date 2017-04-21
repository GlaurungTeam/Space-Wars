package managers;

import models.enemies.bosses.Boss;
import models.gameObjects.Explosion;
import models.gameObjects.GameObject;
import models.gameObjects.HealthableGameObject;
import models.level.Level;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MissileManager {

    public void manageMissiles(Level level) {
        if (level.getMissiles().size() == 0) {
            return;
        }

        for (int i = 0; i < level.getMissiles().size(); i++) {
            GameObject currentMissile = level.getMissiles().get(i);

            if (this.missileOutsideBoundsOfCanvas(currentMissile, level)) {
                level.removeMissile(currentMissile);
                continue;
            }

            this.renderMissile(currentMissile, level);

            //Here we check if the player collides with a missile
            if (currentMissile.intersects(level.getPlayer()) && currentMissile.getType().equals("enemy")) {
                level.getPlayerManager().resetPlayerPosition();
                level.getFuelManager().resetFuel();

                level.getPlayer().decrementLives();
                level.getPlayerManager().playerHit();

                level.getExplosionManager().playAsteroidHit(this.generateExplosion(currentMissile.getPositionX(),
                        currentMissile.getPositionY()));
                level.removeMissile(currentMissile);
            }

            //Collision detection missile hits enemy and removes it from canvas
            for (HealthableGameObject enemy : level.getRealEnemies()) {
                if (enemy.getHealth() == 0) {
                    continue;
                }

                if (currentMissile.intersects(enemy) && currentMissile.getType().equals("player")) {
                    enemy.decrementHealth();

                    if (enemy.getHealth() > 0) {
                        level.removeMissile(currentMissile);
                        level.getExplosionManager().playAsteroidHit(this.generateExplosion(currentMissile.getPositionX(),
                                currentMissile.getPositionY()));
                    } else if (enemy.getHealth() == 0) {
                        level.getExplosionManager().playAsteroidHit(this.generateExplosion(currentMissile.getPositionX(),
                                currentMissile.getPositionY()));
                        level.removeMissile(currentMissile);

                        if (!level.isActiveBoss()) {
                            level.getPlayer().incrementKillPoints(enemy.getPointsOnKill());
                        }
                    }
                }
            }

            if (level.isActiveBoss()) {
                for (Boss boss : level.getBosses()) {
                    if (!boss.isVisible()) {
                        continue;
                    }

                    if (currentMissile.intersects(boss) && currentMissile.getType().equals("player")) {
                        boss.decrementHealth();
                        level.removeMissile(currentMissile);

                        level.getExplosionManager().playUfoHit(this.generateExplosion(currentMissile.getPositionX(),
                                currentMissile.getPositionY()));
                    }
                }
            }
        }
    }

    private void renderMissile(GameObject currentMissile, Level level) {
        currentMissile.setImage(currentMissile.getFrame(currentMissile.getSprites(),
                level.getCurrentFrame(), 0.100));
        currentMissile.render(level.getGc());
        this.updateMissileLocation(currentMissile);
    }

    private void updateMissileLocation(GameObject missile) {
        if (missile.getType().equals("player")) {
            missile.updateLocation(Math.max(0, missile.getPositionX() +
                    missile.getSpeed() * Constants.MISSILE_SPEED_MULTIPLIER), missile.getPositionY());
        } else {
            missile.updateLocation(Math.max(0, missile.getPositionX() -
                    missile.getSpeed() * Constants.MISSILE_SPEED_MULTIPLIER), missile.getPositionY());
        }
    }

    private boolean missileOutsideBoundsOfCanvas(GameObject currentMissile, Level level) {
        return currentMissile.getPositionX() >= level.getCanvas().getWidth()
                || currentMissile.getPositionX() == 0;
    }

    private Explosion generateExplosion(double explosionX, double explosionY) {
        BufferedImage explosionSpriteSheet = null;

        try {
            explosionSpriteSheet = ImageIO.read(
                    new File(Constants.PROJECT_PATH + Constants.EXPLOSION_SPRITESHEET_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Explosion(explosionX, explosionY, Constants.EXPLOSION_SPEED, explosionSpriteSheet);
    }
}