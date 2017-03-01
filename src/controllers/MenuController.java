package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MenuController {
    //Project Path
    public static String PROJECT_PATH = System.getProperty("user.dir");
    public Button startButton;
    public Button scoreButton;
    public Button quitButton;
    public TextField usernameField;
    public Button startGameButton;
    public AnchorPane finalScorePane;
    public AnchorPane launcherPane;
    public Label loginLabel;
    public AnchorPane loginPane;
    public static String userName;
    public Label spaceWars;

    public void start(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        Stage loginStage = (Stage) this.spaceWars.getScene().getWindow();

        loginStage.setTitle("User Login");
        loginStage.setScene(new Scene(root, 1280, 720));
        loginStage.show();
    }

    public void openHighScores(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage primaryStage = (Stage) spaceWars.getScene().getWindow();

        Pane root = FXMLLoader.load(getClass().getResource("../views/leaderboard.fxml"));
        Scene scene = new Scene(root, 1280, 720);

        primaryStage.setTitle("Leaderboard");
        primaryStage.setScene(scene);
        primaryStage.show();

        Path path = Paths.get("src\\views\\leaderBoard.txt");
        Path realPath = path.toRealPath(LinkOption.NOFOLLOW_LINKS);

        try (BufferedReader in = new BufferedReader(new FileReader(realPath.toString()))) {
            String scoreTokens = in.readLine();

            int i = 1;
            int y = 125;

            //Keep it simple stupid :)
            while (scoreTokens != null) {
                String[] scoreLineArr = scoreTokens.split(":");
                String userName = scoreLineArr[0];
                Long result = Long.parseLong(scoreLineArr[1]);

                Text scoreLine = new Text(500, y, "");
                root.getChildren().add(scoreLine);
                scoreLine.setFont(Font.font("Verdana", 18));
                scoreLine.setFill(Color.WHITE);
                String score = toString().format("%d. %s - %d points", i, userName, result);
                scoreLine.setText(score);

                y += 36;
                i++;

                scoreTokens = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }


    public void getUsername(ActionEvent actionEvent) {
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("../views/sample.fxml"));

        Stage leaderBoardStage = (Stage) spaceWars.getScene().getWindow();

        leaderBoardStage.setTitle("Launcher");
        leaderBoardStage.setScene(new Scene(root, 1280, 720));
        leaderBoardStage.show();
    }

    public void startGame(ActionEvent actionEvent) throws Exception {
        userName = usernameField.getText();

        if (userName.trim().length() != 0 && userName.trim().length() <= 10) {
            Stage stage = new Stage();
            ((Stage) this.startGameButton.getScene().getWindow()).close();
            GameController gameController = new GameController();
            gameController.start(stage);
        }
    }
}