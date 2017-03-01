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
        this.scoreLine = new Text(20, 30, "");
        this.lives = new Text(20, 50, "");
        this.fuelBarText = new Text(20, 80, "Fuel: ");

        root.getChildren().add(scoreLine);
        root.getChildren().add(lives);
        root.getChildren().add(fuelBarText);

        this.scoreLine.setFont(Font.font("Verdana", 20));
        this.scoreLine.setFill(Color.WHITE);

        this.lives.setFont(Font.font("Verdana", 20));
        this.lives.setFill(Color.WHITE);

        this.fuelBarText.setFont(Font.font("Verdana", 20));
        this.fuelBarText.setFill(Color.WHITE);
    }

    public void updateText(Player player) {
        String score = String.format("Score: %d", player.getPoints());
        this.scoreLine.setText(score);

        String livesNumber = String.format("Lives: %d", player.getLives());
        this.lives.setText(livesNumber);

        //FuelBar Text
        String livesC = String.format("Lives: %d", player.getLives());
        this.lives.setText(livesC);
    }
}