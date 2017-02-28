package Controllers;

import Entities.*;
import Managers.AsteroidManager;
import Managers.EnemyManager;
import Managers.MissileManager;
import Managers.PlayerManager;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class GameController extends Application {
    @FXML
    private int backgroundX = 0;
    private int backgroundY = 0;

    private int planetX = 500;
    private int planetY = 196;

    private ArrayList<Explosion> explosions = new ArrayList<>();

    public void addExplosions(Explosion e) {
        this.explosions.add(e);
    }

    @Override
    public void start(Stage theStage) throws Exception {
        theStage.setTitle("Space Wars");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(1280, 720);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Make new player object
        Player player = new Player(3.0, 3, theScene);
        PlayerManager playerManager = new PlayerManager(player);
        EnemyManager enemyManager = new EnemyManager(playerManager);
        AsteroidManager asteroidManager = new AsteroidManager(playerManager);
        MissileManager missileManager = new MissileManager();

        //Initialize objects
        ArrayList<Ufo> ufos = enemyManager.initializeUfos(canvas);
        ArrayList<Asteroid> asteroids = asteroidManager.initializeAsteroids(canvas);

        //Make Level object
        Level level1 = new Level
                (root, player, gc, canvas, this, theScene, 0.0, ufos, asteroids);

        //The shiny PlayerManager class :D
        playerManager.getFirePermition();
        playerManager.initializePlayerControls(theScene, level1);

        //Add hitboxes to canvas//
        root.getChildren().addAll(player.getR(), player.getRv(), player.getRv2());



        Text scoreLine = new Text(20, 30, "");
        root.getChildren().add(scoreLine);
        scoreLine.setFont(Font.font("Verdana", 20));
        scoreLine.setFill(Color.WHITE);
        String score = String.format("Score: %d", player.getPoints());
        scoreLine.setText(score);

        Text lives = new Text(20, 50, "");
        root.getChildren().add(lives);
        lives.setFont(Font.font("Verdana", 20));
        lives.setFill(Color.WHITE);
        String livesNumber = String.format("Lives: %d", player.getLives());
        lives.setText(livesNumber);

        Image earth = new Image("Resources/earth.png");
        Image sun = new Image("Resources/sun.png");
        Image space = new Image("Resources/space.png");

        //FuelBar
        int totalFuel = 50;
        String FUEL_BURNED_FORMAT = "%.0f";
        ReadOnlyDoubleWrapper workDone = new ReadOnlyDoubleWrapper();

        FuelBar bar = new FuelBar(
                workDone.getReadOnlyProperty(),
                totalFuel,
                FUEL_BURNED_FORMAT
        );

        Timeline countDown = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(workDone, totalFuel)),
                new KeyFrame(Duration.seconds(30), new KeyValue(workDone, 0))
        );

        countDown.play();

        VBox layout = new VBox(20);
        layout.setLayoutX(80);
        layout.setLayoutY(60);
        layout.getChildren().addAll(bar);
        root.getChildren().add(layout);

        //FuelBar Text
        Text fuelBarText = new Text(20, 80, "Fuel: ");
        root.getChildren().add(fuelBarText);
        fuelBarText.setFont(Font.font("Verdana", 20));
        fuelBarText.setFill(Color.WHITE);

        //Adjustable speeds
        double backGroundSpeed = 1.5;
        double fuelSpeed = 1;

        //Load sprites from file
        BufferedImage playerSpriteSheet =
                ImageIO.read(new File(MenuController.PROJECT_PATH +
                        "/src/Resources/spaceship/spaceshipSprites4.png"));
        player.setSpriteParameters(82, 82, 2, 3);
        player.loadSpriteSheet(playerSpriteSheet);
        player.splitSprites();
        player.setPosition(100, canvas.getHeight() / 2, player.getSpeed());

        //Disable and enable shooting if space key is pressed(ship can shoot in 1sec delay)
        /*timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                player.fired = false;
            }
        }, 0, 1000);*/

        FuelCan fuelCan = new FuelCan(canvas, fuelSpeed);

        final long startNanoTime = System.nanoTime();

        //The main game loop begins below
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                //The 4 rows below are used to help make the earth move around the sun
                //No need to understand it

                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                double earthX = planetX + 36 + 128 * Math.cos(t);
                double earthY = 232 + 128 * Math.sin(t);

                /*if (KeyListener.held) {
                    countDown.setRate(3);
                } else {
                    countDown.setRate(1);
                }*/

                //Update background, planet and earth location
                backgroundX -= backGroundSpeed;
                planetX -= backGroundSpeed - 0.5;

                //Check fuel
                if (bar.getWorkDone().getValue() == 0.0) {
                    level1.getPlayer().setLives(level1.getPlayer().getLives() - 1);
                    playerManager.resetPlayerPosition(canvas);
                    countDown.playFromStart();

                    try {
                        level1.checkIfPlayerIsDead(theScene, this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String livesC = String.format("Lives: %d", player.getLives());
                    lives.setText(livesC);
                }

                if (backgroundX < -1280) {
                    backgroundX = 0;
                }
                if (planetX < -1280) {
                    planetX = (int) canvas.getWidth() + 50;
                }

                //Background image clears canvas
                gc.drawImage(space, backgroundX, backgroundY);
                gc.drawImage(earth, earthX, earthY);
                gc.drawImage(sun, planetX, planetY);

                if (!fuelCan.getTakenStatus()) {
                    fuelCan.render(gc);
                }

                if (!fuelCan.getTakenStatus() &&
                        playerManager.checkCollision(fuelCan.getPositionX(), fuelCan.getPositionY(), 45)) {

                    countDown.playFromStart();
                    fuelCan.setTakenStatus(true);
                }
                fuelCan.updateFuelCanLocation(canvas);

                level1.setCurrentFrame(t);

                //Here we are using our shiny new managers! :)
                enemyManager.manageUfos(level1, this);
                asteroidManager.manageAsteroids(level1, this);
                missileManager.manageMissiles(level1);
                playerManager.updatePlayerLocation(canvas);

                level1.manageExplosions();
                player.setImage(player.getFrame(player.getSprites(), t, 0.100));
                player.render(gc);

            }
        };
        timer.start();

        theStage.show();
    }
}