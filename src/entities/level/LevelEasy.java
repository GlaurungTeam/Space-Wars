package entities.level;

import entities.Constants;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class LevelEasy extends Level {
    @Override
    public void setDifficultyParameters() {
        super.initializeEnemies(Constants.ASTEROID_HEALTH_EASY, Constants.ASTEROID_COUNT_EASY,
                Constants.UFO_HEALTH_EASY, Constants.UFO_COUNT_EASY);

        try {
            super.initializeBosses();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        super.setBackgroundImage(new Image(Constants.EASY_LEVEL_BACKGROUND));
    }
}