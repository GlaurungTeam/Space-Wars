package entities.enemies;
import entities.GameObject;
import java.awt.image.BufferedImage;

public class Ufo extends GameObject {
    public Ufo(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int width, int height, int rows, int cols) {
        super(positionX, positionY, objectSpeed, spriteSheet, width, height, rows, cols);
    }

    /*public Ufo(Canvas canvas, double speed) {
        super(canvas, speed, Constants.UFO_IMAGE +
                String.valueOf(SpawnCoordinates.getRandom(6)) + ".png");
    }*/

    /*private void move(Canvas canvas) {
        double heightOffset = 37;
        double offset = canvas.getHeight() - heightOffset;

        this.move(this.getPositionX() - this.getSpeed(), this.getPositionY());

        if (this.getPositionX() < -20) {
            this.move(canvas.getWidth(), super.generateRandomPosition((int) offset));
            this.setHitStatus(false);
        }
    }*/
}