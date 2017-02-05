package objectClasses;

import javafx.scene.canvas.Canvas;

import java.util.Random;

public class Asteroid extends Sprite {

    // Experimental asteroid animation feature
//    public static Image[] sprites;
//    public static BufferedImage spriteSheet;
//
//    public static void splitAsteroidSprites(int rows, int cols, int width, int height) {
//        Asteroid.sprites = new Image[rows*cols];
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                Asteroid.sprites[(i * cols) + j] = SwingFXUtils.toFXImage(Asteroid.spriteSheet.getSubimage(
//                        j * width,
//                        i * height,
//                        width,
//                        height
//                ), null);
//            }
//        }
//    }
//
//    public static void loadAsteroidSpriteSheet(BufferedImage spriteSheet) {
//        Asteroid.spriteSheet = spriteSheet;
//    }


    public void updateAsteroidLocation(Canvas canvas) {

        //Offset so that asteroids don't spawn outside of boundaries
        double heightOffset = 37;

        //Offset Formula
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        this.positionX -= this.speed;

        if (this.positionX < -20) {
            this.positionX = canvas.getWidth();
            this.positionY = rnd.nextInt((int) offset);
        }
    }
}