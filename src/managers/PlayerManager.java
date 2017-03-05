package managers;

import controllers.MenuController;
import entities.Level;
import entities.Missile;
import entities.Player;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.TimerTask;

public class PlayerManager {
    //TODO Player class must only have fields, getters and setters. All other methods must be managed by the PlayerManager class!

    //Initialize Player entity with spawn coordinates(row 129-134 in GameController)
    //UpdatePlayerLocation() row 91 in Player class
    //Must implement all methods from Player class
    private Player player;
    private GraphicsContext graphicsContext;

    public PlayerManager(Player player, GraphicsContext graphicsContext) {
        this.setPlayer(player);
        this.initializeHitboxes();
        this.getFirePermission();
        this.setGraphicsContext(graphicsContext);
    }

    private GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    private void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    private Player getPlayer() {
        return this.player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    public void updatePlayerLocation(Canvas canvas) {
        //Offset Formula
        double heightOffset = canvas.getHeight() - 72;
        double widthOffset = canvas.getWidth() - 82;

        double speedMultiplier = 1;

        //Speed up if held var is true(see GameController key events)
        if (this.getPlayer().isHeld()) {
            speedMultiplier = 1.5;
        }

        if (this.getPlayer().isGoUp()) {
            this.getPlayer().setPositionY(Math.max(0, this.getPlayer().getPositionY() - this.getPlayer().getSpeed() * speedMultiplier));
        }
        if (this.getPlayer().isGoDown()) {
            this.getPlayer().setPositionY(Math.min(heightOffset, this.getPlayer().getPositionY() + this.getPlayer().getSpeed() * speedMultiplier));
        }
        if (this.getPlayer().isGoLeft()) {
            this.getPlayer().setPositionX(Math.max(0, this.getPlayer().getPositionX() - this.getPlayer().getSpeed() * speedMultiplier));
        }
        if (this.getPlayer().isGoRight()) {
            this.getPlayer().setPositionX(Math.min(widthOffset, this.getPlayer().getPositionX() + this.getPlayer().getSpeed() * speedMultiplier));
        }

        //Updates the hitbox with every player update
        this.getPlayer().getR().setY(this.getPlayer().getPositionY() + 38);
        this.getPlayer().getR().setX(this.getPlayer().getPositionX() + 13);

        this.getPlayer().getRv().setY(this.getPlayer().getPositionY() + 11);
        this.getPlayer().getRv().setX(this.getPlayer().getPositionX() + 20);

        this.getPlayer().getRv2().setY(this.getPlayer().getPositionY() + 27);
        this.getPlayer().getRv2().setX(this.getPlayer().getPositionX() + 38);
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

        AudioClip shoot = new AudioClip(Paths.get("src/resources/sound/lasergun.mp3").toUri().toString());
        shoot.play(0.7);

        //Make missile
        Missile missile = new Missile(this.player.getPositionX() + this.player.getWidth() / 1.2,
                this.player.getPositionY() + this.player.getHeight() / 2,
                2);

        //Load missile sprites
        BufferedImage missileSpriteSheet = null;

        try {
            missileSpriteSheet = ImageIO.read(new File(MenuController.PROJECT_PATH + "/src/resources/missiles/largeMissiles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        missile.setSpriteParameters(31, 7, 1, 23);
        missile.setSpriteSheet(missileSpriteSheet);
        missile.splitSprites();

        //gameController.setMissiles(missile);
        //TODO update current level missiles

        this.getPlayer().setFired(true);
        return missile;
    }

    public void initializePlayerControls(Scene theScene, Level level) {
        //Add event listener
        theScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    this.getPlayer().setGoUp(true);
                    break;
                case DOWN:
                    this.getPlayer().setGoDown(true);
                    break;
                case LEFT:
                    this.getPlayer().setGoLeft(true);
                    break;
                case RIGHT:
                    this.getPlayer().setGoRight(true);
                    break;
                case SPACE:
                    //System.out.println(player.isFired());
                    if (!this.getPlayer().isFired()) {
                        level.getMissiles().add(this.fire());
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
                    break;
                case DOWN:
                    this.player.setGoDown(false);
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

    private void initializeHitboxes() {
        this.player.getR().setWidth(57);
        this.player.getR().setHeight(7);
        this.player.getR().setStroke(Color.TRANSPARENT);
        this.player.getR().setFill(Color.TRANSPARENT);

        this.player.getRv().setWidth(4);
        this.player.getRv().setHeight(58);
        this.player.getRv().setStroke(Color.TRANSPARENT);
        this.player.getRv().setFill(Color.TRANSPARENT);

        this.player.getRv2().setWidth(3);
        this.player.getRv2().setHeight(28);
        this.player.getRv2().setStroke(Color.TRANSPARENT);
        this.player.getRv2().setFill(Color.TRANSPARENT);
    }

    //Method that checks if the player collides with a given object
    public boolean checkCollision(double x, double y, int offset) {
        int hitX = (int) x;
        int hitY = (int) y;
        int mainX = (int) this.getPlayer().getR().getX();
        int mainY = (int) this.getPlayer().getR().getY();
        int mainX1 = (int) this.getPlayer().getRv().getX();
        int mainY1 = (int) this.getPlayer().getRv().getY();
        int mainX2 = (int) this.getPlayer().getRv2().getX();
        int mainY2 = (int) this.getPlayer().getRv2().getY();

        if ((hitX <= mainX + (int) this.getPlayer().getR().getWidth() && hitX + offset >= mainX && hitY <= mainY + (int) this.getPlayer().getR().getHeight() && hitY + offset >= mainY) ||
                (hitX <= mainX1 + (int) this.getPlayer().getRv().getWidth() && hitX + offset >= mainX1 && hitY <= mainY1 + (int) this.getPlayer().getRv().getHeight() && hitY + offset >= mainY1) ||
                (hitX <= mainX2 + (int) this.getPlayer().getRv2().getWidth() && hitX + offset >= mainX2 && hitY <= mainY2 + (int) this.getPlayer().getRv2().getHeight() && hitY + offset >= mainY2)
                ) {
            return true;
        }
        return false;
    }

    public void resetPlayerPosition(Canvas canvas, FuelManager fuelManager) {
        this.getPlayer().setPositionX(50);
        this.getPlayer().setPositionY(canvas.getHeight() / 2);
        fuelManager.resetFuel();
    }

    public void refreshPlayerSprite(double time) {
        this.getPlayer().setImage(this.getPlayer().getFrame(this.getPlayer().getSprites(), time, 0.100));
        this.getPlayer().render(this.getGraphicsContext());
    }

    public void checkIfPlayerIsDead(Level level, AnimationTimer timer) throws Exception {
        if (this.getPlayer().getLives() <= 0) {
            timer.stop();
            try {
                level.getScene().setRoot(FXMLLoader.load(getClass().getResource("../views/sample.fxml")));
                level.writeInLeaderboard(MenuController.userName, this.getPlayer().getPoints());
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc);
            }
        }
    }
}