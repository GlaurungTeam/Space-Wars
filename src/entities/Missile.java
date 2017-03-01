package entities;

public class Missile extends Sprite {
    public Missile(double x, double y, double speed) {
        super.setPosition(x, y, speed);
    }

    public void updateMissileLocation() {
        int speedMultiplier = 2;
        super.setPositionX(Math.max(0, super.getPositionX() + super.getSpeed() * speedMultiplier));
    }
}