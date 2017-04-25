package contracts;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;
import java.util.List;

public interface GameObject {

    Image getImage();

    void setImage(Image image);

    double getPositionX();

    double getPositionY();

    double getSpeed();

    int getWidth();

    int getHeight();

    int getRows();

    int getCols();

    List<Image> getSprites();

    void setSprites(List<Image> sprites);

    BufferedImage getSpriteSheet();

    void setSpriteSheet(BufferedImage spriteSheet);

    String getType();

    Image getFrame(List<Image> sprites, double time, double duration);

    int getCurrentFrameIndex();

    void setCurrentFrameIndex(int currentFrameIndex);

    void splitSprites();

    void setSpriteParameters(int width, int height, int rows, int cols);

    Image getRandomImageFromSpritesheet();

    Image getImageFromSpritesheet(int index);

    void updateLocation(double x, double y);

    void speedUp(double speed);

    boolean intersects(GameObject object);

    Rectangle getBoundsAsShape();

    Rectangle2D getBoundary();

    void render(GraphicsContext gc);
}