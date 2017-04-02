package managers;

import entities.Constants;
import entities.Player;
import entities.level.Level;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class GameManager {
    public void start(Stage theStage, Level level) throws Exception {
        theStage.setTitle("Space Wars");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);
        theStage.setFullScreen(true);
        theStage.setFullScreenExitHint("");

        DimensionsManager dimensions = new DimensionsManager();
        dimensions.calculateScreenDimensions();

        Canvas canvas = new Canvas(dimensions.getCurrentDeviceWidth(), dimensions.getCurrentDeviceHeight());
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Player player = new Player(Constants.playerStartX, canvas.getHeight() / 2,
                Constants.PLAYER_SPEED, Constants.PLAYER_LIVES, theScene);

        //Initialize managers
        PlayerManager playerManager = new PlayerManager(player, gc);
        FuelManager fuelManager = new FuelManager(root);
        EnemyManager enemyManager = new EnemyManager(playerManager, fuelManager);
        MissileManager missileManager = new MissileManager(level);
        UserInterfaceManager userInterfaceManager = new UserInterfaceManager(root);
        EffectsManager effectsManager = new EffectsManager();
        BackgroundManager backgroundManager = new BackgroundManager();
        BossManager bossManager = new BossManager(playerManager, fuelManager);

        level.initializeLevel(player, gc, canvas, theScene, 0.0,
                enemyManager, bossManager, playerManager, fuelManager);
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
                enemyManager.manageEnemies(level);
                missileManager.manageMissiles();
                playerManager.managePlayer(level, t, this);
                userInterfaceManager.updateText(level.getPlayer());
                effectsManager.manageExplosions(level.getGc());
                fuelManager.updateFuel(playerManager, level);
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