package id1212.patjo.lab2;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * The View Class handles the HTTP responses to be sent back to the client.
 *
 * @author patricialagerhult && johansellerfredlund
 *
 */
public class View {

    PrintStream response;
    OutputStream outputStream;

    /**
     * Creates an instance of View.
     * Initializes the OutputStream and PrintStream based on the provided socket.
     *
     * @param socket, the socket associated with the client
     */
    public View(Socket socket) {

        try {
            this.outputStream = socket.getOutputStream();
            this.response = new PrintStream(this.outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
        /**
     * Creates and sends the initial respond when the game starts.
     * 
     * @param clientID,        unique identifier for the client.
     * @param numberOfGuesses, the current number of guesses made.
     */
    public void initialHTTPResponse(int clientID, int numberOfGuesses) {

        try {
            sendHTTPHeader(clientID);
            commonHTTPBody(numberOfGuesses);
            scriptHTTPBody();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.flush();
        response.close();
    }
    
    /**
     * Sends a favicon response,containing only the HTTP header.
     * 
     */
    public void faviconResponse() {
        Integer placeholder = null;

        try {
            sendHTTPHeader(placeholder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.flush();
        response.close();
    }
    
    /**
     * Creates and sends HTTP responses based on the game state.
     * Includes information about the users guess, total number of guesses made,
     * and the game outcome.
     * 
     * @param answer,          a boolean value indicating whether the guess is
     *                         correct or not.
     * @param numberOfGuesses, the current number of guesses made.
     * @param clientID,        unique identifier for the client.
     * @param userGuess,       the number which represents the user's guess,
     * @param guessDiff,       string that displays whether the guess number is too
     *                         high or too low.
     */
    public void guessHTTPResponse(Boolean answer, int numberOfGuesses, int clientID, int userGuess, String guessDiff) {

        try {
            sendHTTPHeader(clientID);
            commonHTTPBody(numberOfGuesses);
            response.print("<p> Your guess <b> " + userGuess + "</b> is:" + " </p>");

            if (!answer) {
                response.println("<h4>" + guessDiff + "</h4>"
                        + "<p> </p>");
                scriptHTTPBody();
            } else {
                restartHTTPResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.flush();
        response.close();
    }



    /**
     * Creates and sends an error response when the user input is invalid.
     *
     * @param clientID,        unique identifier for the client.
     * @param numberOfGuesses, the current number of guesses made.
     */
    public void inputFailResponse(int clientID, int numberOfGuesses) {

        try {

            sendHTTPHeader(clientID);
            commonHTTPBody(numberOfGuesses);
            errorResponse();
            scriptHTTPBody();

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.flush();
        response.close();
    }

    /**
     * Send HTTPHeader with optional Set-Cookie information.
     *
     * @param clientID, unique identifier for a client.
     */
    
    
    
    //------------- HTTP RESPONSE CONTENT  ------------//
    
    private void sendHTTPHeader(Integer clientID) {

        response.println("HTTP/1.1 200 OK");
        response.println("Server: Patjo 0.1 Beta");
        response.println("Content-Type: text/html");
        if (clientID != null) {
            response.println("Set-Cookie: clientID=" + clientID + "; expires=Wednesday,31-Dec-23 21:00:00 GMT");
        }
        response.println();
    }

    /**
     * Creates the first part of the HTTP Body , shared by multiple responses.
     *
     * @param numberOfGuesses, the current number of guesses made.
     */
    private void commonHTTPBody(int numberOfGuesses) {
        response.print("""
                <html>
                <head>
                <title>Guess Number Game!</title>
                </head>
                <body>
                <h1>Guess the Number</h1>
                <p>Guess a number between 1-100:</p>
                <p> Number of guesses made: <b> """ + numberOfGuesses + " <b> </p>");
    }

    //
    private void errorResponse() {

        response.print("<h1>Please enter a valid number!</h1>");
    }

    /**
     * Creates the second part of the HTTP Body, containing a script.
     *
     * @param numberOfGuesses, the current number of guesses made.
     */
    private void scriptHTTPBody() {

        response.print("""
                <form>
                <input type="text" id="guess" name="guess">
                <input type="button" value="guess" onclick="sendUserInput()">
                </form>
                <script type="text/javascript">
                function sendUserInput() {
                var userInput = document.getElementById("guess").value;
                window.location.href = "http://localhost:1234/submitguess?guess=" + encodeURIComponent(userInput);
                }
                </script>
                </body>
                </html>""");

    }

    /**
     * Creates a part of HTTP Body, used when we want to restart the game.
     */
    private void restartHTTPResponse() {

        response.print("<b><h4> Correct! </h4></b>");
        response.print("""
                <p> </p>
                <input type="button" value="Restart" onclick="restart()">
                </form>
                <script type="text/javascript">
                function restart() {
                   window.location.href = "http://localhost:1234/";
                }
                </script>
                </body>
                </html>""");

    }
}
