package entities;

import javafx.scene.canvas.Canvas;

import java.util.Random;

public class SpawnCoordinates {
    //Method class for getting random X and Y coordinates for initial spawning of various objects
    static double heightOffset = 37;
    static Random rnd = new Random();
    static Random rndX = new Random();
    static Random rndY = new Random();

    public static int getRandom(int number) {
        return rnd.nextInt(number);
    }

    public static double getSpawnX(Canvas canvas) {
        return canvas.getWidth() + rndX.nextInt((int) canvas.getWidth());
    }

    public static double getSpawnY(Canvas canvas) {
        //Offset so that objects don't spawn out of boundaries
        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;
        return rndY.nextInt((int) offset);
    }
}