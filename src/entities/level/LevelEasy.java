package entities.level;

import entities.Constants;

public class LevelEasy extends Level {
    @Override
    public void setDifficultyParameters() {
        super.initializeAsteroids(Constants.ASTEROID_HEALTH_EASY, Constants.ASTEROIDS_COUNT_EASY);
        super.initializeUfos(Constants.UFO_COUNT_EASY, Constants.UFO_SPEED_EASY);
    }
}