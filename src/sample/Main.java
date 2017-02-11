package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage theStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        theStage.setTitle("Launcher");
        theStage.setScene(new Scene(root, 1280, 720));
        theStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}