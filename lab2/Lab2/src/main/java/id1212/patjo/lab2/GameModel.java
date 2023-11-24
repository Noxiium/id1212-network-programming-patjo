package id1212.patjo.lab2;

import java.util.Random;

/**
 * @author patricialagerhult && johansellerfredlund
 * 
 *         GameModel represents an instance of a game session
 *         Holds information about the current game.
 *         currentUserGuessDTO holds information about the users guess.
 */
public class GameModel {
    int NUMBER_OF_GUESSES_START_VALUE = 0;
    int randomNumber;
    int numberOfGuesses;
    int clientID;
    int currentUsersGuess;

    /**
     * Creates a new instance of gameModel
     * Generate a random number
     * NumberOfGuesses is set to 0
     * 
     * @param clientID
     */
    public GameModel(int clientID) {
        generateNewRandomNumber();
        this.numberOfGuesses = NUMBER_OF_GUESSES_START_VALUE;
        this.clientID = clientID;
    }

    /**
     * Called from ClientHandlerController when HTTP request is a guess
     * Creates a new userGuessDTO, increments numberOfGuesses
     * 
     * @param userInput
     * @return boolean
     */
    public boolean checkAnswer(int userInput) {
        this.currentUsersGuess = userInput;
        this.numberOfGuesses++;
        return userInput == randomNumber;
    }

    // Compare user's guess to the generated random number, and
    // return whether the guess is too high or too low
    public String getStringDiff(int userGuess) {
        if (userGuess > randomNumber) {
            return "too high";
        } else if (userGuess < randomNumber) {
            return "too low";
        } else {
            return "";
        }
    }

    public int getNumberOfGuesses() {
        return this.numberOfGuesses;
    }

    public int getClientID() {
        return this.clientID;
    }

    /**
     * If user guesses right and presses restart button this method is called from
     * ClientHandlerController
     * Start at new game by resetting numberOfGuesses and generate a new random
     * number.
     */
    public void restart() {
        resetNumberOfGuessesLeft();
        generateNewRandomNumber();
    }

    private void resetNumberOfGuessesLeft() {
        this.numberOfGuesses = NUMBER_OF_GUESSES_START_VALUE;
    }

    private void generateNewRandomNumber() {
        Random rand = new Random();
        this.randomNumber = rand.nextInt(100) + 1;
        System.out.println("New number is: " + randomNumber);
    }
}