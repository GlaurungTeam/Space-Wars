package managers;

import entities.Constants;
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

    private void setFuelBar(FuelBar fuelBar) {
        this.fuelBar = fuelBar;
    }

    private Timeline getTimeline() {
        return this.timeline;
    }

    private void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    private FuelCan getFuelCan() {
        return this.fuelCan;
    }

    private void setFuelCan(Canvas canvas) {
        this.fuelCan = new FuelCan(canvas, Constants.FUELCAN_SPEED);
    }

    public void resetFuel() {
        this.getTimeline().playFromStart();
    }

    private void setFuelBar(Group root) {
        String FUEL_BURNED_FORMAT = "%.0f";
        ReadOnlyDoubleWrapper workDone = new ReadOnlyDoubleWrapper();

        FuelBar bar = new FuelBar(
                workDone.getReadOnlyProperty(),
                Constants.TOTAL_FUEL,
                FUEL_BURNED_FORMAT
        );

        this.setCountdown(Constants.TOTAL_FUEL, workDone);

        VBox layout = new VBox(20);
        layout.setLayoutX(80);
        layout.setLayoutY(60);
        layout.getChildren().addAll(bar);
        root.getChildren().add(layout);

        this.fuelBar = bar;
    }

    private void setCountdown(int totalFuel, ReadOnlyDoubleWrapper workDone) {
        this.setTimeline(new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(workDone, totalFuel)),
                new KeyFrame(Duration.seconds(30), new KeyValue(workDone, 0))
        ));

        this.getTimeline().play();
    }

    public void updateFuel(PlayerManager playerManager, Level level, AnimationTimer animationTimer) {
        if (!this.getFuelCan().getTakenStatus()) {
            this.getFuelCan().render(level.getGc());
        }

        if (!this.getFuelCan().getTakenStatus() &&
                playerManager.checkCollision(this.getFuelCan().getPositionX(), this.getFuelCan().getPositionY(), 45)) {

            this.getTimeline().playFromStart();
            this.getFuelCan().setTakenStatus(true);
        }

        if (level.getPlayer().isHeld()) {
            this.getTimeline().setRate(Constants.FUEL_RATE_FAST);
        } else {
            this.getTimeline().setRate(Constants.FUEL_RATE_SLOW);
        }

        this.getFuelCan().updateFuelCanLocation(level.getCanvas());
        this.checkFuel(level, playerManager, animationTimer);
    }

    private void checkFuel(Level level1, PlayerManager playerManager, AnimationTimer animationTimer) {
        if (this.getFuelBar().getWorkDone().getValue() == 0.0) {
            level1.getPlayer().setLives(level1.getPlayer().getLives() - 1);
            playerManager.resetPlayerPosition(level1.getCanvas(), this);

            this.getTimeline().playFromStart();

            try {
                playerManager.checkIfPlayerIsDead(level1, animationTimer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}