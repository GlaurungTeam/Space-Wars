package managers;

import entities.*;
import entities.enemies.Ufo;
import entities.level.Level;
import javafx.scene.canvas.Canvas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    public ArrayList<GameObject> initializeEnemies(int enemyCount, String enemyType) {

        ArrayList<GameObject> enemiesToReturn = new ArrayList<>();

        for (int i = 0; i < enemyCount; i++) {
            GameObject enemy = null;
            switch (enemyType) {
                case "ufo":
                    enemy = createUfo();
                    break;
            }
            enemiesToReturn.add(enemy);
        }
        return enemiesToReturn;
    }

    private GameObject createUfo() {//TODO "create ufo"
        BufferedImage ufoSpriteSheet = null;

        try {
            ufoSpriteSheet = ImageIO.read(new File(
                    Constants.PROJECT_PATH + Constants.UFO_SpriteSheet));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        int lowBound = 1300;
        int highBound = 2500;

        int currentXPos = this.getRandomNumber(lowBound,highBound);
        int currentYPos = this.getRandomNumber(720);

        GameObject ufo = new Ufo(currentXPos, currentYPos, Constants.UFO_SPEED_EASY, ufoSpriteSheet);

        return ufo;
    }

    private void manageEnemyCollision(Level level, GameObject enemy) {
        if (this.getPlayerManager().checkCollision(enemy)) {
            this.getPlayerManager().resetPlayerPosition(level.getCanvas(), this.getFuelManager());
            enemy.resetLocation(-1300, enemy.getPositionY());

            level.getPlayer().setLives(level.getPlayer().getLives() - 1);
            this.getPlayerManager().playerHit();
        }
    }

    private void move(GameObject enemy, Canvas canvas) {
        double heightOffset = 37;
        double offset = canvas.getHeight() - heightOffset;

        double nextXPosition = enemy.getPositionX() - enemy.getSpeed();

        //enemy.updateLocation(enemy.getPositionX() - enemy.getSpeed(), enemy.getPositionY());
        if (enemy.getPositionX() < -20) {
            enemy.updateLocation(canvas.getWidth(),this.getRandomNumber((int) offset));
            enemy.setHitStatus(false);
            return;
        }

        enemy.updateLocation(nextXPosition, enemy.getPositionY());
    }

    public void manageUfos(Level level) {
        for (GameObject enemy : level.getEnemies()) {
            if (!enemy.getHitStatus()) {
                enemy.setImage(enemy.getCurrentFrame(1));//TODO here the index of the current image is hardcoded - change this!!!
                enemy.render(level.getGc());
                this.manageEnemyCollision(level, enemy);
                this.move(enemy, level.getCanvas());
            } else{
                int low = 1300;
                int high = 2500;
                int newRandomX = this.getRandomNumber(low, high);
                int newRandomY = this.getRandomNumber(720);

                enemy.speedUp(Constants.OBJECT_SPEED_UP_VALUE);
                enemy.updateLocation(newRandomX, newRandomY);
                enemy.setHitStatus(false);
            }
        }
    }

    private int getRandomNumber(int bound){
        Random rnd = new Random();
        return rnd.nextInt(bound);
    }

    private int getRandomNumber(int lowBound, int highBound){
        Random rnd = new Random();
        return rnd.nextInt(highBound - lowBound) + lowBound;
    }
}