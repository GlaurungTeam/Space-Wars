package controllers;

import utils.Constants;
import models.level.BaseLevel;
import models.level.LevelEasy;
import models.level.LevelHard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import managers.GameManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MenuController {

    public TextField usernameField;
    public static String USERNAME;
    public Label spaceWars;
    public Button easyLevelButton;
    public Button hardLevelButton;

    public void start(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Stage loginStage = (Stage) this.spaceWars.getScene().getWindow();

        loginStage.setTitle("User Login");
        loginStage.setScene(new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loginStage.show();
        loginStage.setFullScreen(true);
    }

    public void openHighScores(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage primaryStage = (Stage) this.spaceWars.getScene().getWindow();

        Pane root = FXMLLoader.load(getClass().getResource("../views/leaderboard.fxml"));
        Scene scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        primaryStage.setTitle("Leaderboard");
        primaryStage.setScene(scene);
        primaryStage.show();

        try (ObjectInputStream in = new ObjectInputStream
                (new FileInputStream(Constants.PROJECT_PATH + Constants.LEADERBOARD_FILE_LOCATION))) {

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

        leaderBoardStage.setTitle("Launcher");
        leaderBoardStage.setScene(new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        leaderBoardStage.show();
    }

    public void startGame(ActionEvent actionEvent) throws Exception {
        Platform.runLater(() -> {
            USERNAME = usernameField.getText();

            if (USERNAME.trim().length() != 0 && USERNAME.trim().length() <= 10) {
                Parent root = null;

                try {
                    root = FXMLLoader.load(getClass().getResource("../views/difficulty.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage difficultyStage = (Stage) this.spaceWars.getScene().getWindow();

                difficultyStage.setTitle("Difficulty selector");
                difficultyStage.setScene(new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
                difficultyStage.show();
                difficultyStage.setFullScreen(true);

            }
        });
    }

    public void startEasyLevel(ActionEvent actionEvent) throws Exception {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            ((Stage) easyLevelButton.getScene().getWindow()).close();
            GameManager gameManager = new GameManager();

            BaseLevel level = new LevelEasy();
            stage.setFullScreen(true);

            try {
                gameManager.start(stage, level);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void startHardLevel(ActionEvent actionEvent) throws Exception {
        Platform.runLater(() -> {
            Stage stage = new Stage();

            ((Stage) easyLevelButton.getScene().getWindow()).close();
            GameManager gameManager = new GameManager();

            BaseLevel level = new LevelHard();

            try {
                gameManager.start(stage, level);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}