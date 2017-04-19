package managers;

import models.enemies.Asteroid;
import models.enemies.Ufo;
import models.gameObjects.Explosion;
import models.gameObjects.HealthableGameObject;
import models.level.Level;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyManager {

    public List<HealthableGameObject> initializeEnemies(int enemyHealth, int enemySpeed, int enemyCount, String enemyType) {
        List<HealthableGameObject> enemiesToReturn = new ArrayList<>();

        for (int i = 0; i < enemyCount; i++) {
            HealthableGameObject enemy = null;

            switch (enemyType) {
                case "ufo":
                    enemy = createUfo(enemyHealth, enemySpeed);
                    break;
                case "asteroid":
                    enemy = createAsteroid(enemyHealth, enemySpeed);
                    break;
            }
            enemiesToReturn.add(enemy);
        }
        return enemiesToReturn;
    }

    //TODO Create EnemyFactory to create enemies!
    private HealthableGameObject createAsteroid(int asteroidHealth, int asteroidSpeed) {
        BufferedImage asteroidSpritesheet = null;

        String path = Constants.PROJECT_PATH + Constants.ASTEROID_IMAGE +
                String.valueOf(this.getRandomNumber(4)) + ".png";

        File sprites = new File(path);

        try {
            asteroidSpritesheet = ImageIO.read(sprites);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int currentXPos = this.getRandomNumber(Constants.SCREEN_WIDTH, Constants.SCREEN_WIDTH * 2);
        int currentYPos = this.getRandomNumber(0, Constants.SCREEN_HEIGHT);

        HealthableGameObject asteroid = new Asteroid(currentXPos, currentYPos,
                asteroidSpeed, asteroidSpritesheet, asteroidHealth, asteroidHealth);

        return asteroid;
    }

    //TODO Create EnemyFactory to create enemies!
    private HealthableGameObject createUfo(int ufoHealth, int ufoSpeed) {
        BufferedImage ufoSpriteSheet = null;

        try {
            ufoSpriteSheet = ImageIO.read(new File(
                    Constants.PROJECT_PATH + Constants.UFO_SPRITESHEET));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        int lowBound = Constants.SCREEN_WIDTH;
        int highBound = Constants.SCREEN_WIDTH * 2;

        int currentXPos = this.getRandomNumber(lowBound, highBound);
        int currentYPos = this.getRandomNumber(Constants.SCREEN_HEIGHT);

        HealthableGameObject ufo = new Ufo(currentXPos, currentYPos, ufoSpeed, ufoSpriteSheet, ufoHealth, ufoHealth);

        return ufo;
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

        enemy.updateLocation(Constants.SCREEN_WIDTH, this.getRandomNumber((int) offset));
        enemy.speedUp(Constants.OBJECT_SPEED_UP_VALUE);
        enemy.setHealth(enemy.getDefaultHealth());
    }

    public void manageEnemies(Level level) {
        for (HealthableGameObject enemy : level.getRealEnemies()) {
            if (!level.isActiveBoss()) {
                //TODO Here the index of the current image is hardcoded - change it
                enemy.setImage(enemy.getCurrentExplosionFrame(0));
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
        int x = this.setStartPosition("x");
        int y = this.setStartPosition("y");

        enemy.updateLocation(x, y);
    }

    private int setStartPosition(String coordinate) {
        switch (coordinate.toLowerCase()) {
            case "x":
                return this.getRandomNumber(Constants.SCREEN_WIDTH + 1,
                        Constants.SCREEN_WIDTH * 2);
            case "y":
                return this.getRandomNumber(0, Constants.SCREEN_HEIGHT);
            default:
                return 0;
        }
    }

    private int getRandomNumber(int bound) {
        Random rnd = new Random();
        return rnd.nextInt(bound);
    }

    private int getRandomNumber(int lowBound, int highBound) {
        Random rnd = new Random();
        return rnd.nextInt(highBound - lowBound) + lowBound;
    }
}