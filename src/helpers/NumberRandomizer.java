package helpers;

import contracts.Randomizer;

import java.util.Random;

public class NumberRandomizer implements Randomizer {

    private Random random;

    public NumberRandomizer() {
        this.random = new Random();
    }

    @Override
    public int getRandomNumber(int bound) {
        return this.random.nextInt(bound);
    }

    @Override
    public int getRandomNumber(int lowBound, int highBound) {
        return this.random.nextInt(highBound - lowBound) + lowBound;
    }
}