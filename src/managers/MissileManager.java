package managers;

import entities.*;
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

                if (this.missileOutsideBoundsOfCanvas(currentMissile)) {
                    this.level.removeMissile(currentMissile);
                    continue;
                }

                this.renderMissile(currentMissile);

                //Here we check if the player collides with a missile
                if (currentMissile.intersects(this.level.getPlayer()) && currentMissile.getType().equals("enemy")) {
                    this.level.getPlayerManager().resetPlayerPosition(this.level.getCanvas(), this.level.getFuelManager());
                    level.getPlayer().setLives(this.level.getPlayer().getLives() - 1);
                    this.level.getPlayerManager().playerHit();

                    EffectsManager.playAsteroidHit(this.generateExplosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                    this.level.removeMissile(currentMissile);
                }

                //Collision detection missile hits asteroid and removes it from canvas
                for (HealthAbleGameObject enemy : this.level.getRealEnemies()) {
                    if (enemy.getHealth() == 0) {
                        continue;
                    }
                    if (currentMissile.intersects(enemy) && currentMissile.getType().equals("player")) {
                        enemy.setHealth(enemy.getHealth() - 1);

                        if (enemy.getHealth() > 0) {
                            this.level.removeMissile(currentMissile);
                            EffectsManager.playAsteroidHit(this.generateExplosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                        } else if (enemy.getHealth() == 0) {
                            EffectsManager.playAsteroidHit(this.generateExplosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                            this.level.removeMissile(currentMissile);

                            if (!this.level.isActiveBoss()) {
                                this.level.getPlayer().setPoints(this.level.getPlayer().getPoints() + 1);
                            }
                        }
                    }
                }


                if (this.level.isActiveBoss()) {
                    for (Boss boss : this.level.getBosses()) {
                        if (currentMissile.intersects(boss) && currentMissile.getType().equals("player")) {
                            boss.setHealth(boss.getHealth() - 1);
                            this.level.removeMissile(currentMissile);
                            EffectsManager.playUfoHit(this.generateExplosion(currentMissile.getPositionX(), currentMissile.getPositionY()));
                        }
                    }
                }
            }
        }
    }

    private void renderMissile(Missile currentMissile) {
        currentMissile.setImage(currentMissile.getFrame(currentMissile.getSprites(),
                this.level.getCurrentFrame(), 0.100));
        currentMissile.render(this.level.getGc());
        currentMissile.updateMissileLocation();
    }

    private boolean missileOutsideBoundsOfCanvas(GameObject currentMissile) {
        return currentMissile.getPositionX() >= this.level.getCanvas().getWidth()
                || currentMissile.getPositionX() == 0;
    }

    private Explosion generateExplosion(double explosionX, double explosionY){
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