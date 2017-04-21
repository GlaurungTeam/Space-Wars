package managers;

import utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BackgroundManager{
    private static final int PLANET_X_POSITION_INCREMENTATION = 50;

    @FXML
    private double backgroundX;
    private double backgroundY;

    private double planetX;
    private double planetY;

    private double earthX;
    private double earthY;

    private Image earth;
    private Image sun;
    private Image space;

    public BackgroundManager() {
        this.planetX = Constants.PLANET_START_X;
        this.planetY = Constants.PLANET_START_Y;

        this.backgroundY = 0;

        this.earth = new Image(Constants.EARTH_IMAGE);
        this.sun = new Image(Constants.SUN_IMAGE);
    }

    public void setBackgroundImage(Image space) {
        this.space = space;
    }

    public void updateBackground(double t, Canvas canvas, GraphicsContext gc) {
        this.renderBackground(gc);

        this.earthX = this.planetX + 36 + 128 * Math.cos(t);
        this.earthY = 232 + 128 * Math.sin(t);

        this.backgroundX = this.backgroundX - Constants.BACKGROUND_SPEED;
        this.planetX = this.planetX - Constants.BACKGROUND_SPEED;

        if (this.backgroundX < -Constants.SCREEN_WIDTH) {
            this.backgroundX = 0;
        }

        if (this.planetX < -Constants.SCREEN_WIDTH) {
            this.planetX = canvas.getWidth() + PLANET_X_POSITION_INCREMENTATION; //TODO Hardcoded value
        }
    }

    private void renderBackground(GraphicsContext gc) {
        gc.drawImage(this.space, this.backgroundX, this.backgroundY);
        gc.drawImage(this.earth, this.earthX, this.earthY);
        gc.drawImage(this.sun, this.planetX, this.planetY);
    }
}