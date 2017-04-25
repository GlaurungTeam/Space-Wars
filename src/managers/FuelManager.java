package managers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import models.gameObjects.FuelBar;
import models.gameObjects.FuelCan;
import contracts.Level;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class FuelManager {

    private static final int VBOX_SPACING = 20;
    private static final int LAYOUT_X = 80;
    private static final int LAYOUT_Y = 60;

    private FuelBar fuelBar;
    private FuelCan fuelCan;
    private Timeline timeline;

    public FuelManager(Group root) {
        this.setFuelBar(root);
        this.setFuelCan();
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

        VBox layout = new VBox(VBOX_SPACING);
        layout.setLayoutX(LAYOUT_X);
        layout.setLayoutY(LAYOUT_Y);
        layout.getChildren().addAll(bar);
        root.getChildren().add(layout);

        this.fuelBar = bar;
    }

    private void setFuelCan() {
        BufferedImage fuelCanSpriteSheet = null;

        try {
            String path = Constants.PROJECT_PATH + Constants.FUELCAN_IMAGE;
            fuelCanSpriteSheet = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.fuelCan = new FuelCan(
                Constants.FUELCAN_SPAWN_X, Constants.FUELCAN_SPAWN_Y, Constants.FUELCAN_SPEED, fuelCanSpriteSheet);
    }

    private void setCountdown(int totalFuel, ReadOnlyDoubleWrapper workDone) {
        this.timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(workDone, totalFuel)),
                new KeyFrame(Duration.seconds(30), new KeyValue(workDone, 0))
        );

        this.timeline.play();
    }

    public void resetFuel() {
        this.timeline.playFromStart();
    }

    public void pauseFuel() {
        this.timeline.pause();
    }

    public void resumeFuel() {
        this.timeline.play();
    }

    public void updateFuel(Level level) {
        if (!this.fuelCan.getTakenStatus()) {
            this.fuelCan.render(level.getGc());
        }

        if (!this.fuelCan.getTakenStatus() &&
                level.getPlayerManager().checkCollision(fuelCan)) {

            this.timeline.playFromStart();
            this.fuelCan.setTakenStatus(true);
        }

        if (level.getPlayer().isLightningSpeedOn()) {
            this.timeline.setRate(Constants.FUEL_RATE_FAST);
        } else {
            this.timeline.setRate(Constants.FUEL_RATE_SLOW);
        }

        this.updateFuelCanLocation(level.getCanvas());
        this.checkFuel(level.getPlayerManager());
    }

    private void checkFuel(PlayerManager playerManager) {
        if (this.fuelBar.getWorkDone().getValue() == 0.0) {
            playerManager.getPlayer().decrementLives();
            playerManager.resetPlayerPosition();

            this.resetFuel();
            this.timeline.playFromStart();
        }
    }

    private void updateFuelCanLocation(Canvas canvas) {
        double heightOffset = this.fuelCan.getHeight();
        double offset = canvas.getHeight() - heightOffset;

        Random rnd = new Random();

        this.fuelCan.updateLocation(this.fuelCan.getPositionX() - this.fuelCan.getSpeed(), this.fuelCan.getPositionY());

        if (this.fuelCan.getPositionX() < Constants.FUELCAN_RESTART_LEFT_COORDINATE) {
            int randomX = rnd.nextInt((int) canvas.getWidth());
            int randomY = rnd.nextInt((int) offset);
            this.fuelCan.updateLocation(randomX, randomY);
            this.fuelCan.setTakenStatus(false);
        }
    }
}