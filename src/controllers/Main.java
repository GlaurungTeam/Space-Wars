package controllers;

import entities.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class Main extends Application {
    @Override
    public void start(Stage theStage) throws Exception {
        AudioClip soundtrack = new AudioClip(Paths.get(Constants.SOUNDTRACK_PATH).toUri().toString());
        soundtrack.play(Constants.SOUND_VOLUME);

        Parent root = FXMLLoader.load(getClass().getResource("../views/mainmenu.fxml"));

        theStage.setTitle("Launcher");
        theStage.setScene(new Scene(root, 1280, 720));
        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}