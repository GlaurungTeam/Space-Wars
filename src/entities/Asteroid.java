package entities;

public class Asteroid extends Sprite {
    private boolean isHit = false;

    public Asteroid(double speed) {
        super.setSpeed(speed);
        this.setHitStatus(false);
    }

    public boolean getHitStatus() {
        return this.isHit;
    }

    public void setHitStatus(boolean isHit) {
        this.isHit = isHit;
    }

}