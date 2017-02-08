package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.Platform;
import objectClasses.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

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