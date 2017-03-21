package entities;

import helpers.SVGPathReader;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Player extends Sprite {
    private Integer lives;
    private long points;
    private Scene scene;
    private boolean fired;
    private Timer timer;
    public SVGPath svgPath;
    private List<Image> originalSprites;
    private List<Image> playerHitSprites;
    private List<Image> playerUpSprites;
    private List<Image> playerDownSprites;
    private List<Image> playerDownHit;
    private List<Image> playerUpHit;
    private boolean isHit;

    private boolean goLeft;
    private boolean goRight;
    private boolean goUp;
    private boolean goDown;
    private boolean held;

    public Player(double positionX, double positionY, double objectSpeed,
                  BufferedImage spriteSheet, int width, int height, int rows, int cols,
                  int lives, Scene scene) throws IOException {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols);
        this.setFired(Constants.DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY);
        this.setHeld(Constants.DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY);
        this.setPoints(Constants.START_POINTS);

        this.setLives(lives);
        this.setScene(scene);

        //We are using SVG Path to make up for the complex form of the spaceship
        svgPath = new SVGPath();

        SVGPathReader SVGPathReader = new SVGPathReader();
        svgPath.setContent(SVGPathReader.readString(Constants.PLAYER_SVGPATH_LOCATION));

        this.timer = new Timer();
        super.setSpriteParameters(43, 39, 1, 2);
        this.loadPlayerSprites();

        this.setHit(false);
        super.setPosition(100, 400/*canvas.getHeight() / 2*/);//TODO
    }

    private List<Image> splitSprites(BufferedImage bufferedImage) {
        List<Image> sprites = new ArrayList<>();
        for (int i = 0; i < super.getRows(); i++) {
            for (int j = 0; j < super.getCols(); j++) {
                sprites.add((i * super.getCols()) + j, SwingFXUtils.toFXImage(bufferedImage.getSubimage(
                        j * super.getWidth(),
                        i * super.getHeight(),
                        super.getWidth(),
                        super.getHeight()
                ), null));
            }
        }
        return sprites;
    }

    private void loadPlayerSprites() throws IOException {
        //Load sprites from file
        BufferedImage playerSpriteSheet =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE));

        BufferedImage playerHitSpriteSheet =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE_HIT));

        BufferedImage playerUpSrites =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE_UP));

        BufferedImage playerDownSrites =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE_DOWN));

        BufferedImage playerDownHitSrites =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE_DOWN_HIT));

        BufferedImage playerUpHitSprites =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE_UP_HIT));

        this.setPlayerHitSprites(this.splitSprites(playerHitSpriteSheet));
        this.setPlayerUpSprites(this.splitSprites(playerUpSrites));
        this.setPlayerDownSprites(this.splitSprites(playerDownSrites));
        this.setPlayerDownHit(this.splitSprites(playerDownHitSrites));
        this.setPlayerUpHit(this.splitSprites(playerUpHitSprites));

        super.setSpriteSheet(playerSpriteSheet);
        super.splitSprites();
        this.setOriginalSprites(super.getSprites());
    }

    private List<Image> getPlayerUpSprites() {
        return playerUpSprites;
    }

    private void setPlayerUpSprites(List<Image> playerUpSprites) {
        this.playerUpSprites = playerUpSprites;
    }

    private List<Image> getPlayerDownSprites() {
        return playerDownSprites;
    }

    private void setPlayerDownSprites(List<Image> playerDownSprites) {
        this.playerDownSprites = playerDownSprites;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    private List<Image> getPlayerDownHit() {
        return playerDownHit;
    }

    private void setPlayerDownHit(List<Image> playerDownHit) {
        this.playerDownHit = playerDownHit;
    }

    private List<Image> getPlayerUpHit() {
        return playerUpHit;
    }

    private void setPlayerUpHit(List<Image> playerUpHit) {
        this.playerUpHit = playerUpHit;
    }

    public void refreshSprites() {
        this.setHit(false);
        super.setSprites(this.originalSprites);
    }



    private List<Image> getOriginalSprites() {
        return this.originalSprites;
    }

    private void setOriginalSprites(List<Image> originalSprites) {
        this.originalSprites = originalSprites;
    }

    public List<Image> getPlayerHitSprites() {
        return this.playerHitSprites;
    }

    private void setPlayerHitSprites(List<Image> playerHitSprites) {
        this.playerHitSprites = playerHitSprites;
    }

    private void playerHit() {
        this.setSprites(this.getPlayerHitSprites());
        if (!this.isHit()) {
            if (this.isGoUp()) {
                this.setSprites(this.getPlayerUpSprites());
            } else if (this.isGoDown()) {
                this.setSprites(this.getPlayerDownSprites());
            }
        } else {
            if (this.isGoUp()) {
                this.setSprites(this.getPlayerUpHit());
            } else if (this.isGoDown()) {
                this.setSprites(this.getPlayerDownHit());
            }
        }
    }

    public void playerMove() {
        this.playerHit();
    }

    public Timer getTimer() {
        return timer;
    }

    //Movement getters and setters
    public boolean isGoLeft() {
        return this.goLeft;
    }

    public void setGoLeft(boolean goLeft) {
        this.goLeft = goLeft;
    }

    public boolean isGoRight() {
        return this.goRight;
    }

    public void setGoRight(boolean goRight) {
        this.goRight = goRight;
    }

    public boolean isGoUp() {
        return this.goUp;
    }

    public void setGoUp(boolean goUp) {
        this.goUp = goUp;
    }

    public boolean isGoDown() {
        return this.goDown;
    }

    public void setGoDown(boolean goDown) {
        this.goDown = goDown;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public Integer getLives() {
        return lives;
    }

    public void setLives(Integer lives) {
        this.lives = lives;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public boolean isHeld() {
        return this.held;
    }

    public void setHeld(boolean value) {
        this.held = value;
    }
}