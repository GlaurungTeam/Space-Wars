package NewGameLayout.Entities;

public class Asteroid extends objectClasses.Sprite {
    public boolean isHit = false;
    private double speed;

    public Asteroid(double speed){
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    /*public static void initializeAsteroids(Asteroid[] asteroids, Canvas canvas) {
        for (int i = 0; i < asteroids.length; i++) {
            Asteroid currentAsteroid = new Asteroid(2.5);//HardCoded
            String path = "resources/asteroid/asteroid" + String.valueOf(SpawnCoordinates.getRandom(4)) + ".png";
            Image image = new Image(path);

            currentAsteroid.setImage(image);
            currentAsteroid.setPosition(SpawnCoordinates.getSpawnX(canvas), SpawnCoordinates.getSpawnY(canvas), 2.5);//HardCoded
            asteroids[i] = currentAsteroid;
        }
    }*/
}
