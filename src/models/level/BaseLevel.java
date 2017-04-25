package models.level;

import contracts.GameObject;
import contracts.HealthableGameObject;
import contracts.Level;
import helpers.LeaderboardWriter;
import managers.*;
import contracts.Boss;
import models.gameObjects.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utils.Constants;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class BaseLevel implements Level {

    private PlayerImpl player;
    private GraphicsContext gc;
    private Canvas canvas;
    private Scene scene;
    private Double currentFrame;
    private EnemyManager enemyManager;
    private BossManager bossManager;
    private PlayerManager playerManager;
    private FuelManager fuelManager;
    private ExplosionManager explosionManager;
    private Image backgroundImage;
    private boolean isActiveBoss;
    private LeaderboardWriter leaderboardWriter;

    private List<GameObject> missiles;
    private List<HealthableGameObject> enemies;
    private List<Boss> bosses;

    public BaseLevel() {
    }

    @Override
    public void initializeLevel(
            PlayerImpl player,
            GraphicsContext gc,
            Canvas canvas,
            Scene scene,
            Double currentFrame,
            EnemyManager enemyManager,
            BossManager bossManager,
            PlayerManager playerManager,
            FuelManager fuelManager,
            ExplosionManager explosionManager) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException {

        this.player = player;
        this.gc = gc;
        this.setCanvas(canvas);
        this.scene = scene;
        this.setCurrentFrame(currentFrame);
        this.missiles = new ArrayList<>();
        this.enemies = new ArrayList<>();

        this.enemyManager = enemyManager;
        this.bossManager = bossManager;
        this.playerManager = playerManager;
        this.fuelManager = fuelManager;
        this.explosionManager = explosionManager;
        this.leaderboardWriter = new LeaderboardWriter();
        this.setDifficultyParameters();
    }

    private List<HealthableGameObject> initializeUfos(int health, int speed, int ufoCount) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        return this.enemyManager
                .getEnemyFactory()
                .createEnemies(health, speed, ufoCount, "Ufo", Constants.UFO_SPRITESHEET);
    }

    private List<HealthableGameObject> initializeAsteroids(int health, int speed, int asteroidCount) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        return this.enemyManager.
                getEnemyFactory().
                createEnemies(health, speed, asteroidCount, "Asteroid", Constants.ASTEROID_SPRITESHEET);
    }

    @Override
    public LeaderboardWriter getLeaderboardWriter() {
        return this.leaderboardWriter;
    }

    public List<HealthableGameObject> getEnemies() {
        return Collections.unmodifiableList(this.enemies);
    }

    @Override
    public void addMissile(GameObject missile) {
        this.missiles.add(missile);
    }

    @Override
    public void removeMissile(GameObject missile) {
        this.missiles.remove(missile);
    }

    @Override
    public List<Boss> getBosses() {
        return Collections.unmodifiableList(this.bosses);
    }

    @Override
    public PlayerImpl getPlayer() {
        return player;
    }

    @Override
    public GraphicsContext getGc() {
        return this.gc;
    }

    @Override
    public Canvas getCanvas() {
        return this.canvas;
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    @Override
    public Double getCurrentFrame() {
        return currentFrame;
    }

    @Override
    public void setCurrentFrame(Double currentFrame) {
        this.currentFrame = currentFrame;
    }

    @Override
    public List<GameObject> getMissiles() {
        return Collections.unmodifiableList(this.missiles);
    }

    @Override
    public Image getBackgroundImage() {
        return this.backgroundImage;
    }

    @Override
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    public boolean isActiveBoss() {
        return this.isActiveBoss;
    }

    @Override
    public void setIsActiveBoss(boolean hasActiveBoss) {
        this.isActiveBoss = hasActiveBoss;
    }

    @Override
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    @Override
    public FuelManager getFuelManager() {
        return this.fuelManager;
    }

    @Override
    public ExplosionManager getExplosionManager() {
        return this.explosionManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initializeBoss(String bossName) throws IOException,
            ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ArrayList<Boss> bosses = new ArrayList<>();

        bosses.add(this.bossManager.getBossFactory().initializeBoss(canvas, bossName));
        this.bosses = bosses;
    }

    @Override
    public void initializeEnemies(
            int asteroidsHealth, int asteroidsSpeed, int asteroidsCount,
            int ufoHealth, int ufoSpeed, int ufoCount) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException {

        List<HealthableGameObject> asteroids = this.initializeAsteroids(asteroidsHealth, asteroidsSpeed, asteroidsCount);
        List<HealthableGameObject> ufos = this.initializeUfos(ufoHealth, ufoSpeed, ufoCount);

        this.enemies.addAll(asteroids);
        this.enemies.addAll(ufos);
    }
}