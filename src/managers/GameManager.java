package managers;

import entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameManager extends Application {
    //TODO Logic from GameController is moved here(initializes Canvas, adds hitboxes, initializes fuelbar, etc...)

    //Implements start() method which initializes all other managers
    //Implements run() method where the game loop will be
    //Implements stop() method
    //Implements updateScore() method
    //Implements updateLevel() method which updates background, planet and earth location
    //Implements checkIfPlayerIsDead()
    //Implements writeInLeaderboard()

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
        Player player = new Player(3.0, 3, theScene, canvas);

        //Initialize managers
        PlayerManager playerManager = new PlayerManager(player, gc);
        FuelManager fuelManager = new FuelManager(root, canvas);
        EnemyManager enemyManager = new EnemyManager(playerManager, fuelManager);
        AsteroidManager asteroidManager = new AsteroidManager(playerManager, fuelManager);
        MissileManager missileManager = new MissileManager();
        TextManager textManager = new TextManager(root);
        EffectsManager effectsManager = new EffectsManager();
        BackgroundManager backgroundManager = new BackgroundManager();

        //Initialize objects
        ArrayList<GameObject> ufos = enemyManager.initializeEnemies(canvas, 2, 2, "ufo");
        ArrayList<Asteroid> asteroids = asteroidManager.initializeAsteroids(canvas, 1);

        //Make Level object
        Level level1 = new Level
                (root, player, gc, canvas, this, theScene, 0.0, ufos, asteroids);

        //The shiny PlayerManager class :D
        playerManager.initializePlayerControls(theScene, level1);

        //Add hitboxes to canvas
        root.getChildren().addAll(player.getR(), player.getRv(), player.getRv2());

        final long startNanoTime = System.nanoTime();

        //The main game loop begins below
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                level1.setCurrentFrame(t);

                //Here we are using our shiny new managers! :)
                backgroundManager.renderBackground(gc);
                backgroundManager.updateBackground(t, canvas);
                enemyManager.manageUfos(level1, this);
                missileManager.manageMissiles(level1);
                asteroidManager.manageAsteroids(level1, this, 1);
                playerManager.updatePlayerLocation(canvas);
                playerManager.refreshPlayerSprite(t);
                textManager.updateText(level1.getPlayer());
                effectsManager.manageExplosions(level1.getGc());
                fuelManager.updateFuel(playerManager, level1, this);
            }
        };
        timer.start();
        theStage.show();
    }
}