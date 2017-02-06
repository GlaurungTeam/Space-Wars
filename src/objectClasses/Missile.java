package objectClasses;

import javafx.scene.canvas.Canvas;

public class Missile extends Sprite{

    public Missile(){

    }

    public void updateMissileLocation() {
        //Offset Formula
        /*double heightOffset = canvas.getHeight() - 72;
        double widthOffset = canvas.getWidth() - 82;*/
        int speedMultiplier = 2;
        this.positionX = Math.max(0, this.positionX + this.speed * speedMultiplier);
    }
}
