package managers;

import entities.Constants;
import entities.enemies.bosses.Boss;
import entities.enemies.bosses.Pedobear;
import entities.level.Level;
import javafx.scene.canvas.Canvas;

import java.util.Timer;
import java.util.TimerTask;

public class BossManager extends EnemyManager {
    private Timer timer;
    private boolean hasJustShowed;

    public BossManager(PlayerManager playerManager, FuelManager fuelManager) {
        super(playerManager, fuelManager);
        this.timer = new Timer();
        this.hasJustShowed = true;
    }

    public Boss initializeBoss(Canvas canvas) {
        Boss pedobear = new Pedobear(canvas, Constants.PEDOBEAR_SPEED);
        pedobear.setPosition(1095, 360.0);

        return pedobear;
    }

    public void manageBoss(Level level, AsteroidManager asteroidManager) {
        if (level.getPlayer().getPoints() != 0
                && level.getPlayer().getPoints() % Constants.POINTS_TILL_BOSS == 0) {
            level.setIsActiveBoss(true);
        }

        if (level.isActiveBoss()) {
            for (Boss boss : level.getBosses()) {
                if (boss.getHealth() > 0) {
                    if (hasJustShowed) {
                        BossManager bossManager = this;

                        this.timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                boss.render(level.getGc());
                                bossManager.hasJustShowed = false;
                            }
                        }, 5000);
                    } else {
                        boss.render(level.getGc());
                    }

                    boss.move();
                    this.updateBossLocation(boss);
                } else {
                    level.setIsActiveBoss(false);
                    asteroidManager.resetAsteroidPosition(level.getAsteroids(), level.getCanvas());
                    boss.resetHealth();
                    this.hasJustShowed = true;
                    level.getPlayer().setPoints(level.getPlayer().getPoints() + 5);
                }
            }
        }
    }

    private void updateBossLocation(Boss boss) {
        boss.getSvgPath().setLayoutX(boss.getPositionX());
        boss.getSvgPath().setLayoutY(boss.getPositionY());
    }
}