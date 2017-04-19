package enums;

public enum SpriteSheetParameters {

    EXPLOSION(48, 49, 1, 25),
    ASTEROID(32, 32, 1, 1),
    UFO(39, 30, 1, 6),
    MISSILE(31, 7, 1, 23),
    FUEL_CAN(30, 45, 1, 1),
    BOSS_PEDOBEAR(186, 280, 1, 1),
    PLAYER_DEFAULT(0, 0, 0, 0),
    PLAYER(43, 39, 1, 2);

    private int width;
    private int height;
    private int rows;
    private int cols;

    SpriteSheetParameters(int width, int height, int rows, int cols) {
        this.setWidth(width);
        this.setHeight(height);
        this.setRows(rows);
        this.setCols(cols);
    }

    private void setWidth(int width) {
        this.width = width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    private void setRows(int rows) {
        this.rows = rows;
    }

    private void setCols(int cols) {
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