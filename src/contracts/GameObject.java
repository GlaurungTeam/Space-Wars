package contracts;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;
import java.util.List;

public interface GameObject {

    Image getImage();

    double getPositionX();

    double getPositionY();

    double getSpeed();

    int getWidth();

    int getHeight();

    int getRows();

    int getCols();

    String getType();

    List<Image> getSprites();

    BufferedImage getSpriteSheet();

    void setSprites(List<Image> sprites);

    void setSpriteSheet(BufferedImage spriteSheet);

    void splitSprites();

    void setSpriteParameters(int width, int height, int rows, int cols);

    void setImage(Image image);

    Image getFrame(List<Image> sprites, double time, double duration);

    void render(GraphicsContext gc);

    Rectangle getBoundsAsShape();

    Rectangle2D getBoundary();

    boolean intersects(GameObject object);

    void speedUp(double speed);

    void updateLocation(double x, double y);

    int getCurrentFrameIndex();

    void setCurrentFrameIndex(int currentFrameIndex);

    Image getCurrentExplosionFrame(int index);
}