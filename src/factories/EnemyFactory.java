package factories;

import helpers.NumberRandomizer;
import models.enemies.Asteroid;
import models.enemies.Ufo;
import models.gameObjects.HealthableGameObject;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnemyFactory {
    private NumberRandomizer randomizer;

    public EnemyFactory() {
        this.randomizer = new NumberRandomizer();
    }

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


    private HealthableGameObject createAsteroid(int asteroidHealth, int asteroidSpeed) {
        BufferedImage asteroidSpritesheet = null;

        String path = Constants.PROJECT_PATH + Constants.ASTEROID_IMAGE +
                String.valueOf(this.randomizer.getRandomNumber(4)) + ".png";

        File sprites = new File(path);

        try {
            asteroidSpritesheet = ImageIO.read(sprites);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int currentXPos = this.randomizer.getRandomNumber(Constants.SCREEN_WIDTH, Constants.SCREEN_WIDTH * 2);
        int currentYPos = this.randomizer.getRandomNumber(0, Constants.SCREEN_HEIGHT);

        HealthableGameObject asteroid = new Asteroid(currentXPos, currentYPos,
                asteroidSpeed, asteroidSpritesheet, asteroidHealth, asteroidHealth);

        return asteroid;
    }


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

        int currentXPos = this.randomizer.getRandomNumber(lowBound, highBound);
        int currentYPos = this.randomizer.getRandomNumber(Constants.SCREEN_HEIGHT);

        HealthableGameObject ufo = new Ufo(currentXPos, currentYPos, ufoSpeed, ufoSpriteSheet, ufoHealth, ufoHealth);

        return ufo;
    }

}
