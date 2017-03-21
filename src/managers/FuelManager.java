package managers;

import entities.Constants;
import entities.FuelBar;
import entities.FuelCan;
import entities.level.Level;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FuelManager {
    private FuelBar fuelBar;
    private FuelCan fuelCan;
    private Timeline timeline;

    public FuelManager(Group root) {
        this.setFuelBar(root);
        this.setFuelCan();
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

    private void setFuelCan() {
        BufferedImage fuelCanSpriteSheet = null;

        try {
            String path = Constants.PROJECT_PATH + Constants.FUELCAN_IMAGE;
            fuelCanSpriteSheet = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fuelCan = new FuelCan(150,150,Constants.FUELCAN_SPEED,fuelCanSpriteSheet,
                30,45,1,1);
    }

    public void resetFuel() {
        this.getTimeline().playFromStart();
    }

    public void pauseFuel() { this.getTimeline().pause();}

    public void resumeFuel() {this.getTimeline().play();}

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

    public void updateFuel(PlayerManager playerManager, Level level) {
        if (!this.getFuelCan().getTakenStatus()) {
            this.getFuelCan().setImage(this.getFuelCan().getCurrentFrame(0));
            this.getFuelCan().render(level.getGc());
        }

        if (!this.getFuelCan().getTakenStatus() &&
                playerManager.checkCollision(fuelCan)) {

            this.getTimeline().playFromStart();
            this.getFuelCan().setTakenStatus(true);
        }

        if (level.getPlayer().isHeld()) {
            this.getTimeline().setRate(Constants.FUEL_RATE_FAST);
        } else {
            this.getTimeline().setRate(Constants.FUEL_RATE_SLOW);
        }

        this.getFuelCan().updateFuelCanLocation(level.getCanvas());
        this.checkFuel(level, playerManager);
    }

    private void checkFuel(Level level, PlayerManager playerManager) {
        if (this.getFuelBar().getWorkDone().getValue() == 0.0) {
            level.getPlayer().setLives(level.getPlayer().getLives() - 1);
            playerManager.resetPlayerPosition(level.getCanvas(), this);

            this.getTimeline().playFromStart();
        }
    }
}