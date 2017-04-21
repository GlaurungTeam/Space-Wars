package factories;

import helpers.NumberRandomizer;
import models.enemies.Asteroid;
import models.enemies.Ufo;
import contracts.HealthableGameObject;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnemyFactory {
    private static final String PNG_SUFFIX = ".png";
    private static final String UFO_TYPE = "ufo";
    private static final String ASTEROID_TYPE = "asteroid";

    private static final int ASTEROID_TYPES_COUNT = 4;
    private static final int MULTIPLIER = 2;
    private static final int LOW_Y_POSITION_BOUND = 0;

    private NumberRandomizer randomizer;

    public EnemyFactory() {
        this.randomizer = new NumberRandomizer();
    }

    public List<HealthableGameObject> initializeEnemies(int enemyHealth, int enemySpeed, int enemyCount, String enemyType) {
        List<HealthableGameObject> enemiesToReturn = new ArrayList<>();

        for (int i = 0; i < enemyCount; i++) {
            HealthableGameObject enemy = null;

            switch (enemyType) {
                case UFO_TYPE:
                    enemy = createUfo(enemyHealth, enemySpeed);
                    break;
                case ASTEROID_TYPE:
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
                String.valueOf(this.randomizer.getRandomNumber(ASTEROID_TYPES_COUNT)) + PNG_SUFFIX;

        File sprites = new File(path);

        try {
            asteroidSpritesheet = ImageIO.read(sprites);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int currentXPos = this.randomizer.getRandomNumber(Constants.SCREEN_WIDTH, Constants.SCREEN_WIDTH * MULTIPLIER);
        int currentYPos = this.randomizer.getRandomNumber(LOW_Y_POSITION_BOUND, Constants.SCREEN_HEIGHT);

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
        int highBound = Constants.SCREEN_WIDTH * MULTIPLIER;

        int currentXPos = this.randomizer.getRandomNumber(lowBound, highBound);
        int currentYPos = this.randomizer.getRandomNumber(Constants.SCREEN_HEIGHT);

        HealthableGameObject ufo = new Ufo(currentXPos, currentYPos, ufoSpeed, ufoSpriteSheet, ufoHealth, ufoHealth);

        return ufo;
    }

}
