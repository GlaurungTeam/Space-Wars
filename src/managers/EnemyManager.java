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
    private PlayerManager playerManager;
    private FuelManager fuelManager;

    public EnemyManager(PlayerManager playerManager, FuelManager fuelManager) {
        this.setPlayerManager(playerManager);
        this.setFuelManager(fuelManager);
    }

    private PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    private void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    private FuelManager getFuelManager() {
        return this.fuelManager;
    }

    private void setFuelManager(FuelManager fuelManager) {
        this.fuelManager = fuelManager;
    }

    public List<HealthAbleGameObject> initializeEnemies(int enemyHealth, int enemyCount, String enemyType) {
        List<HealthAbleGameObject> enemiesToReturn = new ArrayList<>();

        for (int i = 0; i < enemyCount; i++) {
            HealthAbleGameObject enemy = null;
            switch (enemyType) {
                case "ufo":
                    enemy = createUfo(enemyHealth);
                    break;
                case "asteroid":
                    enemy = createAsteroid(enemyHealth);
            }
            enemiesToReturn.add(enemy);
        }
        return enemiesToReturn;
    }

    private HealthAbleGameObject createAsteroid(int enemyHealth) {//TODO create EnemyFactory to create enemies!
        BufferedImage asteroidSpritesheet = null;

        String path = Constants.PROJECT_PATH + Constants.ASTEROID_IMAGE +
                String.valueOf(SpawnCoordinates.getRandom(4)) + ".png";

        File sprites = new File(path);

        try {
            asteroidSpritesheet = ImageIO.read(sprites);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int currentXPos = this.getRandomNumber(1200, 5000);//TODO refactoring random position-spawn
        int currentYPos = this.getRandomNumber(0, 720);

        HealthAbleGameObject asteroid = new Asteroid(currentXPos, currentYPos,
                Constants.ASTEROID_SPEED, asteroidSpritesheet, enemyHealth, enemyHealth);

        return asteroid;
    }

    private HealthAbleGameObject createUfo(int ufoHealth) {//TODO create Factory for enemies
        BufferedImage ufoSpriteSheet = null;

        try {
            ufoSpriteSheet = ImageIO.read(new File(
                    Constants.PROJECT_PATH + Constants.UFO_SpriteSheet));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        int lowBound = 1300;
        int highBound = 2500;

        int currentXPos = this.getRandomNumber(lowBound, highBound);
        int currentYPos = this.getRandomNumber(720);

        HealthAbleGameObject ufo = new Ufo(currentXPos, currentYPos, Constants.UFO_SPEED_EASY, ufoSpriteSheet, ufoHealth, ufoHealth);

        return ufo;
    }

    private void manageEnemyCollision(Level level, HealthAbleGameObject enemy) {
        if (this.getPlayerManager().checkCollision(enemy)) {


            this.explodeEnemy(enemy);//TODO created

            this.getPlayerManager().resetPlayerPosition(level.getCanvas(), this.getFuelManager());
            enemy.resetLocation(-1300, enemy.getPositionY());

            level.getPlayer().setLives(level.getPlayer().getLives() - 1);
            this.getPlayerManager().playerHit();
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
        double heightOffset = 37;
        double offset = canvas.getHeight() - heightOffset;

        double nextXPosition = enemy.getPositionX() - enemy.getSpeed();

        if (enemy.getPositionX() < -20 || enemy.getHealth() == 0) {
            enemy.updateLocation(canvas.getWidth(), this.getRandomNumber((int) offset));
            enemy.speedUp(Constants.OBJECT_SPEED_UP_VALUE);//TODO crete method "resurrectEnemy()"
            enemy.setHealth(1);
            System.out.println(enemy.getType() + ": " + enemy.getHealth() + "x " + enemy.getPositionX());//TODO printing
            return;
        }

        enemy.updateLocation(nextXPosition, enemy.getPositionY());
    }

    public void manageEnemies(Level level) {
        for (HealthAbleGameObject enemy : level.getRealEnemies()) {
            if (!level.isActiveBoss()) {
                enemy.setImage(enemy.getCurrentFrame(0));//TODO here the index of the current image is hardcoded - change it
                enemy.render(level.getGc());

                this.move(enemy, level.getCanvas());
                this.manageEnemyCollision(level, enemy);
            } else if (enemy.getPositionX() > -20 && enemy.getPositionX() < 2000) {
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
                return this.getRandomNumber(2001, 5000);//TODO hardcoded values
            case "y":
                return this.getRandomNumber(0, 1080);
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