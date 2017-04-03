package managers;

import entities.*;
import entities.enemies.Asteroid;
import entities.enemies.Ufo;
import entities.level.Level;
import javafx.scene.canvas.Canvas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyManager {

    public List<HealthAbleGameObject> initializeEnemies(int enemyHealth, int enemySpeed, int enemyCount, String enemyType) {
        List<HealthAbleGameObject> enemiesToReturn = new ArrayList<>();

        for (int i = 0; i < enemyCount; i++) {
            HealthAbleGameObject enemy = null;
            switch (enemyType) {
                case "ufo":
                    enemy = createUfo(enemyHealth, enemySpeed);
                    break;
                case "asteroid":
                    enemy = createAsteroid(enemyHealth, enemySpeed);
            }
            enemiesToReturn.add(enemy);
        }
        return enemiesToReturn;
    }

    //TODO Create EnemyFactory to create enemies!
    private HealthAbleGameObject createAsteroid(int asteroidHealth, int asteroidSpeed) {
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

        HealthAbleGameObject asteroid = new Asteroid(currentXPos, currentYPos,
                asteroidSpeed, asteroidSpritesheet, asteroidHealth, asteroidHealth);

        return asteroid;
    }

    //TODO Create Factory for enemies
    private HealthAbleGameObject createUfo(int ufoHealth, int ufoSpeed) {
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

        HealthAbleGameObject ufo = new Ufo(currentXPos, currentYPos, ufoSpeed, ufoSpriteSheet, ufoHealth, ufoHealth);

        return ufo;
    }

    private void manageEnemyCollision(Level level, HealthAbleGameObject enemy) {
        if (level.getPlayerManager().checkCollision(enemy)) {
            this.explodeEnemy(enemy);

            level.getPlayerManager().resetPlayerPosition();
            level.getFuelManager().resetFuel();

            enemy.updateLocation(Constants.OBJECT_RESTART_LEFT_COORDINATE, enemy.getPositionY());

            level.getPlayer().setLives(level.getPlayer().getLives() - 1);
            level.getPlayerManager().playerHit();
        }
    }

    private void explodeEnemy(HealthAbleGameObject enemy) {
        BufferedImage explosionSpriteSheet = null;

        try {
            explosionSpriteSheet = ImageIO.read(new File(
                    Constants.PROJECT_PATH + Constants.EXPLOSION_SPRITESHEET_IMAGE));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        EffectsManager.playAsteroidHit(new Explosion(enemy.getPositionX(), enemy.getPositionY(),
                Constants.EXPLOSION_SPEED, explosionSpriteSheet));
    }

    private void move(HealthAbleGameObject enemy, Canvas canvas) {
        double offset = canvas.getHeight() - Constants.HEIGHT_OFFSET;

        double nextXPosition = enemy.getPositionX() - enemy.getSpeed();

        if (enemy.getPositionX() < Constants.OBJECT_RESTART_LEFT_COORDINATE || enemy.getHealth() == 0) {
            enemy.updateLocation(canvas.getWidth(), this.getRandomNumber((int) offset));
            enemy.speedUp(Constants.OBJECT_SPEED_UP_VALUE);//TODO crete method "resurrectEnemy()"
            enemy.setHealth(1);
            return;
        }

        enemy.updateLocation(nextXPosition, enemy.getPositionY());
    }

    public void manageEnemies(Level level) {
        for (HealthAbleGameObject enemy : level.getRealEnemies()) {
            if (!level.isActiveBoss()) {
                //TODO Here the index of the current image is hardcoded - change it
                enemy.setImage(enemy.getCurrentFrame(0));
                enemy.render(level.getGc());

                this.move(enemy, level.getCanvas());
                this.manageEnemyCollision(level, enemy);
            } else if (enemy.getPositionX() > Constants.OBJECT_RESTART_LEFT_COORDINATE &&
                    enemy.getPositionX() < Constants.SCREEN_WIDTH) {
                this.explodeEnemy(enemy);
                this.resetEnemyLocationOnActiveBoss(enemy);
            }
        }
    }

    private void resetEnemyLocationOnActiveBoss(HealthAbleGameObject enemy) {
        int x = this.setStartPosition("x");
        int y = this.setStartPosition("y");
        enemy.updateLocation(x, y);
    }

    private int setStartPosition(String coordinate) {
        switch (coordinate.toLowerCase()) {
            case "x":
                return this.getRandomNumber(Constants.SCREEN_WIDTH + 1, Constants.SCREEN_WIDTH * 2);
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