package entities;

import controllers.GameController;
import controllers.MenuController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Level {
    private ArrayList<Explosion> explosions;
    private ArrayList<Missile> missiles;
    private ArrayList<Ufo> ufos;
    private ArrayList<Asteroid> asteroids;
    private Group group;
    private Player player;
    private GraphicsContext gc;
    private Canvas canvas;
    private GameController gameController;
    private Scene scene;
    private Double currentFrame;

    public Level(Group group,
                 Player player,
                 GraphicsContext gc,
                 Canvas canvas,
                 GameController gameController,
                 Scene scene,
                 Double currentFrame,
                 ArrayList ufos,
                 ArrayList asteroids) {
        this.explosions = new ArrayList<>();
        this.missiles = new ArrayList<>();

        this.setGroup(group);
        this.setPlayer(player);
        this.setGc(gc);
        this.setCanvas(canvas);
        this.setGameController(gameController);
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

    public GameController getGameController() {
        return this.gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
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

    public void setGc(GraphicsContext gc) {
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(ArrayList<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public ArrayList<Ufo> getUfos() {
        return ufos;
    }

    public void setUfos(ArrayList<Ufo> ufos) {
        this.ufos = ufos;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public ArrayList<Explosion> getExplosions() {
        return explosions;
    }

    public void setExplosions(ArrayList<Explosion> explosions) {
        this.explosions = explosions;
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void setMissiles(ArrayList<Missile> missiles) {
        this.missiles = missiles;
    }

    public void manageExplosions() {
        //goes in EffectsManager
        //Iterate through all explosions
        if (this.getExplosions().size() != 0) {
            for (int i = 0; i < this.getExplosions().size(); i++) {
                Explosion explosion = this.getExplosions().get(i);
                Image currentFrame = explosion.getCurrentExplosionFrame(explosion.getCurrentFrameIndex());

                if (explosion.getCurrentFrameIndex() < explosion.getSprites().length - 1) {
                    explosion.setImage(currentFrame);
                    explosion.render(this.gc);
                    explosion.setCurrentFrameIndex(explosion.getCurrentFrameIndex() + 1);
                } else {
                    explosions.remove(i);
                }
            }
        }
    }

    public void checkIfPlayerIsDead(Scene theScene, AnimationTimer timer) throws Exception {
        if (this.getPlayer().getLives() <= 0) {
            timer.stop();

            try {
                theScene.setRoot(FXMLLoader.load(getClass().getResource("../views/sample.fxml")));
                this.writeInLeaderboard(MenuController.userName, this.getPlayer().getPoints());
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc);
            }
        }
    }

    public void writeInLeaderboard(String name, long score) throws IOException {
        SortedMap<String, Long> scores = new TreeMap<>();

        Path path = Paths.get("src\\views\\leaderBoard.txt");
        Path realPath = path.toRealPath(LinkOption.NOFOLLOW_LINKS);

        try (BufferedReader in = new BufferedReader(new FileReader(realPath.toString()))) {
            String scoreLine = in.readLine();
            int i = 0;

            while (scoreLine != null && i < 10) {
                String[] scoreLineArr = scoreLine.split(":");
                String userName = scoreLineArr[0];
                Long result = Long.parseLong(scoreLineArr[1]);
                scores.put(userName, result);
                scoreLine = in.readLine();
                i++;
            }

            scores.put(name, score);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Map.Entry<String, Long>> sortedScores = scores.entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, Long>>comparingLong(pair -> pair.getValue()).reversed())
                .collect(Collectors.toCollection(ArrayList::new));

        try (PrintWriter out = new PrintWriter(new FileWriter(realPath.toString()))) {
            Iterator it = sortedScores.iterator();

            int i = 0;

            while (it.hasNext() && i < 10) {
                Map.Entry pair = (Map.Entry) it.next();
                out.println(pair.getKey() + ":" + pair.getValue());
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}