package entities.level;

import entities.Constants;
import javafx.scene.image.Image;

public class LevelHard extends Level {
    @Override
    public void setDifficultyParameters() {
        super.initializeAsteroids(Constants.ASTEROID_HEALTH_HARD, Constants.ASTEROIDS_COUNT_HARD);
        super.initializeUfos(Constants.UFO_COUNT_HARD, Constants.UFO_SPEED_HARD);
        super.initializeBosses();

        super.setBackgroundImage(new Image(Constants.HARD_LEVEL_BACKGROUND));
    }
}