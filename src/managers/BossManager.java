package managers;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import entities.Constants;
import entities.Explosion;
import entities.Missile;
import entities.enemies.bosses.Boss;
import entities.enemies.bosses.Pedobear;
import entities.level.Level;
import javafx.scene.canvas.Canvas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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

    private Timer getShootTimer() {
        return this.shootTimer;
    }

    private void setShootTimer(Timer shootTimer) {
        this.shootTimer = shootTimer;
    }

    private void setJustShowed(boolean justShowed) {
        this.justShowed = justShowed;
    }

    public Boss initializeBoss(Canvas canvas) throws FileNotFoundException {
        double startPosX = canvas.getWidth() - 200;
        double startPosY = canvas.getHeight() / 2;


        BufferedImage pedobearSpriteSheet = null;

        try{
            pedobearSpriteSheet = ImageIO.read(new File(
                    Constants.PROJECT_PATH + Constants.BOSS_PEDOBEAR_IMAGE));
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }

        Boss pedobear = new Pedobear(startPosX, startPosY, Constants.PEDOBEAR_SPEED, pedobearSpriteSheet, Constants.BOSS_PEDOBEAR_HEALTH);

        return pedobear;
    }

    void manageBoss(Level level, AsteroidManager asteroidManager) {
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
                        boss.setImage(boss.getCurrentFrame(0));//TODO Always set the current image before render child of Sprite
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
        Missile missile;
        BufferedImage missileSpriteSheet = null;

        double missileX = boss.getPositionX();
        double missileY =  boss.getPositionY() + 100;

        try {
            missileSpriteSheet = ImageIO.read(
                    new File(Constants.PROJECT_PATH + Constants.MISSILE_SPRITESHEET_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        missile = new Missile(missileX, missileY, Constants.MISSILE_SPEED, missileSpriteSheet);

        level.addEnemyMissile(missile);
    }

    private void manageBossCollision(Level level, Boss boss) {
        if (level.getPlayerManager().checkCollision(boss)) {

            BufferedImage explosionSpriteSheet = null;

            try {
                explosionSpriteSheet = ImageIO.read(
                        new File(Constants.PROJECT_PATH + Constants.EXPLOSION_SPRITESHEET_IMAGE));
            } catch (IOException e) {
                e.printStackTrace();
            }

            double explosionX = boss.getPositionX();
            double explosionY = boss.getPositionY();

            EffectsManager.playAsteroidHit(new Explosion(explosionX, explosionY,Constants.EXPLOSION_SPEED,explosionSpriteSheet));

            level.getPlayer().setLives(0);
        }
    }

    private void updateBossLocation(Boss boss) {
        boss.getSvgPath().setLayoutX(boss.getPositionX());
        boss.getSvgPath().setLayoutY(boss.getPositionY());
    }
}