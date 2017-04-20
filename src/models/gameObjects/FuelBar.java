package models.gameObjects;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import utils.Constants;

public class FuelBar extends StackPane implements IFuelBar{

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

        //Allows the progress bar to expand to fill available horizontal space
        this.bar.setMaxWidth(Double.MAX_VALUE);
        getChildren().setAll(this.bar, this.text);
    }

    @Override
    public ReadOnlyDoubleProperty getWorkDone() {
        return this.workDone;
    }

    //Synchronizes the progress indicated with the work done
    private void syncProgress() {
        if (this.getWorkDone() == null || this.totalWork == 0) {
            this.text.setText("");
            this.bar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        } else {
            this.text.setText(String.format(this.labelFormatSpecifier, Math.ceil(this.workDone.get())));
            this.bar.setProgress(this.getWorkDone().get() / this.totalWork);
        }

        this.bar.setMinHeight(this.text.getBoundsInLocal().getHeight() + Constants.DEFAULT_LABEL_PADDING * 2);
        this.bar.setMinWidth(this.text.getBoundsInLocal().getWidth() + Constants.DEFAULT_LABEL_PADDING * 2);
    }
}