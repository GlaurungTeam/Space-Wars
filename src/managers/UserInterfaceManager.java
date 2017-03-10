package managers;

import entities.Player;
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
        this.setScoreLine(new Text(20, 30, ""));
        this.setLives(new Text(20, 50, ""));
        this.setFuelBarText(new Text(20, 80, "Fuel: "));

        root.getChildren().addAll(this.getScoreLine(), this.getLives(), this.getFuelBarText());

        this.getScoreLine().setFont(Font.font("Verdana", 20));
        this.getScoreLine().setFill(Color.WHITE);

        this.getLives().setFont(Font.font("Verdana", 20));
        this.getLives().setFill(Color.WHITE);

        this.getFuelBarText().setFont(Font.font("Verdana", 20));
        this.getFuelBarText().setFill(Color.WHITE);

        this.setPauseBox(this.createBox());
        this.setPauseButton(this.createPauseButton());
        this.setResumeButton(this.createResumeButton());
        this.setQuitButton(this.createQuitButton());

        root.getChildren().addAll(this.getPauseBox(), this.getPauseButton(), this.getResumeButton(), this.getQuitButton());
    }

    private Text getScoreLine() {
        return this.scoreLine;
    }

    private void setScoreLine(Text scoreLine) {
        this.scoreLine = scoreLine;
    }

    private Text getLives() {
        return this.lives;
    }

    private void setLives(Text lives) {
        this.lives = lives;
    }

    private Text getFuelBarText() {
        return this.fuelBarText;
    }

    private void setFuelBarText(Text fuelBarText) {
        this.fuelBarText = fuelBarText;
    }

    public VBox getPauseBox() {
        return this.pauseBox;
    }

    private void setPauseBox(VBox pauseBox) {
        this.pauseBox = pauseBox;
    }

    public Button getPauseButton() {
        return this.pauseButton;
    }

    private void setPauseButton(Button pauseButton) {
        this.pauseButton = pauseButton;
    }

    public Button getResumeButton() {
        return this.resumeButton;
    }

    private void setResumeButton(Button resumeButton) {
        this.resumeButton = resumeButton;
    }

    public Button getQuitButton() {
        return this.quitButton;
    }

    private void setQuitButton(Button quitButton) {
        this.quitButton = quitButton;
    }

    public void updateText(Player player) {
        String score = String.format("Score: %d", player.getPoints());
        this.getScoreLine().setText(score);

        String livesNumber = String.format("Lives: %d", player.getLives());
        this.getLives().setText(livesNumber);

        //FuelBar Text
        String livesC = String.format("Lives: %d", player.getLives());
        this.getLives().setText(livesC);
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
        pauseButton.setStyle("-fx-alignment: baseline-center; -fx-animated: true; -fx-font-size: 20; -fx-border-color: black; -fx-border-width: 3; -fx-font-weight: bold");

        return pauseButton;
    }

    private Button createResumeButton() {
        Button resumeButton = new Button("RESUME");
        resumeButton.setLayoutX(640);
        resumeButton.setLayoutY(320);
        resumeButton.setStyle("-fx-alignment: baseline-center; -fx-animated: true; -fx-font-size: 20; -fx-border-color: black; -fx-font-weight: bold");
        resumeButton.setVisible(false);

        return resumeButton;
    }

    private Button createQuitButton() {
        Button quitButton = new Button("QUIT");
        quitButton.setLayoutX(655);
        quitButton.setLayoutY(370);
        quitButton.setStyle("-fx-alignment: baseline-center; -fx-animated: true; -fx-font-size: 20; -fx-border-color: black; -fx-font-weight: bold");
        quitButton.setVisible(false);

        return quitButton;
    }
}