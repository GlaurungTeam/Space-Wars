package managers;

import entities.Constants;
import entities.Explosion;
import entities.GameObject;
import entities.Missile;
import entities.enemies.Asteroid;
import entities.enemies.bosses.Boss;
import entities.level.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MissileManager {
    private Level level;


    public MissileManager(Level level){
        this.level = level;
    }

    public void manageMissiles() {
        if (this.level.getMissiles().size() != 0) {
            for (int i = 0; i < this.level.getMissiles().size(); i++) {
                Missile currentMissile = this.level.getMissiles().get(i);
                if (currentMissile.getPositionX() > this.level.getCanvas().getWidth()) {
                    this.level.removeMissile(currentMissile);
                    return;
                }
                currentMissile.setImage(currentMissile.getFrame(currentMissile.getSprites(),
                        this.level.getCurrentFrame(), 0.100));
                currentMissile.render(this.level.getGc());
                currentMissile.updateMissileLocation();

                //Here we check if the player collides with a missile
                if (currentMissile.intersects(this.level.getPlayer())) {
                    this.level.getPlayerManager().resetPlayerPosition(this.level.getCanvas(), this.level.getFuelManager());
                    level.getPlayer().setLives(this.level.getPlayer().getLives() - 1);
                    this.level.getPlayerManager().playerHit();

                    EffectsManager.playAsteroidHit(this.createExplosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                    this.level.removeMissile(currentMissile);
                }

                //Collision detection missile hits asteroid and removes it from canvas
                for (Asteroid asteroidToCheck : this.level.getAsteroids()) {
                    if (asteroidToCheck.getHealth() == 0) {
                        continue;
                    }
                    if (currentMissile.intersects(asteroidToCheck)) {
                        asteroidToCheck.setHealth(asteroidToCheck.getHealth() - 1);

                        if (asteroidToCheck.getHealth() > 0) {
                            this.level.removeMissile(currentMissile);
                            EffectsManager.playAsteroidHit(this.createExplosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                        } else if (asteroidToCheck.getHealth() == 0) {
                            EffectsManager.playAsteroidHit(this.createExplosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                            this.level.removeMissile(currentMissile);

                            if (!this.level.isActiveBoss()) {
                                this.level.getPlayer().setPoints(this.level.getPlayer().getPoints() + 1);
                            }
                        }
                    }
                }

                for (GameObject ufoToCheck : this.level.getEnemies()) {
                    if (ufoToCheck.getHitStatus()) {
                        continue;
                    }
                    if (currentMissile.intersects(ufoToCheck)) {
                        ufoToCheck.setHitStatus(true);

                        EffectsManager.playUfoHit(this.createExplosion(currentMissile.getPositionX(), currentMissile.getPositionY()));

                        this.level.removeMissile(currentMissile);
                        this.level.getPlayer().setPoints(this.level.getPlayer().getPoints() + 3);
                    }
                }

                if (this.level.isActiveBoss()) {
                    for (Boss boss : this.level.getBosses()) {
                        if (currentMissile.intersects(boss)) {
                            boss.setHealth(boss.getHealth() - 1);
                            this.level.removeMissile(currentMissile);
                            EffectsManager.playUfoHit(this.createExplosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                        }
                    }
                }
            }
        }
    }

    public void manageEnemyMissiles(Level level) {
        if (level.getEnemyMissiles().size() != 0) {
            for (int i = 0; i < level.getEnemyMissiles().size(); i++) {
                Missile currentMissile = level.getEnemyMissiles().get(i);
                if (currentMissile.getPositionX() < 50) {
                    level.removeEnemyMissile(currentMissile);
                    return;
                }
                currentMissile.setImage(currentMissile.getFrame(currentMissile.getSprites(),
                        level.getCurrentFrame(), 0.100));
                currentMissile.render(level.getGc());
                currentMissile.updateEnemyMissileLocation();

                //Here we check if the player collides with a missile
                if (currentMissile.intersects(level.getPlayer())) {
                    level.getPlayerManager().resetPlayerPosition(level.getCanvas(), level.getFuelManager());
                    level.getPlayer().setLives(level.getPlayer().getLives() - 1);
                    level.getPlayerManager().playerHit();

                    EffectsManager.playAsteroidHit(this.createExplosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                    level.removeEnemyMissile(currentMissile);
                }
            }
        }
    }

    private Explosion createExplosion(double explosionX, double explosionY){
        BufferedImage explosionSpriteSheet = null;

        try {
            explosionSpriteSheet = ImageIO.read(
                    new File(Constants.PROJECT_PATH + Constants.EXPLOSION_SPRITESHEET_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new Explosion(explosionX, explosionY,Constants.EXPLOSION_SPEED,explosionSpriteSheet);
    }
}