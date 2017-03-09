package managers;

import java.awt.*;

public class DimensionsManager {
    private double currentDeviceWidth;
    private double currentDeviceHeight;

    public double getCurrentDeviceWidth() {
        return this.currentDeviceWidth;
    }

    private void setCurrentDeviceWidth(double currentDeviceWidth) {
        this.currentDeviceWidth = currentDeviceWidth;
    }

    public double getCurrentDeviceHeight() {
        return this.currentDeviceHeight;
    }

    private void setCurrentDeviceHeight(double currentDeviceHeight) {
        this.currentDeviceHeight = currentDeviceHeight;
    }

    public void calculateScreenDimensions() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        this.setCurrentDeviceWidth(width);
        this.setCurrentDeviceHeight(height);
    }
}