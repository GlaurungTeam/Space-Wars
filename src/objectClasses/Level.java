package objectClasses;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import sample.Controller;
import sample.GameController;

import java.io.*;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Level {
    private ArrayList<Explosion> explosions;
    private ArrayList<Missile> missiles;
    private ArrayList<Ufo> Ufos;
    private ArrayList<Asteroid> Asteroids;
    private Group group;
    private Player player;
    private GraphicsContext gc;
    private Canvas canvas;
    private GameController gameController;
    private Scene theScene;
    private Double currentFrame;


    public Level(Group group,
                 Player player,
                 GraphicsContext gc,
                 Canvas canvas,
                 GameController gameController,
                 Scene theScene,
                 Double currentFrame) {
        this.explosions = new ArrayList<>();
        this.missiles = new ArrayList<>();
        this.Ufos = new ArrayList<>();
        this.Asteroids = new ArrayList<>();

        this.group = group;
        this.player = player;
        this.gc = gc;
        this.canvas = canvas;
        this.gameController = gameController;
        this.theScene = theScene;
        this.currentFrame = currentFrame;
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
        return Asteroids;
    }

    public void setAsteroids(ArrayList<Asteroid> asteroids) {
        Asteroids = asteroids;
    }

    public ArrayList<Ufo> getUfos() {
        return Ufos;
    }

    public void setUfos(ArrayList<Ufo> ufos) {
        Ufos = ufos;
    }

    public Group getGroup() {
        return group;
    }

    public ArrayList<Explosion> getExplosions() {
        return explosions;
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setExplosions(ArrayList<Explosion> explosions) {
        this.explosions = explosions;
    }

    public void setMissiles(ArrayList<Missile> missiles) {
        this.missiles = missiles;
    }

    public void manageAsteroids() {
        //Iterate through all asteroids
        for (Asteroid asteroidToRenderAndUpdate : this.Asteroids) {

            if (!asteroidToRenderAndUpdate.isHit) {
                asteroidToRenderAndUpdate.render(this.gc);

                if (player.checkCollision(asteroidToRenderAndUpdate.positionX, asteroidToRenderAndUpdate.positionY, 32)) {
                    asteroidToRenderAndUpdate.positionX = -1300;
                    player.resetPlayerPosition(this.canvas);

                    this.player.setLives(this.player.getLives() - 1);
                    //countDown.playFromStart(); What is that ?
                    try {
                        this.checkIfPlayerIsDead(this.player.getTheScene());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String livesC = toString().format("Lives: %d", this.player.getLives());
                    //lives.setText(livesC); // TODO create class to work with all text fields in the game scene
                    continue;

                    //TODO Change color of ship when hit, or some kind of visual effect

                    //TODO Implement ship damage tracker
                }
            }

            //Asteroid speed updating every rotation making them faster:
            Double speed = 2.5;//Hardcoded
            speed += 0.00001;
            asteroidToRenderAndUpdate.updateAsteroidLocation(canvas);
        }
    }

    public void initializeAsteroids() {
        for (int i = 0; i < 20; i++) {
            Asteroid currentAsteroid = new Asteroid(2.5);//HardCoded
            String path = "resources/asteroid/asteroid" + String.valueOf(SpawnCoordinates.getRandom(4)) + ".png";
            Image image = new Image(path);

            currentAsteroid.setImage(image);
            currentAsteroid.setPosition(SpawnCoordinates.getSpawnX(canvas), SpawnCoordinates.getSpawnY(canvas), 2.5);//HardCoded
            this.getAsteroids().add(currentAsteroid);
        }
    }

    public void manageUfos(){
        for (Ufo ufo : this.getUfos()) {
            if (!ufo.isHit) {
                ufo.render(gc);

                if (this.player.checkCollision(ufo.positionX, ufo.positionY, 32)) {
                    this.player.resetPlayerPosition(canvas);
                    ufo.positionX = -1300;

                    this.player.setLives(player.getLives() - 1);
                    //countDown.playFromStart(); TODO Manage countdown
                    try {
                        checkIfPlayerIsDead(this.theScene);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String livesC = toString().format("Lives: %d", player.getLives());
                    //lives.setText(livesC); // TODO create class to work with all text fields in the game scene
                    continue;
                }
            }
            ufo.setSpeed(ufo.getSpeed() + 0.00002);
            ufo.updateUfoLocation(canvas);
        }
    }

    public void initializeUfos() {
        for (int i = 0; i < 2; i++) {
            Ufo ufo = new Ufo(2.0);
            String path = "resources/UFO/ufo_" + String.valueOf(SpawnCoordinates.getRandom(6)) + ".png";
            Image image = new Image(path);

            ufo.setImage(image);
            ufo.setPosition(SpawnCoordinates.getSpawnX(this.canvas), SpawnCoordinates.getSpawnY(this.canvas), ufo.getSpeed());
            this.getUfos().add(ufo);
        }
    }

    public void manageMissiles(){
        if (this.missiles.size() != 0) {
            for (int i = 0; i < missiles.size(); i++) {
                Missile m = this.getMissiles().get(i);

                if (m.positionX > canvas.getWidth()) {
                    this.missiles.remove(m);
                    return;
                }

                m.setImage(m.getFrame(m.sprites, this.getCurrentFrame(), 0.100));
                m.render(this.gc);
                m.updateMissileLocation();

                //Collision detection missile hits asteroid and removes it from canvas
                for (Asteroid asteroidToCheck : this.getAsteroids()) {
                    if (asteroidToCheck.isHit) {
                        continue;
                    }

                    if (m.intersects(asteroidToCheck)) {
                        asteroidToCheck.isHit = true;

                        AudioClip explode = new AudioClip(Paths.get("src/resources/sound/explosion2.mp3").toUri().toString());
                        explode.play(0.6);

                        //Remove missile from missiles array and explode
                        Explosion explosion = new Explosion(gameController, m);
                        this.missiles.remove(m);
                        this.explosions.add(explosion);
                        //TODO Move that one to the killing aliens method to display score
                        player.setPoints(player.getPoints() + 1);
                        String score = toString().format("Score: %d", player.getPoints());
                        //scoreLine.setText(score);// TODO create class to work with all text fields in the game scene
                        //TODO Implement score tracker
                    }
                }

                for (Ufo ufoToCheck : this.getUfos()) {
                    if (ufoToCheck.isHit) {
                        continue;
                    }

                    if (m.intersects(ufoToCheck)) {
                        ufoToCheck.isHit = true;

                        AudioClip explode = new AudioClip(Paths.get("src/resources/sound/explossion.mp3").toUri().toString());
                        explode.play(0.2);

                        Explosion explosion = new Explosion(gameController, m);
                        explosion.explode();
                        this.missiles.remove(m);

                        this.player.setPoints(this.player.getPoints() + 3);
                        String score = toString().format("Score: %d", player.getPoints());
                        //scoreLine.setText(score);// TODO create class to work with all text fields in the game scene
                    }
                }
            }
        }
    }

    public void manageExplosions(){
        //Iterate through all explosions
        if (this.getExplosions().size() != 0) {
            for (int i = 0; i < this.getExplosions().size(); i++) {
                Explosion explosion = this.getExplosions().get(i);
                Image currentFrame = explosion.getCurrentExplosionFrame(explosion.currentFrameIndex);

                if (explosion.currentFrameIndex < explosion.sprites.length - 1) {
                    explosion.setImage(currentFrame);
                    explosion.render(this.gc);
                    explosion.currentFrameIndex++;
                } else {
                    explosions.remove(i);
                }
            }
        }
    }

    private void checkIfPlayerIsDead(Scene theScene) throws Exception {
        if (this.player.getLives() < 0) {
            this.gameController.stop();

            try {
                this.writeInLeaderboard(Controller.userName, this.player.getPoints());
                theScene.setRoot(FXMLLoader.load(getClass().getResource("sample.fxml")));
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc);
            }
        }
    }

    public void writeInLeaderboard(String name, long score) throws IOException {
        SortedMap<String, Long> scores = new TreeMap<>();

        Path path = Paths.get("src\\sample\\leaderBoard.txt");
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
