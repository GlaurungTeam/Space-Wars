package controllers;

import entities.Constants;
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

    @Override
    public void start(Stage theStage) throws Exception {
        Platform.runLater(() -> {
            AudioClip soundtrack = new AudioClip(Paths.get(Constants.SOUNDTRACK_PATH).toUri().toString());
            soundtrack.play(Constants.SOUND_VOLUME);

            Parent root = null;

            try {
                root = FXMLLoader.load(getClass().getResource("../views/mainmenu.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            DimensionsManager dimensions = new DimensionsManager();
            dimensions.calculateScreenDimensions();

            theStage.setTitle("Launcher");
            theStage.setScene(new Scene(root, dimensions.getCurrentDeviceWidth(), dimensions.getCurrentDeviceHeight()));
            theStage.show();
            theStage.setFullScreen(true);
            theStage.setFullScreenExitHint("");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}