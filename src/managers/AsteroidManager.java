package managers;

import entities.*;
import entities.enemies.Asteroid;
import entities.level.Level;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class AsteroidManager {
    //TODO Asteroid class must only have fields, getters and setters. All other methods must be managed by the AsteroidManager class!

    //Initializing Asteroids
    //Checking Asteroid Collisions with player or missile and add explosion to List<Explosion>
    //Must implement all methods from Asteroid class
    private PlayerManager playerManager;
    private FuelManager fuelManager;
    private Random rnd;

    public AsteroidManager(PlayerManager playerManager, FuelManager fuelManager) {
        this.setPlayerManager(playerManager);
        this.setFuelManager(fuelManager);
        this.rnd = new Random();
    }

    private PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    private void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    private FuelManager getFuelManager() {
        return this.fuelManager;
    }

    private void setFuelManager(FuelManager fuelManager) {
        this.fuelManager = fuelManager;
    }

    public ArrayList<Asteroid> initializeAsteroids(Canvas canvas, int health, int asteroidCount) {
        ArrayList<Asteroid> asteroids = new ArrayList<>();

        for (int i = 0; i < asteroidCount; i++) {
            Asteroid currentAsteroid = new Asteroid(Constants.ASTEROID_SPEED, health, health);

            Image image = new Image(Constants.ASTEROID_IMAGE +
                    String.valueOf(SpawnCoordinates.getRandom(4)) + ".png");

            currentAsteroid.setImage(image);
            currentAsteroid.setPosition(SpawnCoordinates.getSpawnX(canvas),
                    SpawnCoordinates.getSpawnY(canvas));

            asteroids.add(currentAsteroid);
        }
        return asteroids;
    }

    private void updateAsteroidLocation(Level level, Asteroid asteroid, int health) {
        //Offset so that asteroids don't spawn outside of boundaries
        double heightOffset = 37;

        //Offset Formula
        double offset = level.getCanvas().getHeight() - heightOffset;

        asteroid.setPositionX(asteroid.getPositionX() - asteroid.getSpeed());

        if (asteroid.getPositionX() < -20 && !level.isActiveBoss()) {
            asteroid.setPositionX(level.getCanvas().getWidth());
            asteroid.setPositionY(this.rnd.nextInt((int) offset));
            asteroid.setHealth(health);
        }
    }

    public void manageAsteroids(Level level, AnimationTimer timer) {
        //Add explosion to list
        //Iterate through all asteroids
        for (Asteroid asteroidToRenderAndUpdate : level.getAsteroids()) {
            if (asteroidToRenderAndUpdate.getHealth() > 0) {
                asteroidToRenderAndUpdate.render(level.getGc());
                this.manageAsteroidCollision(level, asteroidToRenderAndUpdate, timer, asteroidToRenderAndUpdate.getDefaultHealth());
            }
            //Asteroid speed updating every rotation making them faster:
            asteroidToRenderAndUpdate.setSpeed(asteroidToRenderAndUpdate.getSpeed() +
                    Constants.OBJECT_SPEED_UP_VALUE);
            this.updateAsteroidLocation(level, asteroidToRenderAndUpdate, asteroidToRenderAndUpdate.getDefaultHealth());
        }
    }

    private void manageAsteroidCollision(Level level, Asteroid asteroid, AnimationTimer timer, int health) {
        if (this.getPlayerManager().checkCollision(asteroid)) {

            EffectsManager.playAsteroidHit(new Explosion(asteroid.getPositionX(), asteroid.getPositionY()));

            asteroid.setPositionX(-1300);
            asteroid.setHealth(health);
            this.getPlayerManager().resetPlayerPosition(level.getCanvas(), this.getFuelManager());
            this.getPlayerManager().playerHit();

            level.getPlayer().setLives(level.getPlayer().getLives() - 1);

            try {
                this.getPlayerManager().checkIfPlayerIsDead(level, timer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Asteroid> resetAsteroidPosition(ArrayList<Asteroid> asteroids, Canvas canvas) {
        ArrayList<Asteroid> asteroidsToReturn = asteroids;

        for (Asteroid asteroidToUpdate : asteroidsToReturn) {
            asteroidToUpdate.setPosition(SpawnCoordinates.getSpawnX(canvas), SpawnCoordinates.getSpawnY(canvas));
        }

        return asteroidsToReturn;
    }
}