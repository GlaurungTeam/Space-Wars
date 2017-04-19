package models.level;

import managers.*;
import models.enemies.bosses.Boss;
import models.gameObjects.*;
import utils.Constants;
import models.enemies.bosses.BaseBoss;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseLevel implements Level {

    private Player player;
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

    private List<GameObject> missiles;
    private List<HealthableGameObject> realEnemies;
    private List<Boss> bosses;

    public BaseLevel() {
    }

    @Override
    public void initializeLevel(
            Player player,
            GraphicsContext gc,
            Canvas canvas,
            Scene scene,
            Double currentFrame,
            EnemyManager enemyManager,
            BossManager bossManager,
            PlayerManager playerManager,
            FuelManager fuelManager,
            ExplosionManager explosionManager) {

        this.player = player;
        this.gc = gc;
        this.setCanvas(canvas);
        this.scene = scene;
        this.setCurrentFrame(currentFrame);
        this.missiles = new ArrayList<>();
        this.realEnemies = new ArrayList<>();

        this.enemyManager = enemyManager;
        this.bossManager = bossManager;
        this.playerManager = playerManager;
        this.fuelManager = fuelManager;
        this.explosionManager = explosionManager;

        this.setDifficultyParameters();
    }

    @Override
    public List<HealthableGameObject> getRealEnemies() {
        return Collections.unmodifiableList(this.realEnemies);
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
    public Player getPlayer() {
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

    private List<HealthableGameObject> initializeUfos(int health, int speed, int ufoCount) {
        return this.enemyManager.initializeEnemies(health, speed, ufoCount, "ufo");
    }

    private List<HealthableGameObject> initializeAsteroids(int health, int speed, int asteroidCount) {
        return this.enemyManager.initializeEnemies(health, speed, asteroidCount, "asteroid");
    }

    @Override
    public void initializeBosses() throws FileNotFoundException {
        ArrayList<Boss> bosses = new ArrayList<>();

        bosses.add(this.bossManager.initializeBoss(canvas));
        this.bosses = bosses;
    }

    @Override
    public void writeInLeaderboard(String name, long score) throws IOException {
        SortedMap<String, Long> scores = new TreeMap<>();

        try (ObjectInputStream in = new ObjectInputStream
                (new FileInputStream(Constants.PROJECT_PATH + Constants.LEADERBOARD_FILE_LOCATION))) {
            String[] allScores = (String[]) in.readObject();

            for (int i = 0; i < allScores.length; i++) {
                if (i >= 10) {
                    break;
                }
                if (allScores[i] == null) {
                    continue;
                }

                String[] scoreLineArr = allScores[i].split(":");
                String userName = scoreLineArr[0];
                Long result = Long.parseLong(scoreLineArr[1]);
                scores.put(userName, result);
            }
            scores.put(name, score);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Map.Entry<String, Long>> sortedScores = scores.entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, Long>>comparingLong(Map.Entry::getValue).reversed())
                .collect(Collectors.toCollection(ArrayList::new));

        try (ObjectOutputStream out = new ObjectOutputStream(
                (new FileOutputStream(Constants.PROJECT_PATH + Constants.LEADERBOARD_FILE_LOCATION)))) {
            Iterator it = sortedScores.iterator();

            int i = 0;

            String[] scoresToWrite = new String[10];

            while (it.hasNext() && i < scoresToWrite.length) {
                Map.Entry pair = (Map.Entry) it.next();
                scoresToWrite[i] = pair.getKey() + ":" + pair.getValue();
                i++;
            }

            out.writeObject(scoresToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeEnemies(int asteroidsHealth, int asteroidsSpeed, int asteroidsCount,
                                  int ufoHealth, int ufoSpeed, int ufoCount) {

        List<HealthableGameObject> asteroids = this.initializeAsteroids(asteroidsHealth, asteroidsSpeed, asteroidsCount);
        List<HealthableGameObject> ufos = this.initializeUfos(ufoHealth, ufoSpeed, ufoCount);

        this.realEnemies.addAll(asteroids);
        this.realEnemies.addAll(ufos);
    }
}