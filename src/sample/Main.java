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
import objectClasses.AsteroidSpawnCoordinates;
import objectClasses.Player;


public class Main extends Application {

    //obsolete for now
    //private Timer timer = new Timer();

    boolean goLeft, goRight, goUp, goDown;
    //variables for "reactive" speed of the player
    int timeHeld;
    public static boolean held = false;
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


        //Add event listener
        theScene.setOnKeyPressed(event -> {
            if(timeHeld < 20){
                timeHeld++;
            }
            else{
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

        //Adjustable player and asteroid speed
        int asteroidSpeed = 2;
        int playerSpeed = 2;


        //Method class for getting random X and Y coordinates for initial asteroid spawning
        AsteroidSpawnCoordinates asteroidSpawnCoordinates = new AsteroidSpawnCoordinates();

        //Initialize all asteroids
        for (int i = 0; i < asteroidArr.length; i++) {
            asteroidArr[i] = new Asteroid
                    (asteroidImageArr, 0.100, gc, 0, asteroidSpawnCoordinates.getSpawnX(canvas), asteroidSpawnCoordinates.getSpawnY(canvas), 30);
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

//                //draw UFO
//                gc.drawImage(ufo.getFrame(t), 100, 25);

                for (int i = 0; i < asteroidArr.length; i++) {
                    asteroidArr[i].drawAsteroid(gc, asteroid, 0, asteroidArr[i].x, asteroidArr[i].y);

                    //Update asteroid location
                    asteroidArr[i].updateAsteroid(canvas, asteroidArr[i], asteroidSpeed);
                }

                playerObject.drawPlayer(gc, player, 0, playerObject.x, playerObject.y);
                playerObject.updateLocation(playerObject, canvas, goUp, goDown, goLeft, goRight, playerSpeed);
            }
        }.start();

        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}