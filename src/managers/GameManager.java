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
        Player player = new Player(Constants.PLAYER_SPEED, Constants.PLAYER_LIVES, theScene, canvas);

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
        ArrayList<GameObject> ufos = enemyManager.initializeEnemies(
                canvas, Constants.UFO_COUNT, Constants.UFO_SPEED, "ufo");
        ArrayList<Asteroid> asteroids = asteroidManager.initializeAsteroids(canvas, Constants.ASTEROID_HEALTH);
        Level level;
        //Make Level object - level object takes currentLevel fiel from the player
        //and create level obj from current level ... level1,level2...
        level = new Level1
                (root, player, gc, canvas, this, theScene, 0.0, asteroids, ufos);

        //The shiny PlayerManager class :D
        playerManager.initializePlayerControls(theScene, level);

        final long startNanoTime = System.nanoTime();

        //The main game loop begins below
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                level.setCurrentFrame(t);
                //Here we are using our shiny new managers! :)
                backgroundManager.renderBackground(gc);
                backgroundManager.updateBackground(t, canvas);
                enemyManager.manageUfos(level, this);
                missileManager.manageMissiles(level);
                asteroidManager.manageAsteroids(level, this, Constants.ASTEROID_HEALTH);
                playerManager.updatePlayerLocation(canvas);
                playerManager.animateSprites(t);
                textManager.updateText(level.getPlayer());
                effectsManager.manageExplosions(level.getGc());
                fuelManager.updateFuel(playerManager, level, this);
            }
        };
        timer.start();
        theStage.show();
    }
}