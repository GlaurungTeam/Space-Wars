package Entities;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.util.Timer;

public class Player extends Sprite {
    private static final boolean DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY = false;
    private static final long START_POINTS = 0;

    private Integer lives;
    private long points;
    private Scene scene;
    private boolean fired;
    private Timer timer;

    private boolean goLeft;
    private boolean goRight;
    private boolean goUp;
    private boolean goDown;
    private boolean held;

    private Rectangle r;
    private Rectangle rv;
    private Rectangle rv2;

    public Player(Double speed, Integer lives, Scene scene) throws IOException {
        this.fired = DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY;
        this.held = DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY;
        this.points = START_POINTS;
        this.setSpeed(speed);
        this.setLives(lives);
        this.setScene(scene);
        this.timer = new Timer();
        this.r = new Rectangle();
        this.rv = new Rectangle();
        this.rv2 = new Rectangle();
    }

    public Timer getTimer() {
        return timer;
    }

    //Collision rectangles getters
    public Rectangle getR() {
        return this.r;
    }

    public Rectangle getRv() {
        return this.rv;
    }

    public Rectangle getRv2() {
        return this.rv2;
    }

    //Movement getters/setters
    public boolean isGoLeft() {
        return this.goLeft;
    }

    public void setGoLeft(boolean goLeft) {
        this.goLeft = goLeft;
    }

    public boolean isGoRight() {
        return this.goRight;
    }

    public void setGoRight(boolean goRight) {
        this.goRight = goRight;
    }

    public boolean isGoUp() {
        return this.goUp;
    }

    public void setGoUp(boolean goUp) {
        this.goUp = goUp;
    }

    public boolean isGoDown() {
        return this.goDown;
    }

    public void setGoDown(boolean goDown) {
        this.goDown = goDown;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public Integer getLives() {
        return lives;
    }

    public void setLives(Integer lives) {
        this.lives = lives;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setHeld(boolean value){
        this.held = value;
    }

    public boolean isHeld(){
        return this.held;
    }

}