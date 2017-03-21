package entities;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private List<Image> sprites;
    private BufferedImage spriteSheet;

    protected Sprite(double positionX, double positionY, double objectSpeed, BufferedImage spriteSheet, int width, int height, int rows, int cols){
        //TODO initialize sprite object in the constructor
        this.setPositionX(positionX);
        this.setPositionY(positionY);
        this.setSpeed(objectSpeed);
        this.setSpriteSheet(spriteSheet);
        this.setSpriteParameters(width,height,rows,cols);
        this.splitSprites();
    }

    public Image getImage() {
        return this.image;
    }

    public double getPositionX() {
        return this.positionX;
    }

    private void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return this.positionY;
    }

    private void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getSpeed() {
        return this.speed;
    }

    private void setSpeed(double speed) {
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

    public List<Image> getSprites() {
        return Collections.unmodifiableList(this.sprites);
    }

    protected void setSprites(List<Image> sprites) {
        this.sprites = sprites;
    }

    public BufferedImage getSpriteSheet() {
        return this.spriteSheet;
    }

    protected void setSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    //load sprites from image
    protected void splitSprites() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                BufferedImage image = this.spriteSheet.getSubimage(
                        j * this.width,
                        i * this.height,
                        this.width,
                        this.height
                );
                this.sprites.add((i * this.cols) + j, SwingFXUtils.toFXImage(image, null));
            }
        }
    }

    public void setSpriteParameters(int width, int height, int rows, int cols) {
        this.setWidth(width);
        this.setHeight(height);
        this.setRows(rows);
        this.setCols(cols);
        this.sprites = new ArrayList<>();
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

    public Image getFrame(List<Image> sprites, double time, double duration) {
        int index = (int) ((time % (sprites.size() * duration)) / duration);
        return sprites.get(index);
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

    public void speedUp(double speed){
        this.setSpeed(this.getSpeed() + speed);
    }

    public void updateLocation(double x, double y){
        this.setPositionX(x);
        this.setPositionY(y);
    }

    public String toString() {
        return "Position: [" + this.positionX + "," + this.positionY + "]" +
                "Velocity: [" + this.speed + "]";
    }
}