package managers;

import entities.Constants;
import entities.Explosion;
import entities.enemies.bosses.Boss;
import entities.enemies.bosses.Pedobear;
import entities.level.Level;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

import java.util.Timer;
import java.util.TimerTask;

public class BossManager extends EnemyManager {
    private Timer timer;
    private boolean justShowed;
    private BossManager bossManager = this;

    public BossManager(PlayerManager playerManager, FuelManager fuelManager) {
        super(playerManager, fuelManager);
        this.setTimer(new Timer());
        this.setJustShowed(true);
    }

    private void setTimer(Timer timer) {
        this.timer = timer;
    }

    private boolean isJustShowed() {
        return this.justShowed;
    }

    private void setJustShowed(boolean justShowed) {
        this.justShowed = justShowed;
    }

    public Boss initializeBoss(Canvas canvas) {
        Boss pedobear = new Pedobear(canvas, Constants.PEDOBEAR_SPEED);
        pedobear.setPosition(1095, 360.0);

        return pedobear;
    }

    public void manageBoss(Level level, AsteroidManager asteroidManager, AnimationTimer gameTimer) {
        if (level.getPlayer().getPoints() != 0
                && level.getPlayer().getPoints() % Constants.POINTS_TILL_BOSS == 0
                && !level.isActiveBoss()) {
            level.setIsActiveBoss(true);
        }

        if (level.isActiveBoss()) {
            for (Boss boss : level.getBosses()) {
                if (boss.getHealth() > 0) {
                    if (this.isJustShowed()) {
                        this.timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                bossManager.setJustShowed(false);

                                //Restart timer
                                timer.cancel();
                                timer.purge();
                                timer = new Timer();
                            }
                        }, 5000);
                    } else {
                        boss.render(level.getGc());
                        boss.move();
                        this.updateBossLocation(boss);
                        this.manageBossCollision(level, boss, gameTimer);
                    }
                } else {
                    this.setJustShowed(true);
                    level.setIsActiveBoss(false);

                    boss.resetHealth();
                    level.getPlayer().setPoints(level.getPlayer().getPoints() + 5);
                    asteroidManager.resetAsteroidPosition(level.getAsteroids(), level.getCanvas());
                }
            }
        }
    }

    private void manageBossCollision(Level level, Boss boss, AnimationTimer gameTimer) {
        if (level.getPlayerManager().checkCollision(boss)) {

            EffectsManager.playAsteroidHit(new Explosion(boss.getPositionX(), boss.getPositionY()));

            level.getPlayer().setLives(0);

            try {
                level.getPlayerManager().checkIfPlayerIsDead(level, gameTimer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateBossLocation(Boss boss) {
        boss.getSvgPath().setLayoutX(boss.getPositionX());
        boss.getSvgPath().setLayoutY(boss.getPositionY());
    }
}