package managers;

import entities.Level;
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

    public ArrayList<Ufo> initializeUfos(Canvas canvas) {
        ArrayList<Ufo> ufosToReturn = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Ufo ufo = new Ufo(canvas, 2);
            ufosToReturn.add(ufo);
        }
        return ufosToReturn;
    }

    public void manageUfos(Level level, AnimationTimer timer) {
        //TODO Iterate through all UFOs and remove the UFO that was hit and/or remove the missile that was hit
        //Add explosion to list

        for (Ufo ufo : level.getUfos()) {
            if (!ufo.getHitStatus()) {
                ufo.render(level.getGc());

                this.manageUfoCollision(level, timer, ufo);
            }
            ufo.setSpeed(ufo.getSpeed() + 0.00002);
            this.updateUfoLocation(ufo, level.getCanvas());
        }
    }

    private void manageUfoCollision(Level level, AnimationTimer timer, Ufo ufo) {
        if (this.getPlayerManager().checkCollision(ufo.getPositionX(), ufo.getPositionY(), 32)) {
            this.getPlayerManager().resetPlayerPosition(level.getCanvas(), this.getFuelManager());
            ufo.setPositionX(-1300);

            level.getPlayer().setLives(level.getPlayer().getLives() - 1);

            try {
                this.getPlayerManager().checkIfPlayerIsDead(level, timer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUfoLocation(Ufo ufo, Canvas canvas) {
        double heightOffset = 37;
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        ufo.setPositionX(ufo.getPositionX() - ufo.getSpeed());

        if (ufo.getPositionX() < -20) {
            ufo.setPositionX(canvas.getWidth());
            ufo.setPositionY(rnd.nextInt((int) offset));
            ufo.setHitStatus(false);
        }
    }
}