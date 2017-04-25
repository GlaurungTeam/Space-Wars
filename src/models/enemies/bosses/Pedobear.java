package models.enemies.bosses;

import contracts.Reader;
import utils.Constants;
import enums.SpritesheetParameters;
import helpers.SVGPathReader;
import javafx.scene.shape.SVGPath;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public class Pedobear extends BaseBoss {

    public Pedobear(
            double positionX,
            double positionY,
            double objectSpeed,
            BufferedImage spriteSheet,
            int health) throws FileNotFoundException {

        super(
                positionX,
                positionY,
                objectSpeed,
                spriteSheet,
                SpritesheetParameters.BOSS_PEDOBEAR.getWidth(),
                SpritesheetParameters.BOSS_PEDOBEAR.getHeight(),
                SpritesheetParameters.BOSS_PEDOBEAR.getRows(),
                SpritesheetParameters.BOSS_PEDOBEAR.getCols(),
                health
        );
        this.initializeHitbox();
    }

    @Override
    public void initializeHitbox() throws FileNotFoundException {
        Reader SVGPathReader = new SVGPathReader();
        SVGPath svgPath = new SVGPath();

        svgPath.setContent(SVGPathReader.read(Constants.BOSS_PEDOBEAR_SVGPATH_LOCATION));
        super.setSvgPath(svgPath);
    }

    @Override
    public void move() {
        if (this.getPositionY() > super.getRandomY()) {
            this.moveUp();
            return;
        }

        super.setRandomY(super.getRandom().nextInt(Constants.BOSS_POSITION_Y_BOUND));

        if (this.getPositionY() < super.getRandomY()) {
            this.moveDown();
        }
    }
}