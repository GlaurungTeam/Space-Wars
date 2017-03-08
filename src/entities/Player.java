package entities;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class Player extends Sprite {
    private Integer lives;
    private long points;
    private Scene scene;
    private boolean fired;
    private Timer timer;
    public SVGPath svgPath;
    private Image[] originalSprites;
    private Image[] playerHitSprites;
    private BufferedImage playerHitSpriteSheet;

    private boolean goLeft;
    private boolean goRight;
    private boolean goUp;
    private boolean goDown;
    private boolean held;

    public Player(Double speed, Integer lives, Scene scene, Canvas canvas) throws IOException {
        this.setFired(Constants.DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY);
        this.setHeld(Constants.DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY);
        this.setPoints(Constants.START_POINTS);

        super.setSpeed(speed);
        this.setLives(lives);
        this.setScene(scene);

        //We are using SVG Path to make up for the complex form of the spaceship
        svgPath = new SVGPath();
        svgPath.setContent("M23,9 L23,9 23,70 29,70 61,45 72,45 78,41 72,35 59,33 28,8 Z");

        this.timer = new Timer();

        //Load sprites from file
        BufferedImage playerSpriteSheet =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE));

        BufferedImage playerHitSpriteSheet =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE_HIT));


        super.setSpriteParameters(82, 82, 2, 3);

        this.setPlayerHitSpriteSheet(playerHitSpriteSheet);
        this.splitHitSprites();
        super.setSpriteSheet(playerSpriteSheet);
        super.splitSprites();
        this.setOriginalSprites(super.getSprites());
        super.setPosition(100, canvas.getHeight() / 2);
    }

    private void splitHitSprites() {
        this.setPlayerHitSprites(new Image[super.getRows()*super.getCols()]);
        for (int i = 0; i < super.getRows(); i++) {
            for (int j = 0; j < super.getCols(); j++) {
                this.playerHitSprites[(i * super.getCols()) + j] = SwingFXUtils.toFXImage(this.playerHitSpriteSheet.getSubimage(
                        j * super.getWidth(),
                        i * super.getHeight(),
                        super.getWidth(),
                        super.getHeight()
                ), null);
            }
        }
    }

    public void refreshSprites() {
        super.setSprites(this.getOriginalSprites());
    }

    private Image[] getOriginalSprites() {
        return originalSprites;
    }

    private void setOriginalSprites(Image[] originalSprites) {
        this.originalSprites = originalSprites;
    }

    private void setPlayerHitSprites(Image[] playerHitSprites) {
        this.playerHitSprites = playerHitSprites;
    }

    private void setPlayerHitSpriteSheet(BufferedImage playerHitSpriteSheet) {
        this.playerHitSpriteSheet = playerHitSpriteSheet;
    }

    public Image[] getPlayerHitSprites() {
        return playerHitSprites;
    }

    public void playerHit(){
        this.setSprites(this.getPlayerHitSprites());
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

    public void setHeld(boolean value) {
        this.held = value;
    }

    public boolean isHeld() {
        return this.held;
    }
}