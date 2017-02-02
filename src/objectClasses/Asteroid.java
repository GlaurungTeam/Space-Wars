package objectClasses;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;


public class Asteroid {
    public double y;
    public double x;
    public double speed;

    //offset so that asteroids dont spawn outside of boundries
    double heightOffset = 37;

    public Asteroid(Image[] arr, double duration, GraphicsContext gc, double frame, double x, double y, double speed) {
        AnimatedImage object = new AnimatedImage();
        object.frames = arr;
        object.duration = duration;
        this.y = y;
        this.x = x;
        this.speed = speed;
    }


    public void drawAsteroid(GraphicsContext gc, AnimatedImage asteroid, double frame, double x, double y) {
        gc.drawImage(asteroid.getFrame(frame), x, y);
    }

    public void updateAsteroid(Canvas canvas, Asteroid asteroid, int speed) {
        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        asteroid.x -= speed;

        if (asteroid.x < -20) {
            asteroid.x = canvas.getWidth();
            asteroid.y = rnd.nextInt((int) offset);
        }
    }
}