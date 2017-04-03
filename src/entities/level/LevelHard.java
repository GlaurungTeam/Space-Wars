package entities.level;

import entities.Constants;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class LevelHard extends Level {
    @Override
    public void setDifficultyParameters() {
        super.initializeEnemies(Constants.ASTEROID_HEALTH_HARD, Constants.ASTEROID_SPEED_HARD, Constants.ASTEROID_COUNT_HARD,
                Constants.UFO_HEALTH_HARD, Constants.UFO_SPEED_HARD, Constants.UFO_COUNT_HARD);

        try {
            super.initializeBosses();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        super.setBackgroundImage(new Image(Constants.HARD_LEVEL_BACKGROUND));
    }
}