package managers;

import contracts.Level;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import models.gameObjects.PlayerImpl;
import utils.Constants;

public class GameManager {

    public void start(Stage theStage, Level level, String playerName) throws Exception {
        Group root = new Group();
        theStage.getScene().setRoot(root);

        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        PlayerImpl player = new PlayerImpl(Constants.PLAYER_START_X, Constants.SCREEN_HEIGHT / 2,
                Constants.PLAYER_SPEED, Constants.PLAYER_LIVES, playerName);

        //Initialize managers
        PlayerManager playerManager = new PlayerManager(player, gc);
        FuelManager fuelManager = new FuelManager(root);
        EnemyManager enemyManager = new EnemyManager();
        MissileManager missileManager = new MissileManager();
        UserInterfaceManager userInterfaceManager = new UserInterfaceManager(root);
        ExplosionManager explosionManager = new ExplosionManager();
        BackgroundManager backgroundManager = new BackgroundManager();
        BossManager bossManager = new BossManager();

        level.initializeLevel(player, gc, canvas, theStage.getScene(), 0.0,
                enemyManager, bossManager, playerManager, fuelManager, explosionManager);

        backgroundManager.setBackgroundImage(level.getBackgroundImage());

        //The shiny PlayerManager class :D
        playerManager.initializePlayerControls(level);

        final long startNanoTime = System.nanoTime();

        //The main game loop begins below
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                level.setCurrentFrame(t);

                //Here we are using our shiny new managers! :)
                backgroundManager.updateBackground(t, canvas, gc);
                enemyManager.manageEnemies(level);
                missileManager.manageMissiles(level);
                playerManager.managePlayer(level, t, this);
                userInterfaceManager.updateText(level.getPlayer());
                explosionManager.manageExplosions(level.getGc());
                fuelManager.updateFuel(level);
                bossManager.manageBoss(level);
            }
        };
        timer.start();
        theStage.show();

        userInterfaceManager.getPauseButton().setOnMouseClicked(event1 -> {
            timer.stop();
            fuelManager.pauseFuel();

            userInterfaceManager.getPauseButton().setVisible(false);
            userInterfaceManager.getPauseBox().setVisible(true);
            userInterfaceManager.getResumeButton().setVisible(true);
            userInterfaceManager.getQuitButton().setVisible(true);

            userInterfaceManager.getResumeButton().setOnMouseClicked(event2 -> {
                {
                    timer.start();
                    fuelManager.resumeFuel();

                    userInterfaceManager.getPauseBox().setVisible(false);
                    userInterfaceManager.getPauseButton().setVisible(true);
                    userInterfaceManager.getResumeButton().setVisible(false);
                    userInterfaceManager.getQuitButton().setVisible(false);
                }
            });
            userInterfaceManager.getQuitButton().setOnMouseClicked(event3 -> System.exit(0));
        });
    }
}