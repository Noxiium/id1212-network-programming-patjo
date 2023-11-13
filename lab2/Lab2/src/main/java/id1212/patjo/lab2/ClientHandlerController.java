package id1212.patjo.lab2;

import java.net.Socket;

/**
 *
 * @author patricialagerhult
 */
public class ClientHandlerController implements Runnable {

    public ClientHandlerController(Socket socket) {

        GameModel model = new GameModel(); // TODO: SessionID?
        View view = new View(socket);
    }

    public void sendToModel(int userInput) {
        Boolean answer = model.checkAnswer(userInput);
        view.generateHTTPResponse(answer, model.getNumberOfGuessesLeft);
    }

}
