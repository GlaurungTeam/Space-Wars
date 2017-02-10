package objectClasses;

public class Missile extends Sprite {

    public Missile() {
    }

    public void updateMissileLocation() {
        int speedMultiplier = 2;
        this.positionX = Math.max(0, this.positionX + this.speed * speedMultiplier);
    }
}