package id1212.patjo.lab2;

import java.net.Socket;


/**
 *
 * @author patricialagerhult
 */
public class ClientHandlerController implements Runnable {
    GameModel gameModel;
    View view;
    
    
    public ClientHandlerController(Socket socket) {
        this.gameModel = new GameModel(); // TODO: SessionID?
        this.view = new View(socket);
    }

    public void sendToModel(int userInput) {
        Boolean answer = gameModel.checkAnswer(userInput);

        view.generateHTTPResponse(answer, gameModel.getNumberOfGuessesLeft());
    }

    @Override
    public void run (){
    }
}
