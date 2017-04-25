package contracts;

import javafx.scene.input.KeyCode;

import java.util.Timer;

public interface Player {

    void manageSpriteAnimation();

    void takeHit();

    void shiftLightningSpeed(boolean value);

    boolean isLightningSpeedOn();

    void incrementKillPoints(long points);

    long getPoints();

    void decrementLives();

    int getCurrentLives();

    boolean isFired();

    boolean isTurningDown();

    boolean isTurningUp();

    boolean isTurningRight();

    boolean isTurningLeft();

    void refreshSprites();

    void changeFiredStatus(boolean fired);

    void setDirection(boolean value, KeyCode keyCode);

    Timer getTimer();
}