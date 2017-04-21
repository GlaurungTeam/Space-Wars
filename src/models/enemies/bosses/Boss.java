package models.enemies.bosses;

import javafx.scene.shape.SVGPath;
import models.gameObjects.HealthableGameObject;

import java.io.FileNotFoundException;

public interface Boss extends HealthableGameObject{

    SVGPath getSvgPath();

    void setSvgPath(SVGPath svgPath);

    boolean isVisible();

    void setVisible(boolean visible);

    void move();

    void initializeHitbox() throws FileNotFoundException;
}