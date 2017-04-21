package helpers;

import contracts.Randomizer;

import java.util.Random;

public class NumberRandomizer implements Randomizer{
    private Random rnd;

    public NumberRandomizer() {
        this.rnd = new Random();
    }

    @Override
    public int getRandomNumber(int bound) {
        return this.rnd.nextInt(bound);
    }

    @Override
    public int getRandomNumber(int lowBound, int highBound) {
        return this.rnd.nextInt(highBound - lowBound) + lowBound;
    }
}
