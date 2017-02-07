package objectClasses;

import javafx.scene.canvas.Canvas;

import java.util.Random;

public class Asteroid extends Sprite {

    public boolean isHit = false;

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
            this.isHit = false;
        }
    }

    public boolean checkCollision(Player player) {
        int hitX = (int) this.positionX;
        int hitY = (int) this.positionY;
        int mainX = (int) player.r.getX();
        int mainY = (int) player.r.getY();
        int mainX1 = (int) player.rv.getX();
        int mainY1 = (int) player.rv.getY();
        int mainX2 = (int) player.rv2.getX();
        int mainY2 = (int) player.rv2.getY();

        if ((hitX <= mainX + (int) player.r.getWidth() && hitX + 32 >= mainX && hitY <= mainY + (int) player.r.getHeight() && hitY + 32 >= mainY) ||
                (hitX <= mainX1 + (int) player.rv.getWidth() && hitX + 32 >= mainX1 && hitY <= mainY1 + (int) player.rv.getHeight() && hitY + 32 >= mainY1) ||
                (hitX <= mainX2 + (int) player.rv2.getWidth() && hitX + 32 >= mainX2 && hitY <= mainY2 + (int) player.rv2.getHeight() && hitY + 32 >= mainY2)
                ) {
            return true;
        }
        return false;
    }
}