package Entities;

public class Missile extends Sprite {

    public Missile(double x, double y, double speed) {
        this.setPosition(x, y, speed);
    }

    public void updateMissileLocation() {
        int speedMultiplier = 2;
        this.setPositionX(Math.max(0, this.getPositionX() + this.getSpeed() * speedMultiplier));
    }
}