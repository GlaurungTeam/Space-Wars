package objectClasses;

import javafx.scene.image.Image;

//The whole class is not used at the moment as you can see
public class AnimatedImage {
    private Image[] frames;
    private double duration;

    public Image[] getFrames() {
        return this.frames;
    }

    public void setFrames(Image[] frames) {
        this.frames = frames;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Image getFrame(double time) {
        int index = (int) ((time % (this.getFrames().length * this.getDuration())) / this.getDuration());
        return this.getFrames()[index];
    }
}