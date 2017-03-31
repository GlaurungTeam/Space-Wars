package entities.level;

import entities.Constants;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public class LevelEasy extends Level {
    @Override
    public void setDifficultyParameters() {
        super.initializeEnemies();
        try {
            super.initializeBosses();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        super.setBackgroundImage(new Image(Constants.EASY_LEVEL_BACKGROUND));
    }
}