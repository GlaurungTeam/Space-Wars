package managers;

import entities.*;
import entities.enemies.bosses.Boss;
import entities.level.Level;

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
            Missile currentMissile = level.getMissiles().get(i);

            if (this.missileOutsideBoundsOfCanvas(currentMissile, level)) {
                level.removeMissile(currentMissile);
                continue;
            }

            this.renderMissile(currentMissile, level);

            //Here we check if the player collides with a missile
            if (currentMissile.intersects(level.getPlayer()) && currentMissile.getType().equals("enemy")) {
                level.getPlayerManager().resetPlayerPosition();
                level.getFuelManager().resetFuel();

                level.getPlayer().setLives(level.getPlayer().getLives() - 1);
                level.getPlayerManager().playerHit();

                EffectsManager.playAsteroidHit(this.generateExplosion(currentMissile.getPositionX(),
                        currentMissile.getPositionY()));
                level.removeMissile(currentMissile);
            }

            //Collision detection missile hits enemy and removes it from canvas
            for (HealthAbleGameObject enemy : level.getRealEnemies()) {
                if (enemy.getHealth() == 0) {
                    continue;
                }

                if (currentMissile.intersects(enemy) && currentMissile.getType().equals("player")) {
                    enemy.setHealth(enemy.getHealth() - 1);

                    if (enemy.getHealth() > 0) {
                        level.removeMissile(currentMissile);
                        EffectsManager.playAsteroidHit(this.generateExplosion(currentMissile.getPositionX(),
                                currentMissile.getPositionY()));
                    } else if (enemy.getHealth() == 0) {
                        EffectsManager.playAsteroidHit(this.generateExplosion(currentMissile.getPositionX(),
                                currentMissile.getPositionY()));
                        level.removeMissile(currentMissile);

                        if (!level.isActiveBoss()) {
                            level.getPlayer().setPoints(level.getPlayer().getPoints() + enemy.getPointsOnKill());
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
                        boss.setHealth(boss.getHealth() - 1);
                        level.removeMissile(currentMissile);

                        EffectsManager.playUfoHit(this.generateExplosion(currentMissile.getPositionX(),
                                currentMissile.getPositionY()));
                    }
                }
            }
        }
    }

    private void renderMissile(Missile currentMissile, Level level) {
        currentMissile.setImage(currentMissile.getFrame(currentMissile.getSprites(),
                level.getCurrentFrame(), 0.100));
        currentMissile.render(level.getGc());
        currentMissile.updateMissileLocation();
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