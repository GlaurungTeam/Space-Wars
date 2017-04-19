package models.gameObjects;

public interface HealthableGameObject extends GameObject {

    int getHealth();

    void setHealth(int health);

    int getDefaultHealth();

    int getPointsOnKill();
}