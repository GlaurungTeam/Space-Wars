package objectClasses;

import javafx.scene.canvas.Canvas;
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

    public Player() throws IOException {
    }


    public void updatePlayerLocation(Canvas canvas, boolean goUp, boolean goDown, boolean goLeft, boolean goRight) {
        //Offset Formula
        double heightOffset = canvas.getHeight() - 72;
        double widthOffset = canvas.getWidth() - 82;

        int speedMultiplier = 1;

        //Speed up if held var is true(see Main key events)
        if (Main.held) {
            speedMultiplier = 2;
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
    }

    public void fire(){
        if(fired){
            return;
        }
        //Make missile
        Missile missile = new Missile();
        missile.setPosition(this.positionX + this.width / 1.2, this.positionY + this.height / 2, 2);
        //load missile sprites
        BufferedImage missileSpriteSheet = null;
        try {
            missileSpriteSheet = ImageIO.read(new File(Controller.PROJECT_PATH + "\\src\\resources\\missiles\\largeMissiles.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        missile.setSpriteParameters(31, 7, 1, 23);
        missile.loadSpriteSheet(missileSpriteSheet);
        missile.splitSprites();
        this.missiles.add(missile);
        fired = true;
    }

}