package managers;

import entities.Constants;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BackgroundManager {
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
        this.setPlanetX(500);
        this.setPlanetY(196);

        this.setEarth(new Image(Constants.EARTH_IMAGE));
        this.setSun(new Image(Constants.SUN_IMAGE));
    }

    private double getBackgroundX() {
        return this.backgroundX;
    }

    private void setBackgroundX(double backgroundX) {
        this.backgroundX = backgroundX;
    }

    private double getBackgroundY() {
        return this.backgroundY;
    }

    private double getPlanetX() {
        return this.planetX;
    }

    private void setPlanetX(double planetX) {
        this.planetX = planetX;
    }

    private double getPlanetY() {
        return this.planetY;
    }

    private void setPlanetY(double planetY) {
        this.planetY = planetY;
    }

    private double getEarthX() {
        return this.earthX;
    }

    private void setEarthX(double earthX) {
        this.earthX = earthX;
    }

    private double getEarthY() {
        return this.earthY;
    }

    private void setEarthY(double earthY) {
        this.earthY = earthY;
    }

    private Image getEarth() {
        return this.earth;
    }

    private void setEarth(Image earth) {
        this.earth = earth;
    }

    private Image getSun() {
        return this.sun;
    }

    private void setSun(Image sun) {
        this.sun = sun;
    }

    private Image getSpace() {
        return this.space;
    }

    public void setBackgroundImage(Image space) {
        this.space = space;
    }

    public void updateBackground(double t, Canvas canvas) {
        //The 2 rows below are used to help make the earth move around the sun
        //No need to understand it
        this.setEarthX(this.getPlanetX() + 36 + 128 * Math.cos(t));
        this.setEarthY(232 + 128 * Math.sin(t));

        //Update background, planet and earth location
        this.setBackgroundX(this.getBackgroundX() - Constants.BACKGROUND_SPEED);
        this.setPlanetX(this.getPlanetX() - Constants.BACKGROUND_SPEED);

        if (this.getBackgroundX() < -1280) {
            this.setBackgroundX(0);
        }
        if (this.getPlanetX() < -1280) {
            this.setPlanetX(canvas.getWidth() + 50);
        }
    }

    public void renderBackground(GraphicsContext gc) {
        gc.drawImage(this.getSpace(), this.getBackgroundX(), this.getBackgroundY());
        gc.drawImage(this.getEarth(), this.getEarthX(), this.getEarthY());
        gc.drawImage(this.getSun(), this.getPlanetX(), this.getPlanetY());
    }
}