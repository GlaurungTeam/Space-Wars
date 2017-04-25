package contracts;

public interface HealthableGameObject extends GameObject {

    int getHealth();

    void revive();

    void decrementHealth();

    int getPointsOnKill();
}