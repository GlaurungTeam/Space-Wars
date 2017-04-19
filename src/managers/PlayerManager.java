package managers;

import controllers.MenuController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Shape;
import models.gameObjects.GameObject;
import models.gameObjects.Missile;
import models.gameObjects.Player;
import models.level.Level;
import utils.Constants;

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
        this.player = player;
        this.getFirePermission();
        this.graphicsContext = graphicsContext;
    }

    public Player getPlayer() {
        return this.player;
    }

    private void updatePlayerLocation() {
        //Offset Formula
        double heightOffset = Constants.SCREEN_HEIGHT - this.getPlayer().getHeight();
        double widthOffset = Constants.SCREEN_WIDTH - this.getPlayer().getWidth() / 2;

        double speedMultiplier = Constants.PLAYER_SPEED_MULTIPLIER;

        //Speed up if held var is true
        if (this.player.isLightningSpeedOn()) {
            speedMultiplier = 1.5;
        }

        double currentPlayerX = this.getPlayer().getPositionX();
        double currentPlayerY = this.getPlayer().getPositionY();
        double currentPlayerSpeed = this.getPlayer().getSpeed();

        Player player = this.getPlayer();

        if (player.isTurningUp()) {
            player.updateLocation(player.getPositionX(),
                    Math.max(0, currentPlayerY - currentPlayerSpeed * speedMultiplier));
        }

        if (player.isTurningDown()) {
            player.updateLocation(player.getPositionX(),
                    Math.min(heightOffset, currentPlayerY + currentPlayerSpeed * speedMultiplier));
        }

        if (player.isTurningLeft()) {
            player.updateLocation(Math.max(0, currentPlayerX - currentPlayerSpeed * speedMultiplier),
                    player.getPositionY());
        }

        if (player.isTurningRight()) {
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
                that.changeFiredStatus(false);
            }
        }, 0, 1000);
    }

    private Missile fire() {
        if (this.getPlayer().isFired()) {
            return null;
        }

        AudioClip shoot = new AudioClip(Paths.get(Constants.PLAYER_SHOOT_SOUND).toUri().toString());
        shoot.play(0.7);

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
        Missile missile = new Missile(missilePositionX, missilePositionY, Constants.MISSILE_SPEED, missileSpriteSheet, "player");

        this.getPlayer().changeFiredStatus(true);
        return missile;
    }

    public void initializePlayerControls(Level level) {
        //Add event listener
        level.getScene().setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();

            switch (event.getCode()) {
                case UP:
                    this.player.setDirection(true, keyCode);
                    this.getPlayer().manageSpriteAnimation();
                    break;
                case DOWN:
                    this.player.setDirection(true, keyCode);
                    this.getPlayer().manageSpriteAnimation();
                    break;
                case LEFT:
                    this.player.setDirection(true, keyCode);
                    break;
                case RIGHT:
                    this.player.setDirection(true, keyCode);
                    break;
                case SHIFT:
                    this.getPlayer().shiftLightningSpeed(true);
                    break;
                case E:
                    if (!this.getPlayer().isFired()) {
                        level.addMissile(this.fire());
                        this.getPlayer().changeFiredStatus(true);
                    }
                    break;
            }
        });

        level.getScene().setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();

            switch (keyCode) {
                case UP:
                    this.player.setDirection(false, keyCode);
                    this.getPlayer().refreshSprites();
                    break;
                case DOWN:
                    this.player.setDirection(false, keyCode);
                    this.getPlayer().refreshSprites();
                    break;
                case LEFT:
                    this.player.setDirection(false, keyCode);
                    break;
                case RIGHT:
                    this.player.setDirection(false, keyCode);
                    break;
                case SHIFT:
                    this.player.shiftLightningSpeed(false);
                    break;
            }
        });
    }

    //Method that checks if the player collides with a given object
    public boolean checkCollision(GameObject sprite) {
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
        this.getPlayer().render(this.graphicsContext);
    }

    private void checkIfPlayerIsDead(Level level, AnimationTimer timer) throws Exception {
        if (this.getPlayer().getCurrentLives() <= 0) {
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
        this.getPlayer().hitPlayer();
        this.getPlayer().manageSpriteAnimation();
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