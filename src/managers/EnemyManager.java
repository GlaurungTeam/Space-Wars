package managers;

import factories.EnemyFactory;
import helpers.NumberRandomizer;
import models.gameObjects.Explosion;
import contracts.HealthableGameObject;
import contracts.Level;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EnemyManager {

    private static final int LOW_SCREEN_BOUND = 0;
    private static final int SCREEN_WIDTH_MULTIPLIER = 2;
    private static final int SCREEN_WIDTH_INCREMENT_VALUE = 1;
    private static final String X_PLANE = "x";
    private static final String Y_PLANE = "y";

    private NumberRandomizer randomizer;
    private EnemyFactory enemyFactory;

    public EnemyManager() {
        this.randomizer = new NumberRandomizer();
        this.enemyFactory = new EnemyFactory();
    }

    private void manageEnemyCollision(Level level, HealthableGameObject enemy) {
        if (level.getPlayerManager().checkCollision(enemy)) {
            this.explodeEnemy(level, enemy);

            level.getPlayerManager().resetPlayerPosition();
            level.getFuelManager().resetFuel();

            enemy.updateLocation(Constants.OBJECT_RESTART_LEFT_COORDINATE, enemy.getPositionY());

            level.getPlayer().decrementLives();
            level.getPlayerManager().playerHit();
        }
    }

    private void explodeEnemy(Level level, HealthableGameObject enemy) {
        BufferedImage explosionSpriteSheet = null;

        try {
            explosionSpriteSheet = ImageIO.read(new File(
                    Constants.PROJECT_PATH + Constants.EXPLOSION_SPRITESHEET_IMAGE));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        level.getExplosionManager().playAsteroidHit(
                new Explosion(enemy.getPositionX(), enemy.getPositionY(),
                        Constants.EXPLOSION_SPEED, explosionSpriteSheet)
        );
    }

    private void move(HealthableGameObject enemy) {
        double nextXPosition = enemy.getPositionX() - enemy.getSpeed();

        if (enemy.getPositionX() < Constants.OBJECT_RESTART_LEFT_COORDINATE || enemy.getHealth() == 0) {
            this.resurrectEnemy(enemy);
            return;
        }

        enemy.updateLocation(nextXPosition, enemy.getPositionY());
    }

    private void resurrectEnemy(HealthableGameObject enemy) {
        double offset = Constants.SCREEN_HEIGHT - Constants.HEIGHT_OFFSET;

        enemy.updateLocation(Constants.SCREEN_WIDTH, this.randomizer.getRandomNumber((int) offset));
        enemy.speedUp(Constants.OBJECT_SPEED_UP_VALUE);
        enemy.revive();
    }

    public EnemyFactory getEnemyFactory() {
        return this.enemyFactory;
    }

    public void manageEnemies(Level level) {
        for (HealthableGameObject enemy : level.getEnemies()) {
            if (!level.isActiveBoss()) {
                enemy.render(level.getGc());
                this.move(enemy);
                this.manageEnemyCollision(level, enemy);
            } else if (enemy.getPositionX() > Constants.OBJECT_RESTART_LEFT_COORDINATE &&
                    enemy.getPositionX() < Constants.SCREEN_WIDTH) {
                this.explodeEnemy(level, enemy);
                this.resetEnemyLocationOnActiveBoss(enemy);
            }
        }
    }

    private void resetEnemyLocationOnActiveBoss(HealthableGameObject enemy) {
        int x = this.setStartPosition(X_PLANE);
        int y = this.setStartPosition(Y_PLANE);

        enemy.updateLocation(x, y);
    }

    private int setStartPosition(String coordinate) {
        switch (coordinate.toLowerCase()) {
            case X_PLANE:
                return this.randomizer.getRandomNumber(Constants.SCREEN_WIDTH + SCREEN_WIDTH_INCREMENT_VALUE,
                        Constants.SCREEN_WIDTH * SCREEN_WIDTH_MULTIPLIER);
            case Y_PLANE:
                return this.randomizer.getRandomNumber(LOW_SCREEN_BOUND, Constants.SCREEN_HEIGHT);
            default:
                return LOW_SCREEN_BOUND;
        }
    }
}