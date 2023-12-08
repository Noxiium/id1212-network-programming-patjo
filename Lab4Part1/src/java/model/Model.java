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
        System.out.println("Model: constructor");
        generateRandomNumber();
        this.numberOfGuesses = NUMBER_OF_GUESSES_START_VALUE;
    }

    //Generate a random number for the current game.
    private void generateRandomNumber() {
        System.out.println("Model: generateRNDNumber");
        Random rand = new Random();
        this.randomNumber = rand.nextInt(100) + 1;

    }

    //Return the random number.
    public int getRandomNumer() {
        return this.randomNumber;
    }

    //Return number of guesses made by the user in the current game.
    public int getNumberOfGuesses() {
        return this.numberOfGuesses;
    }

    // Method called upon completing a game. 
    // Restarts the game by resetting the number of guesses made, and generating a new random number.
    public void restart() {
        resetNumberOfGuesses();
        generateRandomNumber();
    }

    // Resets the number of guesses to its initial value (0)
    private void resetNumberOfGuesses() {
        this.numberOfGuesses = NUMBER_OF_GUESSES_START_VALUE;
    }

    // Method to check if the user input matches the generated random number in the current game.
    // Increments number of guesses made
    public boolean checkAnswer(int userInput) {
        this.numberOfGuesses++;
        return userInput == randomNumber;
    }
}
