package entities.level;

import entities.*;
import entities.enemies.Asteroid;
import entities.enemies.bosses.Boss;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import managers.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Level {
    private Player player;
    private GraphicsContext gc;
    private Canvas canvas;
    private Scene scene;
    private Double currentFrame;
    private EnemyManager enemyManager;
    private BossManager bossManager;
    private PlayerManager playerManager;
    private FuelManager fuelManager;
    private Image backgroundImage;
    private boolean isActiveBoss;

    private List<Missile> missiles;
    private List<HealthAbleGameObject> realEnemies;
    private List<Boss> bosses;

    public Level() {
    }

    public void initializeLevel(
            Player player,
            GraphicsContext gc,
            Canvas canvas,
            Scene scene,
            Double currentFrame,
            EnemyManager enemyManager,
            BossManager bossManager,
            PlayerManager playerManager,
            FuelManager fuelManager) {
        this.setPlayer(player);
        this.setGc(gc);
        this.setCanvas(canvas);
        this.setScene(scene);
        this.setCurrentFrame(currentFrame);
        this.setMissiles(new ArrayList<>());
        this.setEnemyManager(enemyManager);
        this.setBossManager(bossManager);
        this.setPlayerManager(playerManager);
        this.setFuelManager(fuelManager);
        this.realEnemies = new ArrayList<>();
    }

    public List<HealthAbleGameObject> getRealEnemies() {
        return Collections.unmodifiableList(this.realEnemies);
    }

    public void addMissile(Missile missile) {
        this.missiles.add(missile);
    }

    public void removeMissile(Missile missile) {
        this.missiles.remove(missile);
    }

    public List<Boss> getBosses() {
        return Collections.unmodifiableList(this.bosses);
    }

    private void setBosses(ArrayList<Boss> bosses) {
        this.bosses = bosses;
    }

    public abstract void setDifficultyParameters();

    public Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    public GraphicsContext getGc() {
        return this.gc;
    }

    private void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Scene getScene() {
        return this.scene;
    }

    private void setScene(Scene scene) {
        this.scene = scene;
    }

    public Double getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(Double currentFrame) {
        this.currentFrame = currentFrame;
    }

    public List<Missile> getMissiles() {
        return Collections.unmodifiableList(this.missiles);
    }

    private void setMissiles(ArrayList<Missile> missiles) {
        this.missiles = missiles;
    }

    public Image getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public boolean isActiveBoss() {
        return this.isActiveBoss;
    }

    public void setIsActiveBoss(boolean hasActiveBoss) {
        this.isActiveBoss = hasActiveBoss;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    private void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public FuelManager getFuelManager() {
        return this.fuelManager;
    }

    private void setFuelManager(FuelManager fuelManager) {
        this.fuelManager = fuelManager;
    }

    public EnemyManager getEnemyManager() {
        return this.enemyManager;
    }

    private void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public BossManager getBossManager() {
        return this.bossManager;
    }

    private void setBossManager(BossManager bossManager) {
        this.bossManager = bossManager;
    }

    public List<HealthAbleGameObject> initializeUfos(int health, int ufoCount) {
        List<HealthAbleGameObject> ufos = this.getEnemyManager().initializeEnemies(health, ufoCount, "ufo");

        return ufos;
    }

    public List<HealthAbleGameObject> initializeAsteroids(int health, int asteroidCount) {
        List<HealthAbleGameObject> asteroids = this.getEnemyManager().initializeEnemies(health, asteroidCount, "asteroid");

        return asteroids;
    }

    public void initializeBosses() throws FileNotFoundException {
        ArrayList<Boss> bosses = new ArrayList<>();
        bosses.add(this.getBossManager().initializeBoss(canvas));

        this.setBosses(bosses);
    }

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

            while (it.hasNext() && i < 10) {
                Map.Entry pair = (Map.Entry) it.next();
                scoresToWrite[i] = pair.getKey() + ":" + pair.getValue();
                i++;
            }

            out.writeObject(scoresToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeEnemies(int asteroidsHealth, int asteroidsCount, int ufoHealth, int ufoCount) {
        List<HealthAbleGameObject> asteroids = this.initializeAsteroids(asteroidsHealth, asteroidsCount);
        List<HealthAbleGameObject> ufos = this.initializeUfos(ufoHealth, ufoCount);

        this.realEnemies.addAll(asteroids);
        this.realEnemies.addAll(ufos);
    }
}