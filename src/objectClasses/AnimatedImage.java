package objectClasses;

import javafx.scene.image.Image;

//The whole class is not used at the moment as you can see
public class AnimatedImage {
    public Image[] frames;
    public double duration;

    public Image getFrame(double time) {
        int index = (int) ((time % (frames.length * duration)) / duration);
        return frames[index];
    }
}
