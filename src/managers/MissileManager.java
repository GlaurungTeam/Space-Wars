package managers;

import entities.*;

public class MissileManager {
    public void manageMissiles(Level level) {
        //TODO Remove method so that the missile is removed in the manageUfos() and manageAsteroids() methods

        if (level.getMissiles().size() != 0) {
            for (int i = 0; i < level.getMissiles().size(); i++) {
                Missile currentMissile = level.getMissiles().get(i);

                if (currentMissile.getPositionX() > level.getCanvas().getWidth()) {
                    level.getMissiles().remove(currentMissile);
                    return;
                }

                currentMissile.setImage(currentMissile.getFrame(currentMissile.getSprites(),
                        level.getCurrentFrame(), 0.100));
                currentMissile.render(level.getGc());
                currentMissile.updateMissileLocation();

                //Collision detection missile hits asteroid and removes it from canvas
                for (Asteroid asteroidToCheck : level.getAsteroids()) {
                    if (asteroidToCheck.getHitStatus()) {
                        continue;
                    }

                    if (currentMissile.intersects(asteroidToCheck)) {
                        asteroidToCheck.setHitStatus(true);

                        EffectsManager.playAsteroidHit(new Explosion(currentMissile.getPositionX(), currentMissile.getPositionY()));

                        level.getMissiles().remove(currentMissile);

                        //TODO Move that one to the killing aliens method to display score

                        level.getPlayer().setPoints(level.getPlayer().getPoints() + 1);

                        //String score = String.format("Score: %d", level.getPlayer().getPoints());
                        //scoreLine.setText(score)
                        //TODO create class to work with all text fields in the game scene
                        //TODO Implement score tracker
                    }
                }

                for (Ufo ufoToCheck : level.getUfos()) {
                    if (ufoToCheck.getHitStatus()) {
                        continue;
                    }

                    if (currentMissile.intersects(ufoToCheck)) {
                        ufoToCheck.setHitStatus(true);

                        EffectsManager.playUfoHit(new Explosion(currentMissile.getPositionX(), currentMissile.getPositionY()));

                        level.getMissiles().remove(currentMissile);
                        level.getPlayer().setPoints(level.getPlayer().getPoints() + 3);
                    }
                }
            }
        }
    }
}