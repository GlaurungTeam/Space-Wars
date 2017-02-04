package objectClasses;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

//The whole point of this class is so that we can use hit detection easily
//Thanks to it every object on the field has width, height, image and coordinates which we can use for the aforementioned reason
public class Sprite {
    public Image image;
    public double positionX;
    public double positionY;
    public double speed;
    public double width;
    public double height;

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
        this.width = i.getWidth();
        this.height = i.getHeight();
    }

    public void setPosition(double x, double y, double speed) {
        this.positionX = x;
        this.positionY = y;
        this.speed = speed;
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
}