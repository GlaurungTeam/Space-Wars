package models.level;

import utils.Constants;
import javafx.scene.image.Image;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class LevelHard extends BaseLevel {

    private static final String BOSS_TYPE = "GrumpyCat";

    @Override
    public void setDifficultyParameters() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        super.initializeEnemies(
                Constants.ASTEROID_HEALTH_HARD,
                Constants.ASTEROID_SPEED_HARD,
                Constants.ASTEROID_COUNT_HARD,
                Constants.UFO_HEALTH_HARD,
                Constants.UFO_SPEED_HARD,
                Constants.UFO_COUNT_HARD);

        try {
            super.initializeBoss(BOSS_TYPE);
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException | IOException e) {
            e.printStackTrace();
        }

        super.setBackgroundImage(new Image(Constants.HARD_LEVEL_BACKGROUND));
    }
}