package entities;

import managers.GameManager;
import controllers.MenuController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import managers.GameManager;

import java.io.*;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Level {
    private ArrayList<Missile> missiles;
    private ArrayList<Ufo> ufos;
    private ArrayList<Asteroid> asteroids;
    private Group group;
    private Player player;
    private GraphicsContext gc;
    private Canvas canvas;
    private GameManager gameManager;
    private Scene scene;
    private Double currentFrame;

    public Level(Group group,
                 Player player,
                 GraphicsContext gc,
                 Canvas canvas,
                 GameManager gameManager,
                 Scene scene,
                 Double currentFrame,
                 ArrayList ufos,
                 ArrayList asteroids) {
        this.missiles = new ArrayList<>();

        this.setGroup(group);
        this.setPlayer(player);
        this.setGc(gc);
        this.setCanvas(canvas);
        this.setGameController(gameManager);
        this.setScene(scene);
        this.setCurrentFrame(currentFrame);
        this.setUfos(ufos);
        this.setAsteroids(asteroids);
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public GameManager getGameController() {
        return this.gameManager;
    }

    private void setGameController(GameManager gameController) {
        this.gameManager = gameController;
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public GraphicsContext getGc() {
        return this.gc;
    }

    private void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public Double getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(Double currentFrame) {
        this.currentFrame = currentFrame;
    }

    public Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    private void setAsteroids(ArrayList<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public ArrayList<Ufo> getUfos() {
        return ufos;
    }

    private void setUfos(ArrayList<Ufo> ufos) {
        this.ufos = ufos;
    }

    public Group getGroup() {
        return group;
    }

    private void setGroup(Group group) {
        this.group = group;
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void setMissiles(ArrayList<Missile> missiles) {
        this.missiles = missiles;
    }

    public void writeInLeaderboard(String name, long score) throws IOException {
        SortedMap<String, Long> scores = new TreeMap<>();

        try (ObjectInputStream in = new ObjectInputStream
                (new FileInputStream("src/leaderboard/leaderboard.ser"))) {
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
                .sorted(Comparator.<Map.Entry<String, Long>>comparingLong(pair -> pair.getValue()).reversed())
                .collect(Collectors.toCollection(ArrayList::new));

        try (ObjectOutputStream out = new ObjectOutputStream(
                (new FileOutputStream("src\\leaderboard\\leaderboard.ser")))) {
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