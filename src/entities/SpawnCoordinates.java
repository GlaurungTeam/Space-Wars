package entities;

import javafx.scene.canvas.Canvas;

import java.util.Random;

@Deprecated
public class SpawnCoordinates {
    //Method class for getting random X and Y coordinates for initial spawning of various objects
    private static Random rnd = new Random();
    private static Random rndX = new Random();
    private static Random rndY = new Random();

    public static int getRandom(int number) {
        return rnd.nextInt(number);
    }

    public static double getSpawnX(Canvas canvas) {
        return canvas.getWidth() + rndX.nextInt((int) canvas.getWidth());
    }

    public static double getSpawnY(Canvas canvas) {
        //Offset formula so that objects don't spawn out of boundaries
        double offset = canvas.getHeight() - Constants.HEIGHT_OFFSET;
        return rndY.nextInt((int) offset);
    }
}