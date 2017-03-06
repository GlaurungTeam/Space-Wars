package entities;

public class Constants {
    //Object speed value
    public static final double EXPLOSION_SPEED = 0.1;
    public static final double ASTEROID_SPEED = 2.5;
    public static final int FUELCAN_SPEED = 1;
    public static final int UFO_SPEED = 2;
    public static final double PLAYER_SPEED = 3;
    public static final double BACKGROUND_SPEED = 1;
    public static final double OBJECT_SPEED_UP_VALUE = 0.00002;

    //Sound effects
    public static final String PLAYER_SHOOT_SOUND = "src/resources/sound/lasergun.mp3";
    public static final String ASTEROID_HIT_SOUND = "src/Resources/sound/explosion2.mp3";
    public static final String UFO_HIT_SOUND = "src/Resources/sound/explossion.mp3";
    public static final String SOUNDTRACK_PATH = "src/resources/sound/soundtrack.mp3";

    //Object images
    public static final String MISSILE_SPRITESHEET_IMAGE = "/src/resources/missiles/largeMissiles.png";
    public static final String EXPLOSION_SPRITESHEET_IMAGE = "/src/resources/explosions/rsz_explosion-spritesheet.png";
    public static final String SPACESHIP_SPRITESHEET_IMAGE = "/src/resources/spaceship/spaceshipSprites4.png";
    public static final String FUELCAN_IMAGE = "resources/fuelcan/fuelCan.png";
    public static final String UFO_IMAGE = "resources/UFO/ufo_";
    public static final String ASTEROID_IMAGE = "resources/asteroid/asteroid";
    public static final String EARTH_IMAGE = "resources/earth.png";
    public static final String SUN_IMAGE = "resources/sun.png";
    public static final String SPACE_IMAGE = "resources/space.png";

    //Misc
    public static final double SOUND_VOLUME = 0.1;
    public static final int DEFAULT_LABEL_PADDING = 5;
    public static final String LEADERBOARD_FILE_LOCATION = "src/leaderboard/leaderboard.ser";
    public static final int MISSILE_SPEED_MULTIPLIER = 2;
    public static final int ASTEROIDS_COUNT = 20;
    public static final int TOTAL_FUEL = 50;
    public static final int FUEL_RATE_FAST = 3;
    public static final int FUEL_RATE_SLOW = 1;
    public static final int PLAYER_LIVES = 3;
    public static final int UFO_COUNT = 2;
    public static final int ASTEROID_HEALTH = 1;
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final boolean DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY = false;
    public static final long START_POINTS = 0;
}