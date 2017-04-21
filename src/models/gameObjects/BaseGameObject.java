package models.gameObjects;

import contracts.GameObject;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseGameObject implements GameObject {
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
    private int currentFrameIndex;
    private String type;

    protected BaseGameObject(double positionX, double positionY, double objectSpeed,
                             BufferedImage spriteSheet, int width, int height,
                             int rows, int cols, String type) {

        this.updateLocation(positionX, positionY);
        this.speed = objectSpeed;
        this.setSpriteSheet(spriteSheet);
        this.setSpriteParameters(width, height, rows, cols);
        this.splitSprites();
        this.type = type;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getPositionX() {
        return this.positionX;
    }

    @Override
    public double getPositionY() {
        return this.positionY;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getCols() {
        return this.cols;
    }

    @Override
    public List<Image> getSprites() {
        return Collections.unmodifiableList(this.sprites);
    }

    @Override
    public void setSprites(List<Image> sprites) {
        this.sprites = sprites;
    }

    @Override
    public BufferedImage getSpriteSheet() {
        return this.spriteSheet;
    }

    @Override
    public void setSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void splitSprites() {
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

    @Override
    public void setSpriteParameters(int width, int height, int rows, int cols) {
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        this.sprites = new ArrayList<>();
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
        this.width = (int) image.getWidth();
        this.height = (int) image.getHeight();
    }

    public Image getFrame(List<Image> sprites, double time, double duration) {
        int index = (int) ((time % (sprites.size() * duration)) / duration);
        return sprites.get(index);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(this.image, this.positionX, this.positionY);
    }

    @Override
    public Rectangle getBoundsAsShape() {
        return new Rectangle(this.positionX, this.positionY, this.width, this.height);
    }

    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(this.positionX, this.positionY, this.width, this.height);
    }

    @Override
    public boolean intersects(GameObject object) {
        return object.getBoundary().intersects(this.getBoundary());
    }

    @Override
    public void speedUp(double speed) {
        this.speed = this.speed + speed;
    }

    @Override
    public void updateLocation(double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }

    @Override
    public int getCurrentFrameIndex() {
        return this.currentFrameIndex;
    }

    @Override
    public void setCurrentFrameIndex(int currentFrameIndex) {
        this.currentFrameIndex = currentFrameIndex;
    }

    @Override
    public Image getCurrentExplosionFrame(int index) {
        return this.getSprites().get(index);
    }
}