package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objectClasses.AnimatedImage;
import objectClasses.Asteroid;
import objectClasses.Player;

import java.util.Random;
import java.util.Timer;


public class Main extends Application {

    //obsolete for now
    //private Timer timer = new Timer();

    @Override
    public void start(Stage theStage) throws Exception {
        theStage.setTitle("Space Wars");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(1280, 720);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image earth = new Image("resources/earth.png");
        Image sun = new Image("resources/sun.png");
        Image space = new Image("resources/space.png");

        Random rndY = new Random();
        Random rndX = new Random();

        //UFO object
        AnimatedImage ufo = new AnimatedImage();
        Image[] imageArray = new Image[6];
        for (int i = 0; i < 6; i++) {
            imageArray[i] = new Image("resources/ufo_" + i + ".png");
        }
        ufo.frames = imageArray;
        ufo.duration = 0.100;

        //Asteroid object
        AnimatedImage asteroid = new AnimatedImage();
        Image[] asteroidImageArr = new Image[1];
        asteroidImageArr[0] = new Image("resources/asteroid/asteroid1.png");
        asteroid.frames = asteroidImageArr;
        asteroid.duration = 0.100;
        Asteroid[] asteroidArr = new Asteroid[20];
        int speed = 2;

        //Initialize all asteroids
        for (int i = 0; i < asteroidArr.length; i++) {
            asteroidArr[i] = new Asteroid
                    (asteroidImageArr, 0.100, gc, 0, rndX.nextInt((int) canvas.getWidth()) + 750, rndY.nextInt((int) canvas.getHeight()), 30);
        }

        //Player object
        AnimatedImage player = new AnimatedImage();
        Image[] playerImageArr = new Image[1];
        playerImageArr[0] = new Image("resources/spaceship/spaceship1.png");
        player.frames = playerImageArr;
        player.duration = 0.100;

        //Initialize player object
        Player playerObject = new Player
                (playerImageArr, 0.100, gc, 0, 100, canvas.getHeight() / 2);

        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);
                //background image clears canvas
                gc.drawImage(space, 0, 0);
                gc.drawImage(earth, x, y);
                gc.drawImage(sun, 196, 196);
                //draw UFO
                gc.drawImage(ufo.getFrame(t), 100, 25);

                for (int i = 0; i < asteroidArr.length; i++) {
                    asteroidArr[i].drawAsteroid(gc, asteroid, 0, asteroidArr[i].x, asteroidArr[i].y);

                    //Update asteroid location
                    asteroidArr[i].updateAsteroid(canvas, asteroidArr[i], speed, rndY);
                }

                playerObject.drawPlayer(gc, player, 0, playerObject.x, playerObject.y);
            }
        }.start();

        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}