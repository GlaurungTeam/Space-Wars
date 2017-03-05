package managers;

import entities.GameObject;
import entities.Level;
import entities.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import entities.Ufo;

import java.util.ArrayList;
import java.util.Random;

public class EnemyManager {
    //Initializing UFOs in the constructor
    //Check UFO Collisions(manageUfos() in Level()) with missile or player and add Explosion to List<Explosion> for the EffectsManager class
    //Renders UFO shots
    //Must implement all methods from UFO class
    private PlayerManager playerManager;
    private FuelManager fuelManager;

    private PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    private void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    private FuelManager getFuelManager() {
        return this.fuelManager;
    }

    private void setFuelManager(FuelManager fuelManager) {
        this.fuelManager = fuelManager;
    }

    public EnemyManager(PlayerManager playerManager, FuelManager fuelManager) {
        this.setPlayerManager(playerManager);
        this.setFuelManager(fuelManager);
    }

    public ArrayList<GameObject> initializeEnemies(
            Canvas canvas, int enemyCount, int enemySpeed, String enemyType) {

        ArrayList<GameObject> enemiesToReturn = new ArrayList<>();

        for (int i = 0; i < enemyCount; i++) {
            GameObject enemy = null;
            switch (enemyType) {
                case "ufo":
                    enemy = new Ufo(canvas, enemySpeed);
                    break;
            }
            enemiesToReturn.add(enemy);
        }
        return enemiesToReturn;
    }

    private void manageEnemyCollision(Level level, AnimationTimer timer, GameObject enemy) {
        if (this.getPlayerManager().checkCollision(enemy.getPositionX(), enemy.getPositionY(), 32)) {
            this.getPlayerManager().resetPlayerPosition(level.getCanvas(), this.getFuelManager());
            enemy.setPositionX(-1300);

            level.getPlayer().setLives(level.getPlayer().getLives() - 1);

            try {
                this.getPlayerManager().checkIfPlayerIsDead(level, timer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateEnemyLocation(GameObject enemy, Canvas canvas) {
        double heightOffset = 37;
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        enemy.setPositionX(enemy.getPositionX() - enemy.getSpeed());

        if (enemy.getPositionX() < -20) {
            enemy.setPositionX(canvas.getWidth());
            enemy.setPositionY(rnd.nextInt((int) offset));
            enemy.setHitStatus(false);
        }
    }

    public void manageUfos(Level level, AnimationTimer timer) {
        //TODO Iterate through all UFOs and remove the UFO that was hit and/or remove the missile that was hit
        //Add explosion to list

        for (GameObject enemy : level.getUfos()) {
            if (!enemy.getHitStatus()) {
                enemy.render(level.getGc());

                this.manageEnemyCollision(level, timer, enemy);
            }
            enemy.setSpeed(enemy.getSpeed() + 0.00002);
            this.updateEnemyLocation(enemy, level.getCanvas());
        }
    }
}