package managers;

import controllers.MenuController;
import entities.*;
import entities.level.Level;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Shape;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.TimerTask;

public class PlayerManager {
    private Player player;
    private GraphicsContext graphicsContext;

    public PlayerManager(Player player, GraphicsContext graphicsContext) {
        this.setPlayer(player);
        this.getFirePermission();
        this.setGraphicsContext(graphicsContext);
    }

    private GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    private void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public Player getPlayer() {
        return this.player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    private void updatePlayerLocation() {
        //Offset Formula
        double heightOffset = Constants.SCREEN_HEIGHT - this.getPlayer().getHeight();
        double widthOffset = Constants.SCREEN_WIDTH - this.getPlayer().getWidth() / 2;

        double speedMultiplier = Constants.PLAYER_SPEED_MULTIPLIER;

        //Speed up if held var is true
        if (this.getPlayer().isHeld()) {
            speedMultiplier = 1.5;
        }

        double currentPlayerX = this.getPlayer().getPositionX();
        double currentPlayerY = this.getPlayer().getPositionY();
        double currentPlayerSpeed = this.getPlayer().getSpeed();

        Player player = this.getPlayer();

        if (player.isGoUp()) {
            player.updateLocation(player.getPositionX(),
                    Math.max(0, currentPlayerY - currentPlayerSpeed * speedMultiplier));
        }
        if (player.isGoDown()) {
            player.updateLocation(player.getPositionX(),
                    Math.min(heightOffset, currentPlayerY + currentPlayerSpeed * speedMultiplier));
        }
        if (player.isGoLeft()) {
            player.updateLocation(Math.max(0, currentPlayerX - currentPlayerSpeed * speedMultiplier),
                    player.getPositionY());
        }
        if (player.isGoRight()) {
            player.updateLocation(Math.min(widthOffset, currentPlayerX + currentPlayerSpeed * speedMultiplier),
                    player.getPositionY());
        }

        //Update the SVG Path location
        player.svgPath.setLayoutX(player.getPositionX());
        player.svgPath.setLayoutY(player.getPositionY());
    }

    private void getFirePermission() {
        Player that = this.getPlayer();
        this.getPlayer().getTimer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                that.setFired(false);
            }
        }, 0, 1000);
    }

    private Missile fire() {
        if (this.getPlayer().isFired()) {
            return null;
        }

        AudioClip shoot = new AudioClip(Paths.get(Constants.PLAYER_SHOOT_SOUND).toUri().toString());
        shoot.play(0.7);

        Missile missile = null;

        //Load missile sprites
        BufferedImage missileSpriteSheet = null;

        try {
            missileSpriteSheet = ImageIO.read(
                    new File(Constants.PROJECT_PATH + Constants.MISSILE_SPRITESHEET_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        double missilePositionX = this.player.getPositionX() + this.player.getWidth() / 1.2;
        double missilePositionY = this.player.getPositionY() + this.player.getHeight() / 2;

        //Make missile
        missile = new Missile(missilePositionX, missilePositionY, Constants.MISSILE_SPEED, missileSpriteSheet, "player");

        this.getPlayer().setFired(true);
        return missile;
    }

    public void initializePlayerControls(Scene theScene, Level level) {
        //Add event listener
        theScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    this.getPlayer().setGoUp(true);
                    this.getPlayer().playerMove();
                    break;
                case DOWN:
                    this.getPlayer().setGoDown(true);
                    this.getPlayer().playerMove();
                    break;
                case LEFT:
                    this.getPlayer().setGoLeft(true);
                    break;
                case RIGHT:
                    this.getPlayer().setGoRight(true);
                    break;
                case CONTROL:
                    if (!this.getPlayer().isFired()) {
                        level.addMissile(this.fire());
                        this.getPlayer().setFired(true);
                    }
                    break;
                case SHIFT:
                    this.getPlayer().setHeld(true);
            }
        });

        theScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    this.player.setGoUp(false);
                    this.getPlayer().refreshSprites();
                    break;
                case DOWN:
                    this.player.setGoDown(false);
                    this.getPlayer().refreshSprites();
                    break;
                case LEFT:
                    this.player.setGoLeft(false);
                    break;
                case RIGHT:
                    this.player.setGoRight(false);
                    break;
                case SHIFT:
                    this.player.setHeld(false);
            }
        });
    }

    //Method that checks if the player collides with a given object
    public boolean checkCollision(Sprite sprite) {
        Shape intersect = Shape.intersect(this.getPlayer().svgPath, sprite.getBoundsAsShape());

        if (intersect.getBoundsInLocal().getWidth() != -1) {
            return true;
        }
        return false;
    }

    public void resetPlayerPosition() {
        this.getPlayer().updateLocation(Constants.PLAYER_START_X, Constants.SCREEN_HEIGHT / 2);
    }

    private void animateSprites(double time) {
        this.getPlayer().setImage(this.getPlayer().getFrame(this.getPlayer().getSprites(), time, 0.100));
        this.getPlayer().render(this.getGraphicsContext());
    }

    private void checkIfPlayerIsDead(Level level, AnimationTimer timer) throws Exception {
        if (this.getPlayer().getLives() <= 0) {
            timer.stop();

            try {
                level.getScene().setRoot(FXMLLoader.load(getClass().getResource("../views/game_over.fxml")));
                level.writeInLeaderboard(MenuController.USERNAME, this.getPlayer().getPoints());
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc);
            }
        }
    }

    private void refreshSprites() {
        Player that = this.getPlayer();
        this.getPlayer().getTimer().schedule(new TimerTask() {
            @Override
            public void run() {
                that.refreshSprites();
            }
        }, 5000);
    }

    public void playerHit() {
        this.getPlayer().setHit(true);
        this.getPlayer().playerMove();
        this.refreshSprites();
    }

    public void managePlayer(Level level, double t, AnimationTimer animationTimer) {
        this.updatePlayerLocation();
        this.animateSprites(t);

        try {
            this.checkIfPlayerIsDead(level, animationTimer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}