package entities;

public class GameObject extends Sprite{
    private boolean isHit = false;

    public boolean getHitStatus() {
        return this.isHit;
    }

    public void setHitStatus(boolean isHit) {
        this.isHit = isHit;
    }
}
