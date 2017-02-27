package objectClasses;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

public class Asteroid extends Sprite {
    private boolean isHit;

    public Asteroid(double speed) {
        this.setSpeed(speed);
        this.setHitStatus(false);
    }

    public boolean getHitStatus() {
        return this.isHit;
    }

    public boolean setHitStatus(boolean isHit) {
        return this.isHit = isHit;
    }

    public void updateAsteroidLocation(Canvas canvas) {
        //Offset so that asteroids don't spawn outside of boundaries
        double heightOffset = 37;

        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        this.setPositionX(this.getPositionX() - this.getSpeed());

        if (this.getPositionX() < -20) {
            this.setPositionX(canvas.getWidth());
            this.setPositionY(rnd.nextInt((int) offset));
            this.setHitStatus(false);
        }
    }

    /*public static void initializeAsteroids(Asteroid[] asteroids, Canvas canvas) {
        for (int i = 0; i < asteroids.length; i++) {
            Asteroid currentAsteroid = new Asteroid(2.5);//HardCoded
            String path = "resources/asteroid/asteroid" + String.valueOf(SpawnCoordinates.getRandom(4)) + ".png";
            Image image = new Image(path);

            currentAsteroid.setImage(image);
            currentAsteroid.setPosition(SpawnCoordinates.getSpawnX(canvas), SpawnCoordinates.getSpawnY(canvas), 2.5);//HardCoded
            asteroids[i] = currentAsteroid;
        }
    }*/
}