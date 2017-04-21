package factories;

import javafx.scene.canvas.Canvas;
import contracts.Boss;
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

    private static final String CONSTANTS_CLASS_NAME = "Constants";
    private static final String HEALTH_SUFFIX = "Health";
    private static final String SPEED_SUFFIX = "Speed";
    private static final String IMAGE_SUFFIX = "Image";

    private static final double INITIAL_BOSS_SPEED = 0.0;
    private static final int INITIAL_BOSS_HEALTH = 0;

    private static final int FIRST_CONSTRUCTOR_INDEX = 0;
    private static final int DIVIDER = 2;


    @SuppressWarnings("unchecked")
    public Boss initializeBoss(Canvas canvas, String bossType) throws IOException,
            ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        double startPosX = canvas.getWidth() - Constants.BOSS_RIGHT_OFFSET;
        double startPosY = canvas.getHeight() / DIVIDER;

        Class<Constants> constantsClass = (Class<Constants>) Class.forName(Constants.CONSTANTS_PACKAGE + CONSTANTS_CLASS_NAME);
        Field[] constantsFields = constantsClass.getDeclaredFields();

        BufferedImage bossSpriteSheet = null;
        double bossSpeed = INITIAL_BOSS_SPEED;
        int bossHealth = INITIAL_BOSS_HEALTH;

        Class<? extends Annotation> healthAnnotation =
                (Class<? extends Annotation>) Class.forName(Constants.ANNOTATIONS_PACKAGE + bossType + HEALTH_SUFFIX);
        Class<? extends Annotation> speedAnnotation =
                (Class<? extends Annotation>) Class.forName(Constants.ANNOTATIONS_PACKAGE + bossType + SPEED_SUFFIX);
        Class<? extends Annotation> imageAnnotation =
                (Class<? extends Annotation>) Class.forName(Constants.ANNOTATIONS_PACKAGE + bossType + IMAGE_SUFFIX);

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
        Constructor<? extends Boss> constructor = (Constructor<? extends Boss>) bossClass.getDeclaredConstructors()[FIRST_CONSTRUCTOR_INDEX];

        Boss bossObject = constructor.newInstance(startPosX, startPosY, bossSpeed, bossSpriteSheet, bossHealth);
        return bossObject;
    }

}
