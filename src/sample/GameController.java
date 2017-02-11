package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objectClasses.*;

import javax.imageio.ImageIO;

import javafx.scene.media.AudioClip;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

public class GameController extends Application {

    @FXML
    public Label spaceWars;

    //Time elapsed
    private Timer timer = new Timer();
    private long points = 0;

    private int livesCount = 3;

    private int backgroundX = 0;
    private int backgroundY = 0;

    private int planetX = 500;
    private int planetY = 196;

    @Override
    public void start(Stage theStage) throws Exception {
        theStage.setTitle("Space Wars");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(1280, 720);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Text scoreLine = new Text(20, 30, "");
        root.getChildren().add(scoreLine);
        scoreLine.setFont(Font.font("Verdana", 20));
        scoreLine.setFill(Color.WHITE);
        String score = toString().format("Score: %d", points);
        scoreLine.setText(score);

        Text lives = new Text(20, 50, "");
        root.getChildren().add(lives);
        lives.setFont(Font.font("Verdana", 20));
        lives.setFill(Color.WHITE);
        String livesNumber = toString().format("Lives: %d", livesCount);
        lives.setText(livesNumber);

        Image earth = new Image("resources/earth.png");
        Image sun = new Image("resources/sun.png");
        Image space = new Image("resources/space.png");

        //Adjustable speeds
        double asteroidSpeed = 2.5;
        double playerSpeed = 3;
        double ufoSpeed = 2;
        double backGroundSpeed = 1.5;

        //Make new player object
        Player player = new Player();

        player.initializeHitboxes();

        //Add hitboxes to canvas
        root.getChildren().addAll(player.r, player.rv, player.rv2);

        //Load sprites from file
        BufferedImage playerSpriteSheet = ImageIO.read(new File(Controller.PROJECT_PATH + "/src/resources/spaceship/spaceshipSprites4.png"));
        player.setSpriteParameters(82, 82, 2, 3);
        player.loadSpriteSheet(playerSpriteSheet);
        player.splitSprites();
        player.setPosition(100, canvas.getHeight() / 2, playerSpeed);

        //Disable and enable shooting if space key is pressed(ship can shoot in 1sec delay)
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                player.fired = false;
            }
        }, 0, 1000);

        //Make new key listener which is going to monitor the keys pressed
        KeyListener.initialize(theScene, player);

        //Make an array holding all asteroids
        Asteroid[] asteroids = new Asteroid[20];

        //Initialize all asteroids
        Asteroid.initializeAsteroids(asteroids, canvas, asteroidSpeed);

        //Experimental asteroid animation
