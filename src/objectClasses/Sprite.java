package objectClasses;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

//The whole point of this class is so that we can use hit detection easily
//Thanks to it every object on the field has width, height, image and coordinates which we can use for the aforementioned reason
public class Sprite {
    public Image image;
    public double positionX;
    public double positionY;
    public double speed;
    public int width;
    public int height;
    public int rows;
    public int cols;
    public Image[] sprites;
    public BufferedImage spriteSheet;

    //load sprites from image
    public void splitSprites() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.sprites[(i * cols) + j] = SwingFXUtils.toFXImage(this.spriteSheet.getSubimage(
                        j * this.width,
                        i * this.height,
                        this.width,
                        this.height
                ), null);
            }
        }
    }

    public void setSpriteParameters(int width, int height, int rows, int cols) {
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        this.sprites = new Image[rows * cols];
    }

    public Sprite() {
    }

    public Sprite(Image image, double x, double y, double speed) {
        this.setImage(image);
        this.positionX = x;
        this.positionY = y;
        this.speed = speed;
    }

    public void setImage(Image i) {
        this.image = i;
        this.width = (int) i.getWidth();
        this.height = (int) i.getHeight();
    }

    public void setPosition(double x, double y, double speed) {
        this.positionX = x;
        this.positionY = y;
        this.speed = speed;
    }

    public Image getFrame(Image[] sprites, double time, double duration) {
        int index = (int) ((time % (sprites.length * duration)) / duration);
        return sprites[index];
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.image, this.positionX, this.positionY);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(this.positionX, this.positionY, this.width, this.height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return "Position: [" + this.positionX + "," + this.positionY + "]" +
                "Velocity: [" + this.speed + "]";
    }

    //Load spritesheet
    public void loadSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }
}