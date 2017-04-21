package helpers;

import java.util.Random;

public class NumberRandomizer {
    private Random rnd;

    public NumberRandomizer() {
        this.rnd = new Random();
    }

    public int getRandomNumber(int bound) {
        return this.rnd.nextInt(bound);
    }

    public int getRandomNumber(int lowBound, int highBound) {
        return this.rnd.nextInt(highBound - lowBound) + lowBound;
    }
}
