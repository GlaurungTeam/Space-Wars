package managers;

import javafx.scene.canvas.Canvas;
import models.enemies.bosses.Boss;
import models.gameObjects.Explosion;
import models.gameObjects.Missile;
import models.level.Level;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

public class BossManager extends EnemyManager {

    private Timer showTimer;
    private Timer shootTimer;
    private boolean justShowed;
    private BossManager bossManager = this;

    public BossManager() {
        this.showTimer = new Timer();
        this.shootTimer = new Timer();
        this.justShowed = true;
    }

    private void setJustShowed(boolean justShowed) {
        this.justShowed = justShowed;
    }

    private void fire(Level level, Boss boss) {
        BufferedImage missileSpriteSheet = null;

        double missileX = boss.getPositionX();
        double missileY = boss.getPositionY() + Constants.MISSILE_POSITION_Y_OFFSET;

        try {
            missileSpriteSheet = ImageIO.read(
                    new File(Constants.PROJECT_PATH + Constants.MISSILE_SPRITESHEET_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Missile missile = new Missile(missileX, missileY, Constants.MISSILE_SPEED, missileSpriteSheet, "enemy");
        level.addMissile(missile);
    }

    @SuppressWarnings("unchecked")
    public Boss initializeBoss(Canvas canvas, String bossType) throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        double startPosX = canvas.getWidth() - Constants.BOSS_RIGHT_OFFSET;
        double startPosY = canvas.getHeight() / 2;

        Class<Constants> constantsClass = (Class<Constants>) Class.forName(Constants.CONSTANTS_PACKAGE + "Constants");
        Field[] constantsFields = constantsClass.getDeclaredFields();

        BufferedImage bossSpriteSheet = null;
        double bossSpeed = 0;
        int bossHealth = 0;

        Class<? extends Annotation> healthAnnotation =
                (Class<? extends Annotation>) Class.forName(Constants.ANNOTATIONS_PACKAGE + bossType + "Health");
        Class<? extends Annotation> speedAnnotation =
                (Class<? extends Annotation>) Class.forName(Constants.ANNOTATIONS_PACKAGE + bossType + "Speed");
        Class<? extends Annotation> imageAnnotation =
                (Class<? extends Annotation>) Class.forName(Constants.ANNOTATIONS_PACKAGE + bossType + "Image");

        for (Field field : constantsFields) {
            if (field.isAnnotationPresent(healthAnnotation)) {
                bossHealth = field.getInt(null);
            } else if (field.isAnnotationPresent(speedAnnotation)) {
                bossSpeed = field.getDouble(null);
            } else if (field.isAnnotationPresent(imageAnnotation)) {
                bossSpriteSheet = ImageIO.read(new File(
                        Constants.PROJECT_PATH + field.get(null)));
            }
        }

        Class<? extends Boss> bossClass = (Class<? extends Boss>) Class.forName(Constants.BOSSES_PACKAGE + bossType);
        Constructor<? extends Boss> constructor = (Constructor<? extends Boss>) bossClass.getDeclaredConstructors()[0];

        Boss bossObject = constructor.newInstance(startPosX, startPosY, bossSpeed, bossSpriteSheet, bossHealth);
        return bossObject;
    }

    public void manageBoss(Level level) {
        for (Boss boss : level.getBosses()) {
            if (level.getPlayer().getPoints() != 0 &&
                    level.getPlayer().getPoints() % Constants.POINTS_TILL_BOSS == 0 &&
                    !level.isActiveBoss()) {

                level.setIsActiveBoss(true);

                this.shootTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        bossManager.fire(level, boss);
                    }
                }, 5000, 2000);
            }

            if (level.isActiveBoss()) {
                if (boss.getHealth() > 0) {
                    if (this.justShowed) {
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
                        boss.setImage(boss.getCurrentExplosionFrame(0));
                        boss.render(level.getGc());
                        boss.move();
                        boss.setVisible(true);
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
                    boss.setVisible(false);

                    boss.revive();
                    level.getPlayer().incrementKillPoints(boss.getPointsOnKill());
                }
            }
        }
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

            level.getExplosionManager().playAsteroidHit(
                    new Explosion(explosionX, explosionY, Constants.EXPLOSION_SPEED, explosionSpriteSheet)
            );

            level.getPlayer().decrementLives();
        }
    }

    private void updateBossLocation(Boss boss) {
        boss.getSvgPath().setLayoutX(boss.getPositionX());
        boss.getSvgPath().setLayoutY(boss.getPositionY());
    }
}