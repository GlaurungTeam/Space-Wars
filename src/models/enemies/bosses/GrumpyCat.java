package models.enemies.bosses;

import enums.SpriteSheetParameters;
import helpers.Reader;
import helpers.SVGPathReader;
import javafx.scene.shape.SVGPath;
import utils.Constants;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

public class GrumpyCat extends BaseBoss {

    public GrumpyCat(double positionX, double positionY, double objectSpeed,
                     BufferedImage spriteSheet, int health) throws FileNotFoundException {

        super(positionX, positionY, objectSpeed, spriteSheet,
                SpriteSheetParameters.BOSS_GRUMPYCAT.getWidth(),
                SpriteSheetParameters.BOSS_GRUMPYCAT.getHeight(),
                SpriteSheetParameters.BOSS_GRUMPYCAT.getRows(),
                SpriteSheetParameters.BOSS_GRUMPYCAT.getCols(),
                health);
        this.initializeHitbox();
    }

    @Override
    public void initializeHitbox() throws FileNotFoundException {
        Reader SVGPathReader = new SVGPathReader();
        SVGPath svgPath = new SVGPath();

        svgPath.setContent(SVGPathReader.read(Constants.BOSS_PEDOBEAR_SVGPATH_LOCATION)); //TODO uses pedobear hitbox
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
