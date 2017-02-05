package objectClasses;

import javafx.scene.canvas.Canvas;
import sample.Main;

import java.io.IOException;

public class Player extends Sprite {

//    final int width = 82;
//    final int height = 82;
//    final int rows = 2;
//    final int cols = 3;
//    public Image[] sprites = new Image[rows * cols];
//    public BufferedImage spriteSheet;
//
//    //load sprites from image
//    public void setSprites(BufferedImage bigImg){
//        for (int i = 0; i < rows; i++)
//        {
//            for (int j = 0; j < cols; j++)
//            {
//                this.sprites[(i * cols) + j] = SwingFXUtils.toFXImage(bigImg.getSubimage(
//                        j * width,
//                        i * height,
//                        width,
//                        height
//                ), null);
//            }
//        }
//    }

    public Player() throws IOException {
    }


    public void updatePlayerLocation(Canvas canvas, boolean goUp, boolean goDown, boolean goLeft, boolean goRight) {
        //Offset Formula
        double heightOffset = canvas.getHeight() - 37;
        double widthOffset = canvas.getWidth() - 54;

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
}