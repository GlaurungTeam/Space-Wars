package objectClasses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {
    public double y;
    public double x;

    public Player(Image[] arr, double duration, GraphicsContext gc, double frame, double x, double y) {
        AnimatedImage object = new AnimatedImage();
        object.frames = arr;
        object.duration = duration;
        this.y = y;
        this.x = x;
    }

    public void drawPlayer(GraphicsContext gc, AnimatedImage player, double frame, double x, double y) {
        gc.drawImage(player.getFrame(frame), x, y);
    }
}