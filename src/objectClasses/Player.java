package objectClasses;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Controller;
import sample.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

        int speedMultiplier = 1;

        //Speed up if held var is true(see Main key events)
//        if (Main.held) {
//            speedMultiplier = 2;
//        }
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
}