//        BufferedImage asteroidSpriteSheet = ImageIO.read(new File(Controller.PROJECT_PATH + "\\src\\resources\\asteroid\\asteroids1.png"));
//        Asteroid.loadAsteroidSpriteSheet(asteroidSpriteSheet);
//        Asteroid.splitAsteroidSprites(1, 4, 35, 35);

        Ufo[] ufos = new Ufo[2];
        Ufo.initializeUfos(ufos, canvas, ufoSpeed);

        final long startNanoTime = System.nanoTime();

        //The main game loop begins below
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                //The 4 rows below are used to help make the earth move around the sun
                //No need to understand it
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                double earthX = planetX + 36 + 128 * Math.cos(t);
                double earthY = 232 + 128 * Math.sin(t);

                //Update background, planet and earth location
                backgroundX -= backGroundSpeed;
                planetX -= backGroundSpeed - 0.5;

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

                //Iterate through all asteroids
                for (Asteroid asteroidToRenderAndUpdate : asteroids) {

                    if (!asteroidToRenderAndUpdate.isHit) {
                        asteroidToRenderAndUpdate.render(gc);

                        if (player.checkCollision(asteroidToRenderAndUpdate.positionX, asteroidToRenderAndUpdate.positionY, 32)) {
                            asteroidToRenderAndUpdate.positionX = -1300;
                            player.positionX = 10;

                            livesCount--;

                            checkIfPlayerIsDead(livesCount, theScene);

                            String livesC = toString().format("Lives: %d", livesCount);
                            lives.setText(livesC);
                            continue;

                            //TODO Change color of ship when hit, or some kind of visual effect

                            //TODO Implement ship damage tracker
                        }
                    }

                    //Asteroid speed updating every rotation making them faster:
                    asteroidToRenderAndUpdate.speed += 0.00001;
                    asteroidToRenderAndUpdate.updateAsteroidLocation(canvas);
                }

                for (Ufo ufo : ufos) {
                    if (!ufo.isHit) {
                        ufo.render(gc);

                        if (player.checkCollision(ufo.positionX, ufo.positionY, 32)) {
                            player.positionX = 10;
                            ufo.positionX = -1300;

                            livesCount--;

                            checkIfPlayerIsDead(livesCount, theScene);

                            String livesC = toString().format("Lives: %d", livesCount);
                            lives.setText(livesC);
                            continue;
                        }
                    }

                    ufo.speed += 0.00002;
                    ufo.updateUfoLocation(canvas);
                }

                player.setImage(player.getFrame(player.sprites, t, 0.100));
                player.render(gc);
                player.updatePlayerLocation(canvas, KeyListener.goUp, KeyListener.goDown, KeyListener.goLeft, KeyListener.goRight);

                if (player.missiles.size() != 0) {
                    for (int i = 0; i < player.missiles.size(); i++) {
                        Missile m = player.missiles.get(i);

                        if (m.positionX > canvas.getWidth()) {
                            player.missiles.remove(m);
                            return;
                        }

                        m.setImage(m.getFrame(m.sprites, t, 0.100));
                        m.render(gc);
                        m.updateMissileLocation();

                        //Collision detection missile hits asteroid and removes it from canvas
                        for (Asteroid asteroidToCheck : asteroids) {
                            if (asteroidToCheck.isHit) {
                                continue;
                            }

                            if (m.intersects(asteroidToCheck)) {
                                asteroidToCheck.isHit = true;

                                AudioClip explode = new AudioClip(Paths.get("src/resources/sound/explosion2.mp3").toUri().toString());
                                explode.play(0.6);

                                //Remove missile from missiles array and explode
                                Explosion explosion = new Explosion();
                                explosion.explode(m);
                                player.missiles.remove(m);

                                //TODO Move that one to the killing aliens method to display score
                                points++;
                                String score = toString().format("Score: %d", points);
                                scoreLine.setText(score);

                                //TODO Implement score tracker
                            }
                        }

                        for (Ufo ufoToCheck : ufos) {
                            if (ufoToCheck.isHit) {
                                continue;
                            }

                            if (m.intersects(ufoToCheck)) {
                                ufoToCheck.isHit = true;

                                AudioClip explode = new AudioClip(Paths.get("src/resources/sound/explossion.mp3").toUri().toString());
                                explode.play(0.2);

                                Explosion explosion = new Explosion();
                                explosion.explode(m);
                                player.missiles.remove(m);

                                points += 3;
                                String score = toString().format("Score: %d", points);
                                scoreLine.setText(score);
                            }
                        }
                    }
                }

                //Iterate through all explosions
                if (Explosion.explosions.size() != 0) {
                    for (int i = 0; i < Explosion.explosions.size(); i++) {
                        Explosion explosion = Explosion.explosions.get(i);
                        Image currentFrame = explosion.getCurrentExplosionFrame(explosion.currentFrameIndex);

                        if (explosion.currentFrameIndex < explosion.sprites.length - 1) {
                            explosion.setImage(currentFrame);
                            explosion.render(gc);
                            explosion.currentFrameIndex++;
                        } else {
                            Explosion.explosions.remove(i);
                        }
                    }
                }

            }
        }.start();

        theStage.show();
    }

    private void checkIfPlayerIsDead(int livesCount, Scene theScene) {
        if (livesCount < 0) {
            try {
                theScene.setRoot(FXMLLoader.load(getClass().getResource("sample.fxml")));
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc);
            }
        }
    }
}