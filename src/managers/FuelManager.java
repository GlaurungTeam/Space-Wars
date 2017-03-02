package managers;

import entities.FuelBar;
import entities.FuelCan;
import entities.Level;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class FuelManager {
    private FuelBar fuelBar;
    private FuelCan fuelCan;
    private Timeline timeline;

    public FuelManager(Group root, Canvas canvas) {
        this.setFuelBar(root);
        this.setFuelCan(canvas);
    }


    private FuelBar getFuelBar() {
        return fuelBar;
    }

    private void setFuelBar(Group root) {
        int totalFuel = 50;
        String FUEL_BURNED_FORMAT = "%.0f";
        ReadOnlyDoubleWrapper workDone = new ReadOnlyDoubleWrapper();

        FuelBar bar = new FuelBar(
                workDone.getReadOnlyProperty(),
                totalFuel,
                FUEL_BURNED_FORMAT
        );

        setCountdown(totalFuel, workDone);

        VBox layout = new VBox(20);
        layout.setLayoutX(80);
        layout.setLayoutY(60);
        layout.getChildren().addAll(bar);
        root.getChildren().add(layout);

        this.fuelBar = bar;
    }

    private void setCountdown(int totalFuel, ReadOnlyDoubleWrapper workDone) {
        Timeline countDown = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(workDone, totalFuel)),
                new KeyFrame(Duration.seconds(30), new KeyValue(workDone, 0))
        );

        countDown.play();

        this.timeline = countDown;
    }

    private Timeline getTimeline() {
        return timeline;
    }

    private FuelCan getFuelCan() {
        return fuelCan;
    }

    private void setFuelCan(Canvas canvas) {
        int fuelSpeed = 1;
        FuelCan fuelCan = new FuelCan(canvas, fuelSpeed);
        this.fuelCan = fuelCan;
    }

    public void updateFuel(GraphicsContext gc, PlayerManager playerManager, Canvas canvas, Level level, Scene scene, AnimationTimer animationTimer){

        if (!this.getFuelCan().getTakenStatus()) {
            this.getFuelCan().render(gc);
        }

        if (!this.getFuelCan().getTakenStatus() &&
                playerManager.checkCollision(this.getFuelCan().getPositionX(), this.getFuelCan().getPositionY(), 45)) {

            this.getTimeline().playFromStart();
            this.getFuelCan().setTakenStatus(true);
        }
        this.getFuelCan().updateFuelCanLocation(canvas);

        checkFuel(level, playerManager, canvas, scene, animationTimer);
    }

    public void checkFuel(Level level1, PlayerManager playerManager, Canvas canvas, Scene theScene, AnimationTimer animationTimer){
        if (this.getFuelBar().getWorkDone().getValue() == 0.0) {
            level1.getPlayer().setLives(level1.getPlayer().getLives() - 1);
            playerManager.resetPlayerPosition(canvas);
            this.getTimeline().playFromStart();

            try {
                level1.checkIfPlayerIsDead(theScene, animationTimer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
