package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objectClasses.Asteroid;
import objectClasses.AsteroidSpawnCoordinates;
import objectClasses.Missile;
import objectClasses.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    //private final static String path = "/src/resources/input.txt";


    //Variables for the direction in which the player is moving at the current time
    boolean goLeft, goRight, goUp, goDown;

    //Variables for "reactive" speed of the player
    int timeHeld;
    public static boolean held = false;

    //time elapsed
    private Timer timer = new Timer();
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


        //Adjustable player and asteroid speed
        int asteroidSpeed = 2;
        double playerSpeed = 2;
        //Make new player object
        Player player = new Player();
        //load sprites from file
        BufferedImage playerSpriteSheet = ImageIO.read(new File(Controller.PROJECT_PATH + "\\src\\resources\\spaceship\\spaceshipSprites4.png"));
        player.setSpriteParameters(82, 82, 2, 3);
        player.loadSpriteSheet(playerSpriteSheet);
        player.splitSprites();
        player.setPosition(100, canvas.getHeight() / 2, playerSpeed);


        //disable and enable shooting if space key is pressed(ship can shoot in 1sec delay)
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                player.fired = false;
            }
        }, 0, 1000);

        //Add event listener
        theScene.setOnKeyPressed(event -> {
            if (timeHeld < 20) {
                timeHeld++;
            } else {
                held = true;
            }
            switch (event.getCode()) {
                case UP:
                    goUp = true;
                    break;
                case DOWN:
                    goDown = true;
                    break;
                case LEFT:
                    goLeft = true;
                    break;
                case RIGHT:
                    goRight = true;
                    break;
                case SPACE:
                    player.fire();
                    break;
            }
        });

        theScene.setOnKeyReleased(event -> {
            timeHeld = 0;
            held = false;
            switch (event.getCode()) {
                case UP:
                    goUp = false;
                    break;
                case DOWN:
                    goDown = false;
                    break;
                case LEFT:
                    goLeft = false;
                    break;
                case RIGHT:
                    goRight = false;
                    break;
            }
        });

        //Make an array holding all asteroids
        Asteroid[] asteroids = new Asteroid[20];


        //experimental asteroid animation
//        BufferedImage asteroidSpriteSheet = ImageIO.read(new File(Controller.PROJECT_PATH + "\\src\\resources\\asteroid\\asteroids1.png"));
//        Asteroid.loadAsteroidSpriteSheet(asteroidSpriteSheet);
//        Asteroid.splitAsteroidSprites(1, 4, 35, 35);

        //Initialize all asteroids
        for (int i = 0; i < asteroids.length; i++) {
            Asteroid currentAsteroid = new Asteroid();
            String path = "resources\\asteroid\\asteroid" + String.valueOf(AsteroidSpawnCoordinates.getRandom(4)) + ".png";
            Image image = new Image(path);

            currentAsteroid.setImage(image);
            currentAsteroid.setPosition(AsteroidSpawnCoordinates.getSpawnX(canvas), AsteroidSpawnCoordinates.getSpawnY(canvas), asteroidSpeed);
            asteroids[i] = currentAsteroid;
        }

        final long startNanoTime = System.nanoTime();
        //The main game loop begins below
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                //The 4 rows below are used to help make the earth move around the sun
                //No need to understand it
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                //Background image clears canvas
                gc.drawImage(space, 0, 0);
                gc.drawImage(earth, x, y);
                gc.drawImage(sun, 196, 196);

                //Iterate through all asteroids
                for (Asteroid asteroidToRenderAndUpdate : asteroids) {

                    asteroidToRenderAndUpdate.render(gc);

                    //Asteroid speed updating every rotation making them faster:
                    asteroidToRenderAndUpdate.speed += 0.00001;

                    asteroidToRenderAndUpdate.updateAsteroidLocation(canvas);
                }

                player.setImage(player.getFrame(player.sprites, t, 0.100));
                player.render(gc);
                player.updatePlayerLocation(canvas, goUp, goDown, goLeft, goRight);

                if (player.missiles.size() != 0) {
                    for (Missile m : player.missiles) {
                        m.setImage(m.getFrame(m.sprites, t, 0.100));
                        m.render(gc);
                        m.updateMissileLocation();
                    }
                }

            }
        }.start();

        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}