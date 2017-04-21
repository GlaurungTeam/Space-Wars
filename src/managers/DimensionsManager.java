package managers;

import java.awt.*;

public class DimensionsManager {
    private double currentDeviceWidth;
    private double currentDeviceHeight;

    public double getCurrentDeviceWidth() {
        return this.currentDeviceWidth;
    }

    public double getCurrentDeviceHeight() {
        return this.currentDeviceHeight;
    }

    public void calculateScreenDimensions() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        this.currentDeviceWidth = width;
        this.currentDeviceHeight = height;
    }
}