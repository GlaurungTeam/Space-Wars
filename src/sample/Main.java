package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objectClasses.AnimatedImage;
import objectClasses.SpaceShip;

import java.util.Random;
import java.util.Timer;


public class Main extends Application {

    private Timer timer = new Timer();

    @Override
    public void start(Stage theStage) throws Exception {
        theStage.setTitle("Timeline Example");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image earth = new Image("resources/earth.png");
        Image sun = new Image("resources/sun.png");
        Image space = new Image("resources/space.png");

        //UFO object
        AnimatedImage ufo = new AnimatedImage();
        Image[] imageArray = new Image[6];
        for (int i = 0; i < 6; i++) {
            imageArray[i] = new Image("resources/ufo_" + i + ".png");
        }
        ufo.frames = imageArray;
        ufo.duration = 0.100;

        //SpaceShip Object
        AnimatedImage ship = new AnimatedImage();
        Image[] spaceShipImageArr = new Image[1];
        spaceShipImageArr[0] = new Image("resources/spaceShip/shipsprite1_07.png");
        ship.frames = spaceShipImageArr;
        ship.duration = 0.100;
        SpaceShip[] shipsArr = new SpaceShip[2];
        shipsArr[0] = new SpaceShip(spaceShipImageArr,0.100,gc,0,-100,150, 30);
        shipsArr[1] = new SpaceShip(spaceShipImageArr,0.100,gc,0,-100,325, 68);


        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);
                //background image clears canvas
                gc.drawImage(space, 0, 0);
                gc.drawImage(earth, x, y);
                gc.drawImage(sun, 196, 196);
                // draw UFO
                gc.drawImage(ufo.getFrame(t), 100, 25);

                //draw spaceShips
                for (int i = 0; i < 2; i++) {
                    double nextX = shipsArr[i].x + (shipsArr[i].speed * t) % (canvas.getWidth() + 100);
                    shipsArr[i].drawShip(gc,ship,0, nextX, shipsArr[i].y);
                }
            }
        }.start();

        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
