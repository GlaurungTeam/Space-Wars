package contracts;

import contracts.GameObject;

public interface HealthableGameObject extends GameObject {

    int getHealth();

    void revive();

    void decrementHealth();

    int getPointsOnKill();
}