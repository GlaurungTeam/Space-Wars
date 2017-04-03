package managers;

import entities.*;
import entities.enemies.Asteroid;
import entities.level.Level;
import javafx.scene.canvas.Canvas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Deprecated
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

    public List<Asteroid> initializeAsteroids(Canvas canvas, int health, int asteroidCount) {
        List<Asteroid> asteroids = new ArrayList<>();

        for (int i = 0; i < asteroidCount; i++) {
            BufferedImage asteroidSpritesheet = null;

            Random rnd = new Random();

            String path = Constants.PROJECT_PATH + Constants.ASTEROID_IMAGE +
                    String.valueOf(rnd.nextInt(4)) + ".png";

            File sprites = new File(path);

            try {
                asteroidSpritesheet = ImageIO.read(sprites);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            int currentXPos = (int)SpawnCoordinates.getSpawnX(canvas);
//            int currentYPos = (int)SpawnCoordinates.getSpawnY(canvas);
//
//            Asteroid currentAsteroid = new Asteroid(currentXPos,currentYPos,
//                    Constants.ASTEROID_SPEED_EASY, asteroidSpritesheet, health, health);

            //currentAsteroid.setImage(asteroidSpritesheet.getSubimage(currentXPos, currentYPos, asteroidWidth, asteroidHeight));

//            asteroids.add(currentAsteroid);
        }
        return asteroids;
    }

    private void move(Level level, Asteroid asteroid, int health) {
        //Offset so that asteroids don't spawn outside of boundaries
        double heightOffset = 37;
        //Offset Formula
        double offset = level.getCanvas().getHeight() - heightOffset;

        asteroid.updateLocation(asteroid.getPositionX() - asteroid.getSpeed(), asteroid.getPositionY());

        if (asteroid.getPositionX() < -20 && !level.isActiveBoss()) {
            asteroid.updateLocation(level.getCanvas().getWidth(), this.rnd.nextInt((int) offset));
            asteroid.setHealth(health);
        }
    }

    /*public void manageAsteroids(Level level) {
        //Add explosion to list
        //Iterate through all asteroids
        for (Asteroid asteroidToRenderAndUpdate : level.getAsteroids()) {
            if (asteroidToRenderAndUpdate.getHealth() > 0) {
                Image currentFrame = asteroidToRenderAndUpdate.getCurrentAsteroidFrame(0);
                asteroidToRenderAndUpdate.setImage(currentFrame);
                asteroidToRenderAndUpdate.render(level.getGc());
                this.manageAsteroidCollision(level, asteroidToRenderAndUpdate, asteroidToRenderAndUpdate.getDefaultHealth());
            }
            //Asteroid speed updating every rotation making them faster:
            asteroidToRenderAndUpdate.speedUp(Constants.OBJECT_SPEED_UP_VALUE);
            this.move(level, asteroidToRenderAndUpdate, asteroidToRenderAndUpdate.getDefaultHealth());
        }
    }*/

    private void manageAsteroidCollision(Level level, Asteroid asteroid, int health) {
        if (this.getPlayerManager().checkCollision(asteroid)) {

            BufferedImage explosionSpriteSheet = null;

            try {
                explosionSpriteSheet = ImageIO.read(new File(
                        Constants.PROJECT_PATH + Constants.EXPLOSION_SPRITESHEET_IMAGE));
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            EffectsManager.playAsteroidHit(new Explosion(asteroid.getPositionX(), asteroid.getPositionY(),
                    Constants.EXPLOSION_SPEED, explosionSpriteSheet));

            asteroid.updateLocation(-1300, asteroid.getPositionY());
            asteroid.setHealth(health);
            this.getPlayerManager().resetPlayerPosition();
            this.getPlayerManager().playerHit();

            level.getPlayer().setLives(level.getPlayer().getLives() - 1);
        }
    }

    public List<Asteroid> resetAsteroidPosition(List<Asteroid> asteroids, Canvas canvas) {

//        for (Asteroid asteroidToUpdate : asteroids) {
//            asteroidToUpdate.setPosition(SpawnCoordinates.getSpawnX(canvas), SpawnCoordinates.getSpawnY(canvas));
//        }

        return asteroids;
    }
}