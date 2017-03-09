package controllers;

import entities.Constants;
import entities.level.Level;
import entities.level.LevelEasy;
import entities.level.LevelHard;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import managers.DimensionsManager;
import managers.GameManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MenuController {
    public TextField usernameField;
    public Button startGameButton;
    public AnchorPane finalScorePane;
    public AnchorPane launcherPane;
    public Label loginLabel;
    public AnchorPane loginPane;
    public static String userName;
    public Label spaceWars;
    public AnchorPane difficultyPane;
    public Button easyLevelButton;
    public Button hardLevelButton;

    private DimensionsManager dimensions = new DimensionsManager();

    public void start(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Stage loginStage = (Stage) this.spaceWars.getScene().getWindow();

        dimensions.calculateScreenDimensions();

        loginStage.setTitle("User Login");
        loginStage.setScene(new Scene(root, dimensions.getCurrentDeviceWidth(), dimensions.getCurrentDeviceHeight()));
        loginStage.show();
        loginStage.setFullScreen(true);
    }

    public void openHighScores(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage primaryStage = (Stage) this.spaceWars.getScene().getWindow();

        dimensions.calculateScreenDimensions();

        Pane root = FXMLLoader.load(getClass().getResource("../views/leaderboard.fxml"));
        Scene scene = new Scene(root, dimensions.getCurrentDeviceWidth(), dimensions.getCurrentDeviceHeight());

        primaryStage.setTitle("Leaderboard");
        primaryStage.setScene(scene);
        primaryStage.show();

        try (ObjectInputStream in = new ObjectInputStream
                (new FileInputStream(Constants.LEADERBOARD_FILE_LOCATION))) {
            String[] scores = (String[]) in.readObject();

            int i = 1;
            int y = 125;

            //Keep it simple stupid :)
            for (String score : scores) {
                if (score == null) {
                    continue;
                }

                String[] scoreLineArr = score.split(":");
                String userName = scoreLineArr[0];
                Long result = Long.parseLong(scoreLineArr[1]);

                Text scoreLine = new Text(500, y, "");
                root.getChildren().add(scoreLine);
                scoreLine.setFont(Font.font("Verdana", 18));
                scoreLine.setFill(Color.WHITE);

                String scoreToPrint = String.format("%d. %s - %d points", i, userName, result);
                scoreLine.setText(scoreToPrint);

                y += 36;
                i++;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }


    public void goBack(ActionEvent actionEvent) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("../views/mainmenu.fxml"));

        Stage leaderBoardStage = (Stage) this.spaceWars.getScene().getWindow();

        dimensions.calculateScreenDimensions();

        leaderBoardStage.setTitle("Launcher");
        leaderBoardStage.setScene(new Scene(root, dimensions.getCurrentDeviceWidth(), dimensions.getCurrentDeviceHeight()));
        leaderBoardStage.show();
    }

    public void startGame(ActionEvent actionEvent) throws Exception {
        userName = usernameField.getText();

        if (userName.trim().length() != 0 && userName.trim().length() <= 10) {
            Parent root = FXMLLoader.load(getClass().getResource("../views/difficulty.fxml"));
            Stage difficultyStage = (Stage) this.spaceWars.getScene().getWindow();

            dimensions.calculateScreenDimensions();

            difficultyStage.setTitle("Difficulty selector");
            difficultyStage.setScene(new Scene(root, dimensions.getCurrentDeviceWidth(), dimensions.getCurrentDeviceHeight()));
            difficultyStage.show();
            difficultyStage.setFullScreen(true);

        }
    }

    public void startEasyLevel(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        ((Stage) easyLevelButton.getScene().getWindow()).close();
        GameManager gameManager = new GameManager();

        Level level = new LevelEasy();
        stage.setFullScreen(true);
        gameManager.start(stage, level);


    }

    public void startHardLevel(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        ((Stage) easyLevelButton.getScene().getWindow()).close();
        GameManager gameManager = new GameManager();

        Level level = new LevelHard();

        gameManager.start(stage, level);
    }
}