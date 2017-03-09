package managers;

import entities.Constants;
import entities.Explosion;
import entities.Missile;
import entities.enemies.bosses.Boss;
import entities.enemies.bosses.Pedobear;
import entities.level.Level;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BossManager extends EnemyManager {
    private Timer showTimer;
    private Timer shootTimer;
    private boolean justShowed;
    private BossManager bossManager = this;

    public BossManager(PlayerManager playerManager, FuelManager fuelManager) {
        super(playerManager, fuelManager);
        this.setShowTimer(new Timer());
        this.setJustShowed(true);
        this.setShootTimer(new Timer());
    }

    private void setShowTimer(Timer showTimer) {
        this.showTimer = showTimer;
    }

    private boolean isJustShowed() {
        return this.justShowed;
    }

    public Timer getShootTimer() {
        return this.shootTimer;
    }

    public void setShootTimer(Timer shootTimer) {
        this.shootTimer = shootTimer;
    }

    private void setJustShowed(boolean justShowed) {
        this.justShowed = justShowed;
    }

    public Boss initializeBoss(Canvas canvas) {
        Boss pedobear = new Pedobear(canvas, Constants.PEDOBEAR_SPEED);
        pedobear.setPosition(canvas.getWidth() - 200, canvas.getHeight() / 2);

        return pedobear;
    }

    public void manageBoss(Level level, AsteroidManager asteroidManager) {
        for (Boss boss : level.getBosses()) {
            if (level.getPlayer().getPoints() != 0
                    && level.getPlayer().getPoints() % Constants.POINTS_TILL_BOSS == 0
                    && !level.isActiveBoss()) {
                level.setIsActiveBoss(true);

                this.getShootTimer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        bossManager.fire(level, boss);
                    }
                }, 5000, 2000);
            }

            if (level.isActiveBoss()) {
                if (boss.getHealth() > 0) {
                    if (this.isJustShowed()) {
                        this.showTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                bossManager.setJustShowed(false);

                                //Restart showTimer
                                showTimer.cancel();
                                showTimer.purge();
                                showTimer = new Timer();
                            }
                        }, 5000);
                    } else {
                        boss.render(level.getGc());
                        boss.move();
                        this.updateBossLocation(boss);
                        this.manageBossCollision(level, boss);
                    }
                } else {
                    //Restart shootTimer
                    shootTimer.cancel();
                    shootTimer.purge();
                    shootTimer = new Timer();

                    this.setJustShowed(true);
                    level.setIsActiveBoss(false);

                    boss.resetHealth();
                    level.getPlayer().setPoints(level.getPlayer().getPoints() + 5);
                    asteroidManager.resetAsteroidPosition(level.getAsteroids(), level.getCanvas());
                }
            }
        }
    }

    private void fire(Level level, Boss boss) {
        Missile missile = new Missile(boss.getPositionX(), boss.getPositionY() + 100, Constants.MISSILE_SPEED);
        BufferedImage missileSpriteSheet = null;

        try {
            missileSpriteSheet = ImageIO.read(
                    new File(Constants.PROJECT_PATH + Constants.MISSILE_SPRITESHEET_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        missile.setSpriteParameters(31, 7, 1, 23);
        missile.setSpriteSheet(missileSpriteSheet);
        missile.splitSprites();

        level.getEnemyMissiles().add(missile);
    }

    private void manageBossCollision(Level level, Boss boss) {
        if (level.getPlayerManager().checkCollision(boss)) {

            EffectsManager.playAsteroidHit(new Explosion(boss.getPositionX(), boss.getPositionY()));

            level.getPlayer().setLives(0);
        }
    }

    private void updateBossLocation(Boss boss) {
        boss.getSvgPath().setLayoutX(boss.getPositionX());
        boss.getSvgPath().setLayoutY(boss.getPositionY());
    }
}