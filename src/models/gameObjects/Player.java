package models.gameObjects;

import enums.SpriteSheetParameters;
import helpers.Reader;
import helpers.SVGPathReader;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Player extends BaseHealthableGameObject {

    private static final String TYPE = "player";

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
    private List<Image> playerUpHitSprites;
    private List<Image> playerDownHitSprites;

    private boolean isHit;
    private boolean goLeft;
    private boolean goRight;
    private boolean goUp;
    private boolean goDown;
    private boolean held;

    public Player(double positionX, double positionY, double objectSpeed, int lives, Scene scene) throws IOException {
        super(positionX, positionY, objectSpeed, null,
                SpriteSheetParameters.PLAYER_DEFAULT.getWidth(),
                SpriteSheetParameters.PLAYER_DEFAULT.getHeight(),
                SpriteSheetParameters.PLAYER_DEFAULT.getRows(),
                SpriteSheetParameters.PLAYER_DEFAULT.getCols(),
                Constants.PLAYER_DEFAULT_HEALTH,
                Constants.PLAYER_DEFAULT_HEALTH,
                0, TYPE);

        this.setFired(Constants.DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY);
        this.setHeld(Constants.DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY);
        this.setPoints(Constants.START_POINTS);

        this.setLives(lives);
        this.setScene(scene);

        //We are using SVG Path to make up for the complex form of the spaceship
        this.svgPath = new SVGPath();

        Reader SVGPathReader = new SVGPathReader();
        this.svgPath.setContent(SVGPathReader.read(Constants.PLAYER_SVGPATH_LOCATION));

        this.timer = new Timer();
        super.setSpriteParameters(
                SpriteSheetParameters.PLAYER.getWidth(),
                SpriteSheetParameters.PLAYER.getHeight(),
                SpriteSheetParameters.PLAYER.getRows(),
                SpriteSheetParameters.PLAYER.getCols()
        );
        this.loadPlayerSprites();
    }

    public void refreshSprites() {
        this.isHit = false;
        super.setSprites(this.originalSprites);
    }

    public Timer getTimer() {
        return timer;
    }

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

    public boolean isHit() {
        return this.isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
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

        BufferedImage playerUpSprites =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE_UP));

        BufferedImage playerDownSprites =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE_DOWN));

        BufferedImage playerDownHitSprites =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE_DOWN_HIT));

        BufferedImage playerUpHitSprites =
                ImageIO.read(new File(Constants.PROJECT_PATH +
                        Constants.SPACESHIP_SPRITESHEET_IMAGE_UP_HIT));

        this.playerHitSprites = this.splitSprites(playerHitSpriteSheet);
        this.playerUpSprites = this.splitSprites(playerUpSprites);
        this.playerDownSprites = this.splitSprites(playerDownSprites);
        this.playerUpHitSprites = this.splitSprites(playerUpHitSprites);
        this.playerDownHitSprites = this.splitSprites(playerDownHitSprites);

        super.setSpriteSheet(playerSpriteSheet);
        super.splitSprites();
        this.originalSprites = super.getSprites();
    }

    public void playerMove() {
        this.setSprites(this.playerHitSprites);

        if (!this.isHit) {
            if (this.isGoUp()) {
                this.setSprites(this.playerUpSprites);
            } else if (this.isGoDown()) {
                this.setSprites(this.playerDownSprites);
            }
        } else {
            if (this.isGoUp()) {
                this.setSprites(this.playerUpHitSprites);
            } else if (this.isGoDown()) {
                this.setSprites(this.playerDownHitSprites);
            }
        }
    }
}