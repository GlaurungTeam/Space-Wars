package entities.level;

import entities.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import managers.AsteroidManager;
import managers.EnemyManager;

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
    private AsteroidManager asteroidManager;
    private Image backgroundImage;

    private ArrayList<Missile> missiles;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<GameObject> enemies;

    public Level() {
    }

    public void initializeLevel(
            Player player,
            GraphicsContext gc,
            Canvas canvas,
            Scene scene,
            Double currentFrame,
            EnemyManager enemyManager,
            AsteroidManager asteroidManager) {
        this.setPlayer(player);
        this.setGc(gc);
        this.setCanvas(canvas);
        this.setScene(scene);
        this.setCurrentFrame(currentFrame);
        this.missiles = new ArrayList<>();
        this.enemyManager = enemyManager;
        this.asteroidManager = asteroidManager;
    }

    public abstract void setDifficultyParameters();

    public ArrayList<GameObject> getEnemies() {
        return this.enemies;
    }

    private void setEnemies(ArrayList<GameObject> ufos) {
        this.enemies = ufos;
    }

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

    public ArrayList<Missile> getMissiles() {
        return this.missiles;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return this.asteroids;
    }

    private void setAsteroids(ArrayList<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public Image getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void writeInLeaderboard(String name, long score) throws IOException {
        SortedMap<String, Long> scores = new TreeMap<>();

        try (ObjectInputStream in = new ObjectInputStream
                (new FileInputStream(Constants.LEADERBOARD_FILE_LOCATION))) {
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
                (new FileOutputStream(Constants.LEADERBOARD_FILE_LOCATION)))) {
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

    public void initializeUfos(int ufoCount, int ufoSpeed) {
        ArrayList<GameObject> ufos = enemyManager.initializeEnemies(
                canvas, ufoCount, ufoSpeed, "ufo");

        this.setEnemies(ufos);
    }

    public void initializeAsteroids(int health, int asteroidCount) {
        ArrayList<Asteroid> asteroids = asteroidManager.initializeAsteroids(canvas, health, asteroidCount);

        this.setAsteroids(asteroids);
    }
}