package managers;

import utils.Constants;
import models.gameObjects.PlayerImpl;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UserInterfaceManager {
    private Text scoreLine;
    private Text lives;
    private Text fuelBarText;

    private VBox pauseBox;
    private Button pauseButton;
    private Button resumeButton;
    private Button quitButton;

    public UserInterfaceManager(Group root) {
        this.scoreLine = new Text(20, 30, "");
        this.lives = new Text(20, 50, "");
        this.fuelBarText = new Text(20, 80, "Fuel: ");

        root.getChildren().addAll(this.scoreLine, this.lives, this.fuelBarText);

        this.scoreLine.setFont(Font.font("Verdana", 20));
        this.scoreLine.setFill(Color.WHITE);

        this.lives.setFont(Font.font("Verdana", 20));
        this.lives.setFill(Color.WHITE);

        this.fuelBarText.setFont(Font.font("Verdana", 20));
        this.fuelBarText.setFill(Color.WHITE);

        this.pauseBox = this.createBox();
        this.pauseButton = this.createPauseButton();
        this.resumeButton = this.createResumeButton();
        this.quitButton = this.createQuitButton();

        root.getChildren().addAll(this.getPauseBox(), this.getPauseButton(),
                this.getResumeButton(), this.getQuitButton());
    }

    public VBox getPauseBox() {
        return this.pauseBox;
    }

    public Button getPauseButton() {
        return this.pauseButton;
    }

    public Button getResumeButton() {
        return this.resumeButton;
    }

    public Button getQuitButton() {
        return this.quitButton;
    }

    public void updateText(PlayerImpl player) {
        String score = String.format("Score: %d", player.getPoints());
        this.scoreLine.setText(score);

        String livesNumber = String.format("Lives: %d", player.getCurrentLives());
        this.lives.setText(livesNumber);

        //FuelBar Text
        String livesC = String.format("Lives: %d", player.getCurrentLives());
        this.lives.setText(livesC);
    }

    private VBox createBox() {
        VBox pauseBox = new VBox();
        pauseBox.setPrefSize(100, 100);
        pauseBox.setSpacing(5);
        pauseBox.setAlignment(Pos.BASELINE_CENTER);
        pauseBox.setFillWidth(true);
        pauseBox.setVisible(false);
        pauseBox.setLayoutX(620);
        pauseBox.setLayoutY(300);

        return pauseBox;
    }

    private Button createPauseButton() {
        Button pauseButton = new Button("PAUSE");
        pauseButton.setLayoutX(20);
        pauseButton.setLayoutY(110);
        pauseButton.setStyle("-fx-alignment: baseline-center; -fx-animated: true; " +
                "-fx-font-size: 20; -fx-border-color: black; -fx-border-width: 3; -fx-font-weight: bold");

        return pauseButton;
    }

    private Button createResumeButton() {
        Button resumeButton = new Button("RESUME");
        resumeButton.setLayoutX(Constants.SCREEN_WIDTH / 2);
        resumeButton.setLayoutY(320);
        resumeButton.setStyle("-fx-alignment: baseline-center; -fx-animated: true; " +
                "-fx-font-size: 20; -fx-border-color: black; -fx-font-weight: bold");
        resumeButton.setVisible(false);

        return resumeButton;
    }

    private Button createQuitButton() {
        Button quitButton = new Button("QUIT");
        quitButton.setLayoutX(Constants.SCREEN_WIDTH / 2 + 15);
        quitButton.setLayoutY(370);
        quitButton.setStyle("-fx-alignment: baseline-center; -fx-animated: true;" +
                " -fx-font-size: 20; -fx-border-color: black; -fx-font-weight: bold");
        quitButton.setVisible(false);

        return quitButton;
    }
}