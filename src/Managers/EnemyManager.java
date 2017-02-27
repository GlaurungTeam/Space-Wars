package Managers;

import Entities.Level;
import javafx.scene.canvas.Canvas;
import Entities.Player;
import Entities.Ufo;

import java.util.ArrayList;
import java.util.Random;

public class EnemyManager {
    public ArrayList<Ufo> initializeUfos(Canvas canvas) {
        ArrayList<Ufo> ufosToReturn = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Ufo ufo = new Ufo(canvas, 2);
            ufosToReturn.add(ufo);
        }
        return ufosToReturn;
    }

    public void manageUfos(Level level) {
        //TODO: Iterate through all UFOs AND remove the UFO that was hit and/or remove the missle that was hit + add explosion to list

        for (Ufo ufo : level.getUfos()) {
            if (!ufo.getHitStatus()) {
                ufo.render(level.getGc());

                this.manageUfoCollision(ufo, level.getCanvas(), level.getPlayer());
            }
            ufo.setSpeed(ufo.getSpeed() + 0.00002);
            this.updateUfoLocation(ufo, level.getCanvas());
        }
    }

    private void manageUfoCollision(Ufo ufo, Canvas canvas, Player player) {
        if (player.checkCollision(ufo.getPositionX(), ufo.getPositionY(), 32)) {
            player.resetPlayerPosition(canvas);
            ufo.setPositionX(-1300);

            player.setLives(player.getLives() - 1);

            System.out.println(player.getLives());
            //countDown.playFromStart(); TODO Manage countdown
//            try {
//                checkIfPlayerIsDead(this.getScene());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String livesC = toString().format("Lives: %d", this.getPlayer().getLives());
            //lives.setText(livesC);
            //TODO create class to work with all text fields in the game scene
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

    //Initializing UFOs in the constructor(initializeUfos() in Level)
    //Check UFO Collisions(manageUfos() in Level()) with Missle or Player and add Explosion to List<Explosion> for EffectsManager to use
    //renders UFO shots
    //must implement all methods from UFO class
}