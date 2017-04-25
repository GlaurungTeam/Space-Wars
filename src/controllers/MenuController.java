package controllers;

import javafx.fxml.FXML;
import utils.Constants;
import models.level.BaseLevel;
import models.level.LevelEasy;
import models.level.LevelHard;
import javafx.application.Platform;
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

    private static final String LOGIN_VIEW_PATH = "../views/login.fxml";
    private static final String LEADER_BOARD_VIEW_PATH = "../views/leaderboard.fxml";
    private static final String DIFICULT_SCREEN_PATH = "../views/difficulty.fxml";
    private static final String PANE_PATH = "../views/mainmenu.fxml";

    private static final String LOGIN_WINDOW_TITLE = "User Login";
    private static final String DIFFICULTY_SELECTOR_SCREEN_TITLE = "Difficulty selector";
    private static final String LEADERBOARD_WINDOW_TITLE = "Leaderboard";
    private static final String LAUNCHER_WINDOW_TITLE = "Launcher";

    private static final String TEXT_FONT = "Verdana";
    private static final int SCORELINE_FONT_SIZE = 18;

    private static final int USERNAME_INDEX = 0;
    private static final int STARTING_ROW_INDEX = 1;
    private static final int RESULT_INDEX = 1;

    private static final int FIRST_TEXTFIELD_Y_COORDINATE = 125;
    private static final int TEXTFIELD_Y_COORDINATE_INCREMENT = 36;
    private static final int TEXT_POSITIONING_X_COORDINATE = 500;

    private static final int USERNAME_MIN_LENGTH = 0;
    private static final int USERNAME_MAX_LENGTH = 10;

    @FXML
    private TextField usernameField;
    @FXML
    private Label spaceWars;
    @FXML
    private Button easyLevelButton;
    @FXML
    private Button hardLevelButton;

    private static String username;

    public void start() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(LOGIN_VIEW_PATH));
        Stage loginStage = (Stage) this.spaceWars.getScene().getWindow();

        loginStage.setTitle(LOGIN_WINDOW_TITLE);
        loginStage.setScene(new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        loginStage.show();
        loginStage.setFullScreen(true);
    }

    public void openHighScores() throws IOException, InterruptedException {
        Stage primaryStage = (Stage) this.spaceWars.getScene().getWindow();

        Pane root = FXMLLoader.load(getClass().getResource(LEADER_BOARD_VIEW_PATH));
        Scene scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        primaryStage.setTitle(LEADERBOARD_WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

        try (ObjectInputStream in = new ObjectInputStream
                (new FileInputStream(Constants.PROJECT_PATH + Constants.LEADERBOARD_FILE_LOCATION))) {

            String[] scores = (String[]) in.readObject();

            int rowIndex = STARTING_ROW_INDEX;
            int currentTextFieldYCoordinate = FIRST_TEXTFIELD_Y_COORDINATE;

            for (String score : scores) {
                if (score == null) {
                    continue;
                }

                String[] scoreLineArr = score.split(":");
                String userName = scoreLineArr[USERNAME_INDEX];
                Long result = Long.parseLong(scoreLineArr[RESULT_INDEX]);

                Text scoreLine = new Text(TEXT_POSITIONING_X_COORDINATE, currentTextFieldYCoordinate, "");
                root.getChildren().add(scoreLine);
                scoreLine.setFont(Font.font(TEXT_FONT, SCORELINE_FONT_SIZE));
                scoreLine.setFill(Color.WHITE);

                String scoreToPrint = String.format("%d. %s - %d points", rowIndex, userName, result);
                scoreLine.setText(scoreToPrint);

                currentTextFieldYCoordinate += TEXTFIELD_Y_COORDINATE_INCREMENT;
                rowIndex++;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        System.exit(0);
    }

    public void goBack() throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource(PANE_PATH));

        Stage leaderBoardStage = (Stage) this.spaceWars.getScene().getWindow();

        leaderBoardStage.setTitle(LAUNCHER_WINDOW_TITLE);
        leaderBoardStage.setScene(new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        leaderBoardStage.show();
    }

    public void startGame() throws Exception {
        Platform.runLater(() -> {
            username = this.usernameField.getText();

            if (username.trim().length() != USERNAME_MIN_LENGTH &&
                    username.trim().length() <= USERNAME_MAX_LENGTH) {
                Parent root = null;

                try {
                    root = FXMLLoader.load(getClass().getResource(DIFICULT_SCREEN_PATH));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage difficultyStage = (Stage) this.spaceWars.getScene().getWindow();

                difficultyStage.setTitle(DIFFICULTY_SELECTOR_SCREEN_TITLE);
                difficultyStage.setScene(new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
                difficultyStage.show();
                difficultyStage.setFullScreen(true);
            }
        });
    }

    public void startEasyLevel() throws Exception {
        Platform.runLater(() -> {

            Stage stage = new Stage();
            ((Stage) easyLevelButton.getScene().getWindow()).close();
            GameManager gameManager = new GameManager();

            BaseLevel level = new LevelEasy();
            stage.setFullScreen(true);

            try {
                gameManager.start(stage, level, username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void startHardLevel() throws Exception {
        Platform.runLater(() -> {
            Stage stage = new Stage();

            ((Stage) easyLevelButton.getScene().getWindow()).close();
            GameManager gameManager = new GameManager();

            BaseLevel level = new LevelHard();

            try {
                gameManager.start(stage, level, username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}