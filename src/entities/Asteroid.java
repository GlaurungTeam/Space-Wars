package entities;

public class Asteroid extends Sprite {
    private boolean isHit;

    public Asteroid(double speed) {
        super.setSpeed(speed);
        this.setHitStatus(false);
    }

    public boolean getHitStatus() {
        return this.isHit;
    }

    public boolean setHitStatus(boolean isHit) {
        return this.isHit = isHit;
    }
}