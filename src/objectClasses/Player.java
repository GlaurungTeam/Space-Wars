package objectClasses;

import javafx.scene.canvas.Canvas;
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

    public void updateLocation(Player player, Canvas canvas, boolean goUp, boolean goDown, boolean goLeft, boolean goRight, int speed) {
        //Offset Formula
        double heightOffset = canvas.getHeight() - 37;
        double widthOffset = canvas.getWidth() - 54;

        if(goUp){
            player.y = Math.max(0, player.y - speed);
        }
        if(goDown){
            player.y = Math.min(heightOffset, player.y + speed);
        }
        if(goLeft){
            player.x = Math.max(0, player.x - speed);
        }
        if(goRight){
            player.x = Math.min(widthOffset, player.x+speed);
        }
    }
}