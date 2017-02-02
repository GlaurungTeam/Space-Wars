package objectClasses;

import javafx.scene.canvas.Canvas;

import java.util.Random;

public class AsteroidSpawnCoordinates {
    //Method class for getting random X and Y coordinates for initial asteroid spawning

    double heightOffset = 37;

    public double getSpawnX(Canvas canvas){

        Random rndX = new Random();
        return canvas.getWidth() + rndX.nextInt((int) canvas.getWidth());
    }

    public double getSpawnY(Canvas canvas){
        //Offset so that asteroids dont spawn out of boundries
        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        Random rndY = new Random();
        return rndY.nextInt((int) offset);
    }
}
