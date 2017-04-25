package enums;

public enum SpritesheetParameters {

    EXPLOSION(48, 49, 1, 25),
    ASTEROID(32, 32, 2, 2),
    UFO(39, 30, 1, 6),
    MISSILE(31, 7, 1, 23),
    FUEL_CAN(30, 45, 1, 1),
    BOSS_PEDOBEAR(186, 280, 1, 1),
    BOSS_GRUMPYCAT(186, 280, 1, 1),
    PLAYER_DEFAULT(0, 0, 0, 0),
    PLAYER(43, 39, 1, 2);

    private int width;
    private int height;
    private int rows;
    private int cols;

    SpritesheetParameters(int width, int height, int rows, int cols) {
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }
}