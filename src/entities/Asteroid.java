package entities;

public class Asteroid extends Sprite {
    private int health;

    public Asteroid(double speed, int health) {
        super.setSpeed(speed);
        this.setHealth(health);
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}