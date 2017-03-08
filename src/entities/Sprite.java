package entities;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;

//The whole point of this class is so that we can use hit detection easily
//Thanks to it every object on the field has width, height, image and coordinates which we can use for the aforementioned reason
public abstract class Sprite {
    private Image image;
    private double positionX;
    private double positionY;
    private double speed;
    private int width;
    private int height;
    private int rows;
    private int cols;
    private Image[] sprites;
    private BufferedImage spriteSheet;

    public Image getImage() {
        return this.image;
    }

    public double getPositionX() {
        return this.positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return this.positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getWidth() {
        return this.width;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public int getRows() {
        return this.rows;
    }

    private void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return this.cols;
    }

    private void setCols(int cols) {
        this.cols = cols;
    }

    public Image[] getSprites() {
        return this.sprites;
    }

    protected void setSprites(Image[] sprites) {
        this.sprites = sprites;
    }

    public BufferedImage getSpriteSheet() {
        return this.spriteSheet;
    }

    public void setSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

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
        this.setWidth(width);
        this.setHeight(height);
        this.setRows(rows);
        this.setCols(cols);
        this.setSprites(new Image[rows * cols]);
    }

    public Sprite() {
    }

    public void setImage(Image image) {
        this.image = image;
        this.setWidth((int) image.getWidth());
        this.setHeight((int) image.getHeight());
    }

    public void setPosition(double x, double y) {
        this.setPositionX(x);
        this.setPositionY(y);
    }

    public Image getFrame(Image[] sprites, double time, double duration) {
        int index = (int) ((time % (sprites.length * duration)) / duration);
        return sprites[index];
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.image, this.positionX, this.positionY);
    }

    public Rectangle getBoundsAsShape() {
        return new Rectangle(this.positionX, this.positionY, this.width, this.height);
    }

    private Rectangle2D getBoundary() {
        return new Rectangle2D(this.positionX, this.positionY, this.width, this.height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return "Position: [" + this.positionX + "," + this.positionY + "]" +
                "Velocity: [" + this.speed + "]";
    }
}