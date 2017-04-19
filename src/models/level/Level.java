package models.level;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import managers.*;
import models.enemies.bosses.BaseBoss;
import models.enemies.bosses.Boss;
import models.gameObjects.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Level {

    void initializeLevel(Player player, GraphicsContext gc, Canvas canvas, Scene scene, Double currentFrame,
                         EnemyManager enemyManager, BossManager bossManager, PlayerManager playerManager,
                         FuelManager fuelManager, ExplosionManager explosionManager);

    List<HealthableGameObject> getRealEnemies();

    void addMissile(GameObject missile);

    void removeMissile(GameObject missile);

    List<Boss> getBosses();

    void setDifficultyParameters();

    Player getPlayer();

    GraphicsContext getGc();

    Canvas getCanvas();

    void setCanvas(Canvas canvas);

    Scene getScene();

    Double getCurrentFrame();

    void setCurrentFrame(Double currentFrame);

    List<GameObject> getMissiles();

    Image getBackgroundImage();

    void setBackgroundImage(Image backgroundImage);

    boolean isActiveBoss();

    void setIsActiveBoss(boolean hasActiveBoss);

    PlayerManager getPlayerManager();

    FuelManager getFuelManager();

    ExplosionManager getExplosionManager();

    void writeInLeaderboard(String name, long score) throws IOException;

    void initializeBosses() throws FileNotFoundException;

    void initializeEnemies(int asteroidsHealth, int asteroidsSpeed, int asteroidsCount, int ufoHealth, int ufoSpeed, int ufoCount);
}