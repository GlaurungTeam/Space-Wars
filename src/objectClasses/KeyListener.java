package objectClasses;

import javafx.scene.Scene;

public class KeyListener {
    public static boolean goLeft;
    public static boolean goRight;
    public static boolean goUp;
    public static boolean goDown;
    public static boolean held = false;

    public static void initialize(Scene theScene, Player player) {

        //Add event listener
        theScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    goUp = true;
                    break;
                case DOWN:
                    goDown = true;
                    break;
                case LEFT:
                    goLeft = true;
                    break;
                case RIGHT:
                    goRight = true;
                    break;
                case SPACE:
                    player.fire();
                    break;
                case SHIFT:
                    held = true;
            }
        });

        theScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                    goUp = false;
                    break;
                case DOWN:
                    goDown = false;
                    break;
                case LEFT:
                    goLeft = false;
                    break;
                case RIGHT:
                    goRight = false;
                    break;
                case SHIFT:
                    held = false;
            }
        });
    }
}