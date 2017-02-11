package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Controller {
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
    public String userName;
    public Label spaceWars;

    public void start(ActionEvent actionEvent) throws Exception {
        Image space = new Image("resources/space.png");
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage loginStage = (Stage) spaceWars.getScene().getWindow();
        loginStage.setTitle("User Login");
        loginStage.setScene(new Scene(root, 1280, 720));
        loginStage.show();
    }

    public void openHighScores(ActionEvent actionEvent) {
        //highScores();
    }

    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void getUsername(ActionEvent actionEvent) {
    }

    public void startGame(ActionEvent actionEvent) throws Exception {
        userName = usernameField.getText();
        if (userName.trim().length() != 0) {
            Stage stage = new Stage();
            ((Stage) this.startGameButton.getScene().getWindow()).close();
            GameController gameController = new GameController();
            gameController.start(stage);
        }
    }
}