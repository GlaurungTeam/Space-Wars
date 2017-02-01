package objectClasses;


import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class SpaceShip{
    public double y;
    public double x;
    public double speed;

    public SpaceShip(Image[] arr, double duration, GraphicsContext gc, double frame, double x, double y, double speed) {
        AnimatedImage object = new AnimatedImage();
        object.frames = arr;
        object.duration = duration;
        GraphicsContext gc1 = gc;
        double frame1 = frame;
        this.y = y;
        this.x = x;
        this.speed = speed;
    }

    public void setX(double x){
        this.x = x;
    }

    public void drawShip(GraphicsContext gc, AnimatedImage ship, double frame, double x, double y) {
        //x = this.x * this.speed;
        gc.drawImage(ship.getFrame(frame), x, y);
    }
}
