package factories;

import javafx.scene.canvas.Canvas;
import models.enemies.bosses.Boss;
import utils.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class BossFactory {

    @SuppressWarnings("unchecked")
    public Boss initializeBoss(Canvas canvas, String bossType) throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        double startPosX = canvas.getWidth() - Constants.BOSS_RIGHT_OFFSET;
        double startPosY = canvas.getHeight() / 2;

        Class<Constants> constantsClass = (Class<Constants>) Class.forName(Constants.CONSTANTS_PACKAGE + "Constants");
        Field[] constantsFields = constantsClass.getDeclaredFields();

        BufferedImage bossSpriteSheet = null;
        double bossSpeed = 0;
        int bossHealth = 0;

        Class<? extends Annotation> healthAnnotation =
                (Class<? extends Annotation>) Class.forName(Constants.ANNOTATIONS_PACKAGE + bossType + "Health");
        Class<? extends Annotation> speedAnnotation =
                (Class<? extends Annotation>) Class.forName(Constants.ANNOTATIONS_PACKAGE + bossType + "Speed");
        Class<? extends Annotation> imageAnnotation =
                (Class<? extends Annotation>) Class.forName(Constants.ANNOTATIONS_PACKAGE + bossType + "Image");

        for (Field field : constantsFields) {
            if (field.isAnnotationPresent(healthAnnotation)) {
                bossHealth = field.getInt(null);
            } else if (field.isAnnotationPresent(speedAnnotation)) {
                bossSpeed = field.getDouble(null);
            } else if (field.isAnnotationPresent(imageAnnotation)) {
                bossSpriteSheet = ImageIO.read(new File(
                        Constants.PROJECT_PATH + field.get(null)));
            }
        }

        Class<? extends Boss> bossClass = (Class<? extends Boss>) Class.forName(Constants.BOSSES_PACKAGE + bossType);
        Constructor<? extends Boss> constructor = (Constructor<? extends Boss>) bossClass.getDeclaredConstructors()[0];

        Boss bossObject = constructor.newInstance(startPosX, startPosY, bossSpeed, bossSpriteSheet, bossHealth);
        return bossObject;
    }

}
