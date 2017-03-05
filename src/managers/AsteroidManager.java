package managers;

import entities.Explosion;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import entities.Asteroid;
import entities.Level;
import entities.SpawnCoordinates;

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

    public ArrayList<Asteroid> initializeAsteroids(Canvas canvas, int health) {
        ArrayList<Asteroid> asteroids = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Asteroid currentAsteroid = new Asteroid(2.5, health);
            String path = "resources/asteroid/asteroid" +
                    String.valueOf(SpawnCoordinates.getRandom(4)) + ".png";
            Image image = new Image(path);

            currentAsteroid.setImage(image);
            currentAsteroid.setPosition(SpawnCoordinates.getSpawnX(canvas),
                    SpawnCoordinates.getSpawnY(canvas), 2.5);

            asteroids.add(currentAsteroid);
        }
        return asteroids;
    }

    private void updateAsteroidLocation(Canvas canvas, Asteroid asteroid, int health) {
        //Offset so that asteroids don't spawn outside of boundaries
        double heightOffset = 37;

        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        asteroid.setPositionX(asteroid.getPositionX() - asteroid.getSpeed());

        if (asteroid.getPositionX() < -20) {
            asteroid.setPositionX(canvas.getWidth());
            asteroid.setPositionY(rnd.nextInt((int) offset));
            asteroid.setHealth(health);
        }
    }

    public void manageAsteroids(Level level, AnimationTimer timer, int health) {
        //TODO Iterate through all asteroids and remove the asteroid that was hit and/or remove the missile that was hit

        //Add explosion to list
        //Iterate through all asteroids
        for (Asteroid asteroidToRenderAndUpdate : level.getAsteroids()) {
            if (asteroidToRenderAndUpdate.getHealth() > 0) {
                asteroidToRenderAndUpdate.render(level.getGc());
                this.manageAsteroidCollision(level, asteroidToRenderAndUpdate, timer, health);
            }
            //Asteroid speed updating every rotation making them faster:
            asteroidToRenderAndUpdate.setSpeed(asteroidToRenderAndUpdate.getSpeed() + 0.00002);
            this.updateAsteroidLocation(level.getCanvas(), asteroidToRenderAndUpdate, health);
        }
    }

    private void manageAsteroidCollision(Level level, Asteroid asteroid, AnimationTimer timer, int health) {
        if (this.getPlayerManager().checkCollision(asteroid.getPositionX(),
                asteroid.getPositionY(), 32)) {

            EffectsManager.playAsteroidHit(new Explosion(asteroid.getPositionX(), asteroid.getPositionY()));

            asteroid.setPositionX(-1300);
            asteroid.setHealth(health);
            this.getPlayerManager().resetPlayerPosition(level.getCanvas(), this.getFuelManager());

            level.getPlayer().setLives(level.getPlayer().getLives() - 1);

            try {
                this.getPlayerManager().checkIfPlayerIsDead(level, timer);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //TODO Change color of ship when hit, or some kind of visual effect
            //TODO Implement ship damage tracker
        }
    }
}