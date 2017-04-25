package contracts;

public interface Randomizer {

    int getRandomNumber(int bound);

    int getRandomNumber(int lowBound, int highBound);
}