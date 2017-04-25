package models.gameObjects;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import utils.Constants;

public class FuelBar extends StackPane {

    private static final int MULTIPLIER = 2;

    private ReadOnlyDoubleProperty workDone;
    private double totalWork;

    private ProgressBar bar;
    private Text text;
    private String labelFormatSpecifier;

    public FuelBar(ReadOnlyDoubleProperty workDone, final double totalWork, final String labelFormatSpecifier) {
        this.workDone = workDone;
        this.totalWork = totalWork;
        this.labelFormatSpecifier = labelFormatSpecifier;

        this.text = new Text();
        this.bar = new ProgressBar();

        this.syncProgress();
        this.workDone.addListener((observableValue, number, number2) -> syncProgress());

        this.bar.setMaxWidth(Double.MAX_VALUE);
        getChildren().setAll(this.bar, this.text);
    }

    public ReadOnlyDoubleProperty getWorkDone() {
        return this.workDone;
    }

    private void syncProgress() {
        if (this.getWorkDone() == null || this.totalWork == 0) {
            this.text.setText("");
            this.bar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        } else {
            this.text.setText(String.format(this.labelFormatSpecifier, Math.ceil(this.workDone.get())));
            this.bar.setProgress(this.getWorkDone().get() / this.totalWork);
        }

        this.bar.setMinHeight(this.text.getBoundsInLocal().getHeight() + Constants.DEFAULT_LABEL_PADDING * MULTIPLIER);
        this.bar.setMinWidth(this.text.getBoundsInLocal().getWidth() + Constants.DEFAULT_LABEL_PADDING * MULTIPLIER);
    }
}