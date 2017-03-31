package entities.level;

import entities.Constants;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class LevelHard extends Level {
    @Override
    public void setDifficultyParameters() {
        super.initializeEnemies();
        try {
            super.initializeBosses();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        super.setBackgroundImage(new Image(Constants.HARD_LEVEL_BACKGROUND));
    }
}