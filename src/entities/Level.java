package entities;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import managers.GameManager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Level {
    private Group group;
    private Player player;
    private GraphicsContext gc;
    private Canvas canvas;
    private GameManager gameManager;
    private Scene scene;
    private Double currentFrame;
    private GameManager gameController;

    private ArrayList<Missile> missiles;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<GameObject> enemies;

    public Level(Group group,
                 Player player,
                 GraphicsContext gc,
                 Canvas canvas,
                 GameManager gameManager,
                 Scene scene,
                 Double currentFrame,
                 ArrayList<Asteroid> asteroids,
                 ArrayList<GameObject> enemies) {
        this.setGroup(group);
        this.setPlayer(player);
        this.setGc(gc);
        this.setCanvas(canvas);
        this.setGameController(gameManager);
        this.setScene(scene);
        this.setCurrentFrame(currentFrame);
        this.missiles = new ArrayList<>();
        this.setAsteroids(asteroids);
        this.setEnemies(enemies);
    }


    public ArrayList<GameObject> getEnemies() {
        return this.enemies;
    }

    private void setEnemies(ArrayList<GameObject> ufos) {
        this.enemies = ufos;
    }

    private void setGroup(Group group) {
        this.group = group;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    private void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    private void setGameController(GameManager gameController) {
        this.gameController = gameController;
    }

    private void setScene(Scene scene) {
        this.scene = scene;
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

    public Canvas getCanvas() {
        return this.canvas;
    }

    public Scene getScene() {
        return this.scene;
    }

    public GraphicsContext getGc() {
        return this.gc;
    }

    public Double getCurrentFrame() {
        return currentFrame;
    }

    public Player getPlayer() {
        return player;
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
}
