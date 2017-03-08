package managers;

import entities.Constants;
import entities.Player;
import entities.level.Level;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class GameManager {
    public void start(Stage theStage, Level level) throws Exception {
        theStage.setTitle("Space Wars");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        VBox pauseBox = createBox();
        Button pauseButton = createPauseButton();

        Canvas canvas = new Canvas(1280, 720);
        root.getChildren().add(canvas);
        root.getChildren().add(pauseBox);
        root.getChildren().add(pauseButton);

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
        BossManager bossManager = new BossManager(playerManager, fuelManager);

        level.initializeLevel(player, gc, canvas, theScene, 0.0,
                enemyManager, asteroidManager, bossManager, playerManager, fuelManager);
        level.setDifficultyParameters();

        backgroundManager.setBackgroundImage(level.getBackgroundImage());

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
                asteroidManager.manageAsteroids(level, this);
                playerManager.updatePlayerLocation(canvas);
                playerManager.animateSprites(t);
                textManager.updateText(level.getPlayer());
                effectsManager.manageExplosions(level.getGc());
                fuelManager.updateFuel(playerManager, level, this);
                bossManager.manageBoss(level, asteroidManager, this);
            }
        };
        timer.start();
        theStage.show();
        pauseButton.setOnMouseClicked(event -> {
            pauseButton.setVisible(false);
            pauseBox.setVisible(true);
            timer.stop();
            fuelManager.pauseFuel();
            pauseBox.getChildren().get(0).setOnMouseClicked(event1 -> {
                {
                    pauseBox.setVisible(false);
                    pauseButton.setVisible(true);
                    timer.start();
                    fuelManager.resumeFuel();
                }
            });
            pauseBox.getChildren().get(1).setOnMouseClicked(event12 -> {
                System.exit(0);
            });
        });
    }

    private VBox createBox(){
        VBox pauseBox = new VBox();
        pauseBox.setPrefSize(100, 100);
        pauseBox.getChildren().addAll(createResumeButton(), createQuitButton());
        pauseBox.setSpacing(5);
        pauseBox.setAlignment(Pos.BASELINE_CENTER);
        pauseBox.setFillWidth(true);
        pauseBox.setVisible(false);
        pauseBox.setLayoutX(620);
        pauseBox.setLayoutY(300);
        return pauseBox;
    }

    private Button createPauseButton() {
        Button pauseButton = new Button("PAUSE");
        pauseButton.setLayoutX(20);
        pauseButton.setLayoutY(110);
        pauseButton.setStyle("-fx-alignment: baseline-center; -fx-animated: true; -fx-font: 18 bolder; -fx-border-color: black");
        return pauseButton;
    }

    private Button createResumeButton() {
        Button pauseButton = new Button("RESUME");
        pauseButton.setLayoutX(20);
        pauseButton.setLayoutY(110);
        pauseButton.setStyle("-fx-alignment: baseline-center; -fx-animated: true; -fx-font: 18 bolder; -fx-border-color: black");
        return pauseButton;
    }

    private Button createQuitButton() {
        Button pauseButton = new Button("QUIT");
        pauseButton.setLayoutX(20);
        pauseButton.setLayoutY(110);
        pauseButton.setStyle("-fx-alignment: baseline-center; -fx-animated: true; -fx-font: 18 bolder; -fx-border-color: black");
        return pauseButton;
    }
}