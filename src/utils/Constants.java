package utils;

import annotations.*;

public class Constants {

    //Object speed value
    public static final double EXPLOSION_SPEED = 0.1;
    public static final int ASTEROID_SPEED_EASY = 2;
    public static final int ASTEROID_SPEED_HARD = 3;
    public static final int UFO_SPEED_EASY = 3;
    public static final int UFO_SPEED_HARD = 4;
    public static final int FUELCAN_SPEED = 1;
    public static final double PLAYER_SPEED = 3;
    public static final double BACKGROUND_SPEED = 1;
    public static final double OBJECT_SPEED_UP_VALUE = 0.00002;

    @PedobearSpeed
    public static final double PEDOBEAR_SPEED = 3;

    @GrumpyCatSpeed
    public static final double GRUMPYCAT_SPEED = 5;

    public static final double MISSILE_SPEED = 5;

    //Sound effects
    public static final String PLAYER_SHOOT_SOUND = "src/resources/sound/lasergun.mp3";
    public static final String ASTEROID_HIT_SOUND = "src/Resources/sound/explosion2.mp3";
    public static final String UFO_HIT_SOUND = "src/Resources/sound/explossion.mp3";
    public static final String SOUNDTRACK_PATH = "src/resources/sound/soundtrack.mp3";

    //Object images
    public static final String MISSILE_SPRITESHEET_IMAGE = "resources/missiles/largeMissiles.png";
    public static final String EXPLOSION_SPRITESHEET_IMAGE = "resources/explosions/rsz_explosion-spritesheet.png";
    public static final String SPACESHIP_SPRITESHEET_IMAGE = "resources/spaceship/spaceshipSprites4.png";
    public static final String SPACESHIP_SPRITESHEET_IMAGE_HIT = "resources/spaceship/spaceshipHit.png";
    public static final String SPACESHIP_SPRITESHEET_IMAGE_UP_HIT = "resources/spaceship/spaceshipUpHit.png";
    public static final String SPACESHIP_SPRITESHEET_IMAGE_UP = "resources/spaceship/spaceshipUp.png";
    public static final String SPACESHIP_SPRITESHEET_IMAGE_DOWN = "resources/spaceship/spaceshipDown.png";
    public static final String SPACESHIP_SPRITESHEET_IMAGE_DOWN_HIT = "resources/spaceship/spaceshipDownHit.png";
    public static final String EASY_LEVEL_BACKGROUND = "resources/backgrounds/easyBackground.png";
    public static final String HARD_LEVEL_BACKGROUND = "resources/backgrounds/hardBackground.png";
    public static final String FUELCAN_IMAGE = "resources/fuelcan/fuelCan.png";
    public static final String UFO_SPRITESHEET = "resources/ufo/ufo.png";
    public static final String ASTEROID_SPRITESHEET = "resources/asteroid/asteroid.png";
    public static final String EARTH_IMAGE = "resources/planets/earth.png";
    public static final String SUN_IMAGE = "resources/planets/sun.png";

    @PedobearImage
    public static final String BOSS_PEDOBEAR_IMAGE = "resources/bosses/pedobear.png";

    @GrumpyCatImage
    public static final String BOSS_GRUMPYCAT_IMAGE = "resources/bosses/grumpycat.png";

    //Misc
    public static final int ASTEROID_COUNT_EASY = 20;
    public static final int ASTEROID_COUNT_HARD = 30;
    public static final double PLAYER_START_X = 100;
    public static final int PLAYER_LIVES = 3;
    public static final int PLAYER_SPEED_MULTIPLIER = 1;
    public static final int UFO_COUNT_EASY = 2;
    public static final int UFO_COUNT_HARD = 4;
    public static final int TOTAL_FUEL = 50;
    public static final int FUEL_RATE_FAST = 3;
    public static final int FUEL_RATE_SLOW = 1;
    public static final int FUELCAN_SPAWN_X = 150;
    public static final int FUELCAN_SPAWN_Y = 150;
    public static final int FUELCAN_RESTART_LEFT_COORDINATE = -200;
    public static final int BOSS_POSITION_Y_BOUND = 5000;
    public static final int BOSS_RIGHT_OFFSET = 200;
    public static final int MISSILE_POSITION_Y_OFFSET = 100;
    public static final int MISSILE_SPEED_MULTIPLIER = 2;
    public static final int PLANET_START_X = 500;
    public static final int PLANET_START_Y = 196;
    public static final int DEFAULT_LABEL_PADDING = 5;
    public static final double SOUND_VOLUME = 0.1;
    public static final boolean DEFAULT_BOOLEAN_VALUE_FOR_PRESSED_KEY = false;
    public static final int OBJECT_RESTART_LEFT_COORDINATE = -20;
    public static final int HEIGHT_OFFSET = 37;

    //Points values
    public static final int BOSS_POINTS_ON_KILL = 6;
    public static final int ASTEROID_POINTS_ON_KILL = 1;
    public static final int UFO_POINTS_ON_KILL = 3;
    public static final int POINTS_TILL_BOSS = 10;
    public static final long START_POINTS = 0;

    //Object health values
    public static final int ASTEROID_HEALTH_EASY = 1;
    public static final int ASTEROID_HEALTH_HARD = 2;
    public static final int PLAYER_DEFAULT_HEALTH = 1;
    public static final int UFO_HEALTH_EASY = 1;
    public static final int UFO_HEALTH_HARD = 2;

    @PedobearHealth
    public static final int BOSS_PEDOBEAR_HEALTH = 5;

    @GrumpyCatHealth
    public static final int BOSS_GRUMPYCAT_HEALTH = 10;

    //Misc file paths
    public static final String BOSS_PEDOBEAR_SVGPATH_LOCATION = "resources/bosses/pedobear_hitbox.txt";
    public static final String PLAYER_SVGPATH_LOCATION = "resources/spaceship/spaceship_hitbox.txt";
    public static final String PROJECT_PATH = System.getProperty("user.dir") + "/src/";
    public static final String LEADERBOARD_FILE_LOCATION = "leaderboard/leaderboard.ser";
    public static final String CONSTANTS_PACKAGE = "utils.";
    public static final String BOSSES_PACKAGE = "models.enemies.bosses.";
    public static final String ENEMIES_PACKAGE = "models.enemies.genericEnemies.";
    public static final String ANNOTATIONS_PACKAGE = "annotations.";

    //Resolution
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static void setResolutionConstants(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }
}