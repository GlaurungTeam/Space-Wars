package managers;

import entities.Player;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextManager {
    private Text scoreLine;
    private Text lives;
    private Text fuelBarText;

    public TextManager(Group root) {
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

    public void updateText(Player player) {
        String score = String.format("Score: %d", player.getPoints());
        this.getScoreLine().setText(score);

        String livesNumber = String.format("Lives: %d", player.getLives());
        this.getLives().setText(livesNumber);

        //FuelBar Text
        String livesC = String.format("Lives: %d", player.getLives());
        this.getLives().setText(livesC);
    }
}