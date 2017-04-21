package models.gameObjects;

import enums.SpriteSheetParameters;
import helpers.Reader;
import helpers.SVGPathReader;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.SVGPath;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerImpl extends BaseHealthableGameObject implements Player {

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

    public PlayerImpl(double positionX, double positionY, double objectSpeed, int lives, Scene scene) throws IOException {
        super(positionX, positionY, objectSpeed, null,
                SpriteSheetParameters.PLAYER_DEFAULT.getWidth(),
                SpriteSheetParameters.PLAYER_DEFAULT.getHeight(),
                SpriteSheetParameters.PLAYER_DEFAULT.getRows(),
                SpriteSheetParameters.PLAYER_DEFAULT.getCols(),
                Constants.PLAYER_DEFAULT_HEALTH,
                Constants.PLAYER_DEFAULT_HEALTH,
                0, TYPE);

        this.changeFiredStatus(Constants.DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY);
        this.shiftLightningSpeed(Constants.DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY);
        this.points = Constants.START_POINTS;

        this.lives = lives;
        this.scene = scene;

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

    @Override
    public void refreshSprites() {
        this.isHit = false;
        super.setSprites(this.originalSprites);
    }

    @Override
    public Timer getTimer() {
        return this.timer;
    }

    @Override
    public boolean isTurningLeft() {
        return this.goLeft;
    }

    @Override
    public boolean isTurningRight() {
        return this.goRight;
    }

    @Override
    public boolean isTurningUp() {
        return this.goUp;
    }

    @Override
    public boolean isTurningDown() {
        return this.goDown;
    }

    @Override
    public void setDirection(boolean value, KeyCode keyCode) {
        switch (keyCode) {
            case UP:
                this.goUp = value;
                break;
            case DOWN:
                this.goDown = value;
                break;
            case LEFT:
                this.goLeft = value;
                break;
            case RIGHT:
                this.goRight = value;
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isFired() {
        return this.fired;
    }

    @Override
    public void changeFiredStatus(boolean fired) {
        this.fired = fired;
    }

    @Override
    public int getCurrentLives() {
        return this.lives;
    }

    @Override
    public void decrementLives() {
        this.lives--;
    }

    @Override
    public long getPoints() {
        return this.points;
    }

    @Override
    public void incrementKillPoints(long points) {
        this.points += points;
    }

    @Override
    public boolean isLightningSpeedOn() {
        return this.held;
    }

    @Override
    public void shiftLightningSpeed(boolean value) {
        this.held = value;
    }

    @Override
    public void hitPlayer() {
        isHit = true;
    }

    @Override
    public void manageSpriteAnimation() {
        this.setSprites(this.playerHitSprites);

        if (!this.isHit) {
            if (this.isTurningUp()) {
                this.setSprites(this.playerUpSprites);
            } else if (this.isTurningDown()) {
                this.setSprites(this.playerDownSprites);
            }
        } else {
            if (this.isTurningUp()) {
                this.setSprites(this.playerUpHitSprites);
            } else if (this.isTurningDown()) {
                this.setSprites(this.playerDownHitSprites);
            }
        }
    }
}