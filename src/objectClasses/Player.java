package objectClasses;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.scene.media.AudioClip;

public class Player extends Sprite {
    public ArrayList<Missile> missiles = new ArrayList<>();
    public boolean fired = false;

    public Rectangle r = new Rectangle();
    public Rectangle rv = new Rectangle();
    public Rectangle rv2 = new Rectangle();

    public Player() throws IOException {
    }

    public void updatePlayerLocation(Canvas canvas, boolean goUp, boolean goDown, boolean goLeft, boolean goRight) {
        //Offset Formula
        double heightOffset = canvas.getHeight() - 72;
        double widthOffset = canvas.getWidth() - 82;

        double speedMultiplier = 1;

        //Speed up if held var is true(see GameController key events)
        if (KeyListener.held) {
            speedMultiplier = 1.5;
        }

        if (goUp) {
            this.positionY = Math.max(0, this.positionY - this.speed * speedMultiplier);
        }
        if (goDown) {
            this.positionY = Math.min(heightOffset, this.positionY + this.speed * speedMultiplier);
        }
        if (goLeft) {
            this.positionX = Math.max(0, this.positionX - this.speed * speedMultiplier);
        }
        if (goRight) {
            this.positionX = Math.min(widthOffset, this.positionX + this.speed * speedMultiplier);
        }

        //Updates the hitbox with every player update
        this.r.setY(this.positionY + 38);
        this.r.setX(this.positionX + 13);

        this.rv.setY(this.positionY + 11);
        this.rv.setX(this.positionX + 20);

        this.rv2.setY(this.positionY + 27);
        this.rv2.setX(this.positionX + 38);
    }

    public void fire() {
        if (fired) {
            return;
        }

        AudioClip shoot = new AudioClip(Paths.get("src/resources/sound/lasergun.mp3").toUri().toString());
        shoot.play(0.7);

        //Make missile
        Missile missile = new Missile();
        missile.setPosition(this.positionX + this.width / 1.2, this.positionY + this.height / 2, 2);

        //Load missile sprites
        BufferedImage missileSpriteSheet = null;

        try {
            missileSpriteSheet = ImageIO.read(new File(Controller.PROJECT_PATH + "/src/resources/missiles/largeMissiles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        missile.setSpriteParameters(31, 7, 1, 23);
        missile.loadSpriteSheet(missileSpriteSheet);
        missile.splitSprites();

        this.missiles.add(missile);
        fired = true;
    }

    public void initializeHitboxes() {
        this.r.setWidth(57);
        this.r.setHeight(7);
        this.r.setStroke(Color.TRANSPARENT);
        this.r.setFill(Color.TRANSPARENT);

        this.rv.setWidth(4);
        this.rv.setHeight(58);
        this.rv.setStroke(Color.TRANSPARENT);
        this.rv.setFill(Color.TRANSPARENT);

        this.rv2.setWidth(3);
        this.rv2.setHeight(28);
        this.rv2.setStroke(Color.TRANSPARENT);
        this.rv2.setFill(Color.TRANSPARENT);
    }

    //Method that checks if the player collides with a given object
    public boolean checkCollision(double x, double y, int offset) {
        int hitX = (int) x;
        int hitY = (int) y;
        int mainX = (int) this.r.getX();
        int mainY = (int) this.r.getY();
        int mainX1 = (int) this.rv.getX();
        int mainY1 = (int) this.rv.getY();
        int mainX2 = (int) this.rv2.getX();
        int mainY2 = (int) this.rv2.getY();

        if ((hitX <= mainX + (int) this.r.getWidth() && hitX + offset >= mainX && hitY <= mainY + (int) this.r.getHeight() && hitY + offset >= mainY) ||
                (hitX <= mainX1 + (int) this.rv.getWidth() && hitX + offset >= mainX1 && hitY <= mainY1 + (int) this.rv.getHeight() && hitY + offset >= mainY1) ||
                (hitX <= mainX2 + (int) this.rv2.getWidth() && hitX + offset >= mainX2 && hitY <= mainY2 + (int) this.rv2.getHeight() && hitY + offset >= mainY2)
                ) {
            return true;
        }
        return false;
    }
}