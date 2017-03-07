package entities;

import managers.GameManager;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;

public class Level1 extends Level{

    public Level1(Group group, Player player, GraphicsContext gc, Canvas canvas, GameManager gameManager, Scene scene, Double currentFrame, ArrayList<Asteroid> asteroids, ArrayList<GameObject> enemies) {
        super(group, player, gc, canvas, gameManager, scene, currentFrame, asteroids, enemies);
    }
}