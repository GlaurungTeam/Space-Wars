package entities;

public class Asteroid extends Sprite {
    private int health;
    private int defaultHealth;

    public Asteroid(double speed, int health, int defaultHealth) {
        super.setSpeed(speed);
        this.setHealth(health);
        this.setDefaultHealth(defaultHealth);
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDefaultHealth() {
        return defaultHealth;
    }

    private void setDefaultHealth(int defaultHealth) {
        this.defaultHealth = defaultHealth;
    }
}