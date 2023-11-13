package id1212.patjo.lab2;

import java.util.Random;

/**
 *
 * @author patricialagerhult
 */
public class GameModel {
    final int randomNumber;
    int numberOfGuessesLeft;

    public GameModel() {
        Random rand = new Random();
        this.randomNumber = rand.nextInt(100) + 1;
        this.numberOfGuessesLeft = 3;
    }

    public boolean checkAnswer(int userInput) {
        this.numberOfGuessesLeft--;
        return (userInput == randomNumber);
    }

    public int getNumberOfGuessesLeft() {
        return this.numberOfGuessesLeft;
    }

}
