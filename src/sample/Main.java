package sample;

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
        //Soundtrack
        AudioClip soundtrack = new AudioClip(Paths.get("src/resources/sound/soundtrack.mp3").toUri().toString());
        soundtrack.play(0.1);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        theStage.setTitle("Launcher");
        theStage.setScene(new Scene(root, 1280, 720));
        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}