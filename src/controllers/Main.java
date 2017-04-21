package controllers;

import utils.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

import managers.*;

public class Main extends Application {
    private static final String MAIN_MENU_VIEW_FILE_PATH = "../views/mainmenu.fxml";
    private static final String WINDOW_TITLE = "Launcher";

    @Override
    public void start(Stage theStage) throws Exception {
        Platform.runLater(() -> {
            AudioClip soundtrack = new AudioClip(Paths.get(Constants.SOUNDTRACK_PATH).toUri().toString());
            soundtrack.play(Constants.SOUND_VOLUME);

            Parent root = null;

            try {
                root = FXMLLoader.load(getClass().getResource(MAIN_MENU_VIEW_FILE_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }

            DimensionsManager dimensionsManager = new DimensionsManager();
            dimensionsManager.calculateScreenDimensions();
            Constants.setResolutionConstants((int) dimensionsManager.getCurrentDeviceWidth(),
                    (int) dimensionsManager.getCurrentDeviceHeight());

            theStage.setTitle(WINDOW_TITLE);
            theStage.setScene(new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
            theStage.show();
            theStage.setFullScreen(true);
            theStage.setFullScreenExitHint("");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}