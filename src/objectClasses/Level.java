package objectClasses;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import controllers.MenuController;
import controllers.GameController;

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
                 Double currentFrame) {
        this.explosions = new ArrayList<>();
        this.missiles = new ArrayList<>();
        this.ufos = new ArrayList<>();
        this.asteroids = new ArrayList<>();

        this.setGroup(group);
        this.setPlayer(player);
        this.setGc(gc);
        this.setCanvas(canvas);
        this.setGameController(gameController);
        this.setScene(scene);
        this.setCurrentFrame(currentFrame);

        this.initializeAsteroids();
        this.initializeUfos();
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

    public void manageAsteroids() {
<<<<<<< HEAD
        //Iterate through all asteroids
        for (Asteroid asteroidToRenderAndUpdate : this.getAsteroids()) {

            if (!asteroidToRenderAndUpdate.getHitStatus()) {
                asteroidToRenderAndUpdate.render(this.getGc());
=======
        //goes in AsteroidManager
        //TODO: Iterate through all asteroids AND remove the asteroid that was hit and/or remove the missle that was hit + add explosion to list
        for (Asteroid asteroidToRenderAndUpdate : this.Asteroids) {
>>>>>>> 5f810050fb3099e74986e24d1517bf5c85996247

                if (player.checkCollision(asteroidToRenderAndUpdate.getPositionX(),
                        asteroidToRenderAndUpdate.getPositionY(), 32)) {

                    asteroidToRenderAndUpdate.setPositionX(-1300);
                    player.resetPlayerPosition(this.getCanvas());

                    this.player.setLives(this.getPlayer().getLives() - 1);
                    //countDown.playFromStart(); What is that ?
                    try {
                        this.checkIfPlayerIsDead(this.getPlayer().getScene());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String livesC = toString().format("Lives: %d", this.getPlayer().getLives());
                    //lives.setText(livesC);
                    //TODO create class to work with all text fields in the game scene
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
            String path = "resources/asteroid/asteroid" +
                    String.valueOf(SpawnCoordinates.getRandom(4)) + ".png";
            Image image = new Image(path);

            currentAsteroid.setImage(image);
            currentAsteroid.setPosition(SpawnCoordinates.getSpawnX(canvas),
                    SpawnCoordinates.getSpawnY(canvas), 2.5);

            this.getAsteroids().add(currentAsteroid);
        }
    }

<<<<<<< HEAD
    public void manageUfos() {
=======
    public void manageUfos(){
        //goes in EnemyManager
        //TODO: Iterate through all UFOs AND remove the UFO that was hit and/or remove the missle that was hit + add explosion to list
>>>>>>> 5f810050fb3099e74986e24d1517bf5c85996247
        for (Ufo ufo : this.getUfos()) {
            if (!ufo.getHitStatus()) {
                ufo.render(gc);

                if (this.getPlayer().checkCollision(ufo.getPositionX(), ufo.getPositionY(), 32)) {
                    this.getPlayer().resetPlayerPosition(canvas);
                    ufo.setPositionX(-1300);

                    this.getPlayer().setLives(this.getPlayer().getLives() - 1);
                    //countDown.playFromStart(); TODO Manage countdown
                    try {
                        checkIfPlayerIsDead(this.getScene());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String livesC = toString().format("Lives: %d", this.getPlayer().getLives());
                    //lives.setText(livesC);
                    //TODO create class to work with all text fields in the game scene
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
            ufo.setPosition(SpawnCoordinates.getSpawnX(this.getCanvas()),
                    SpawnCoordinates.getSpawnY(this.getCanvas()), ufo.getSpeed());

            this.getUfos().add(ufo);
        }
    }

<<<<<<< HEAD
    public void manageMissiles() {
        if (this.getMissiles().size() != 0) {
=======
    public void manageMissiles(){
        //TODO:Remove method so that the missile is removed in the manageUfos() and manageAsteroids() methods
        if (this.missiles.size() != 0) {
>>>>>>> 5f810050fb3099e74986e24d1517bf5c85996247
            for (int i = 0; i < missiles.size(); i++) {
                Missile currentMissile = this.getMissiles().get(i);

                if (currentMissile.getPositionX() > this.getCanvas().getWidth()) {
                    this.getMissiles().remove(currentMissile);
                    return;
                }

                currentMissile.setImage(currentMissile.getFrame(currentMissile.getSprites(),
                        this.getCurrentFrame(), 0.100));
                currentMissile.render(this.gc);
                currentMissile.updateMissileLocation();

                //Collision detection missile hits asteroid and removes it from canvas
                for (Asteroid asteroidToCheck : this.getAsteroids()) {
                    if (asteroidToCheck.getHitStatus()) {
                        continue;
                    }

                    if (currentMissile.intersects(asteroidToCheck)) {
                        asteroidToCheck.setHitStatus(true);

                        AudioClip explode = new AudioClip
                                (Paths.get("src/resources/sound/explosion2.mp3").toUri().toString());
                        explode.play(0.6);

                        //Remove missile from missiles array and explode
                        Explosion explosion = new Explosion(this.getGameController(), currentMissile);

                        this.getMissiles().remove(currentMissile);
                        this.getExplosions().add(explosion);

                        //TODO Move that one to the killing aliens method to display score

                        this.getPlayer().setPoints(this.getPlayer().getPoints() + 1);
                        String score = toString().format("Score: %d", this.getPlayer().getPoints());

                        //scoreLine.setText(score)
                        //TODO create class to work with all text fields in the game scene
                        //TODO Implement score tracker
                    }
                }

                for (Ufo ufoToCheck : this.getUfos()) {
                    if (ufoToCheck.getHitStatus()) {
                        continue;
                    }

                    if (currentMissile.intersects(ufoToCheck)) {
                        ufoToCheck.setHitStatus(true);

                        AudioClip explode = new AudioClip
                                (Paths.get("src/resources/sound/explossion.mp3").toUri().toString());
                        explode.play(0.2);

                        Explosion explosion = new Explosion(this.getGameController(), currentMissile);
                        explosion.explode();
                        this.getMissiles().remove(currentMissile);

                        this.getPlayer().setPoints(this.getPlayer().getPoints() + 3);
                        String score = toString().format("Score: %d", this.getPlayer().getPoints());

                        //scoreLine.setText(score);
                        //TODO create class to work with all text fields in the game scene
                    }
                }
            }
        }
    }

<<<<<<< HEAD
    public void manageExplosions() {
=======
    public void manageExplosions(){
        //goes in EffectsManager
>>>>>>> 5f810050fb3099e74986e24d1517bf5c85996247
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

    private void checkIfPlayerIsDead(Scene theScene) throws Exception {
        if (this.getPlayer().getLives() < 0) {
            this.getGameController().stop();

            try {
                this.writeInLeaderboard(MenuController.userName, this.getPlayer().getPoints());
                theScene.setRoot(FXMLLoader.load(getClass().getResource("misc.fxml")));
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc);
            }
        }
    }

    public void writeInLeaderboard(String name, long score) throws IOException {
        SortedMap<String, Long> scores = new TreeMap<>();

        Path path = Paths.get("src\\misc\\leaderBoard.txt");
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