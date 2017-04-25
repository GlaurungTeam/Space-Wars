package factories;

import contracts.HealthableGameObject;
import helpers.NumberRandomizer;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EnemyFactory {

    private static final int MULTIPLIER = 2;

    private NumberRandomizer randomizer;

    public EnemyFactory() {
        this.randomizer = new NumberRandomizer();
    }

    @SuppressWarnings("unchecked")
    public List<HealthableGameObject> createEnemies
            (int enemyHealth, int enemySpeed, int enemyCount, String enemyType, String enemySpritesheet) throws ClassNotFoundException, IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<HealthableGameObject> enemiesToReturn = new ArrayList<>();

        Class<? extends HealthableGameObject> enemyClass =
                (Class<? extends HealthableGameObject>) Class.forName(Constants.ENEMIES_PACKAGE + enemyType);
        Constructor<? extends HealthableGameObject> constructor =
                (Constructor<? extends HealthableGameObject>) enemyClass.getDeclaredConstructors()[0];

        int lowBound = Constants.SCREEN_WIDTH;
        int highBound = Constants.SCREEN_WIDTH * MULTIPLIER;

        BufferedImage spritesheet = ImageIO.read(new File(Constants.PROJECT_PATH + enemySpritesheet));

        for (int i = 0; i < enemyCount; i++) {
            int currentXPos = this.randomizer.getRandomNumber(lowBound, highBound);
            int currentYPos = this.randomizer.getRandomNumber(Constants.SCREEN_HEIGHT);

            HealthableGameObject enemy = constructor.newInstance(
                    currentXPos,
                    currentYPos,
                    enemySpeed,
                    spritesheet,
                    enemyHealth,
                    enemyHealth
            );
            enemiesToReturn.add(enemy);
        }
        return enemiesToReturn;
    }
}