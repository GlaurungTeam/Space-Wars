package entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class GameObject extends Sprite{
    private boolean isHit = false;
    protected Image image;

    public GameObject(Canvas canvas, double speed, String imagePath){
        super.setSpeed(speed);
        super.setPosition(SpawnCoordinates.getSpawnX(canvas),
                SpawnCoordinates.getSpawnY(canvas), this.getSpeed());
        super.setImage(new Image(imagePath));
    }

    public boolean getHitStatus() {
        return this.isHit;
    }

    public void setHitStatus(boolean isHit) {
        this.isHit = isHit;
    }
}
