package model;

import java.util.Random;

/**
 * GameModel represents an instance of a game session. Holds information about
 * the current game.
 *
 * @author patricialagerhult && johansellerfredlund
 */
public class Model {

    private final int NUMBER_OF_GUESSES_START_VALUE = 0;
    private int randomNumber;
    private int numberOfGuesses;

    /**
     * Creates a new instance of gameModel 
     * Generates a random number, and NumberOfGuesses is set to 0
     */
    public Model() {
        generateRandomNumber();
        this.numberOfGuesses = NUMBER_OF_GUESSES_START_VALUE;
    }

    private void generateRandomNumber() {
        Random rand = new Random();
        this.randomNumber = rand.nextInt(100) + 1;

    }

    //Return the random number.
    public int getRandomNumer() {
        return this.randomNumber;
    }

    public int getNumberOfGuesses() {
        return this.numberOfGuesses;
    }

    public void restart() {
        resetNumberOfGuesses();
        generateRandomNumber();
    }

    private void resetNumberOfGuesses() {
        this.numberOfGuesses = NUMBER_OF_GUESSES_START_VALUE;
    }

    public boolean checkAnswer(int userInput) {
        this.numberOfGuesses++;
        return userInput == randomNumber;
    }
}
