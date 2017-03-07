package entities;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import managers.GameManager;

import java.util.ArrayList;

public class Level2 extends Level{
    public Level2(Group group, Player player, GraphicsContext gc, Canvas canvas, GameManager gameManager, Scene scene, Double currentFrame, ArrayList<Asteroid> asteroids, ArrayList<GameObject> enemies) {
        super(group, player, gc, canvas, gameManager, scene, currentFrame, asteroids, enemies);
    }
}
