package id1212.patjo.lab2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.net.ssl.SSLSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * ClientHandlerController Class handles the HTTPRequest from clients in the
 * Guessing Game.
 * Each instance of this Class is associated with specific client connection.
 * 
 * @author patricialagerhult && johansellerfredlund
 */

public class ClientHandlerController implements Runnable {

    private static List<GameModel> clients = new ArrayList<>();
    private static int generateClientID = 20;
    private SSLSocket socket;
    private BufferedReader br;
    private InputStreamReader inputStreamReader;
    private GameModel gameModel;
    private String requestLine;

    /**
     * Enum representing the type of the HTTP Request.
     * 
     */
    enum RequestType {
        favicon,
        initialConnection,
        guess
    }

    /**
     * Creates an instance of ClientHandlerController.
     * 
     * @param socket . The Client's socket used for communication.
     */

    public ClientHandlerController(SSLSocket socket) {
        try {
            this.socket = socket;
            this.inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.br = new BufferedReader(inputStreamReader);

        } catch (Exception e) {
            e.printStackTrace();
            closeAllResources();
        }
    }
    
    
      @Override
    public void run() {
        try {
            // Get what type of request it is
            RequestType requestType = getHTTPRequestType();

            // Create a View for handling the HTTP respons
            View view = new View(socket);

            // Check the client id from cookie id in request
            int clientIDFromRequest = getClientIDFromRequest();

            // Set the clients existing GameModel or create a new one.
            int clientID = setClientModel(clientIDFromRequest);

            // Use the view to initiate an HTTP response based on the type of the incoming
            // request.
            switch (requestType) {
                case favicon -> view.faviconResponse();

                case initialConnection -> view.initialHTTPResponse(clientID, gameModel.getNumberOfGuesses());

                case guess -> handleGuessRequest(clientID, view);
            }
            br.close();
            inputStreamReader.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("###### Caught error #####");
            //System.out.println("\"this.requestLine\" is null");
        }

    }

    /**
     * Reads the first line of the HTTP request
     * Matches it with strings and return the enum RequestType
     * 
     * @Throws Exception if this.requestLine is null
     * 
     * @return the type of request in the first line of the HTTP header
     */
    private RequestType getHTTPRequestType() throws Exception {
        this.requestLine = br.readLine();
        System.out.println(requestLine);
        // System.out.println("Request type: " + requestLine);
        if (this.requestLine.contains("/favicon.ico")) {
            return RequestType.favicon;
        } else if (this.requestLine.contains("/submitguess?")) {
            return RequestType.guess;
        } else {
            return RequestType.initialConnection;
        }
    }

    /**
     * 
     * Extract the users guess by matching the pattern "guess=" to the requestLine.
     * The character(s) after the "=" represents the user's input.
     * 
     * @return int value of the users input if found, otherwise return -1
     */
    private int extractUserGuess() {
        Pattern pattern = Pattern.compile("guess=(\\d+)");
        Matcher matcher = pattern.matcher(this.requestLine);
        try {
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("User did not write a number");
            return -1;
        }
    }

    /**
     * Handles the request when a users makes a guess.
     * If the guess is correct a call to the model is made to restart it's values.
     * 
     * @param clientID , the Clients unique identifier.
     * @param view     , the View that handles the HTTP response
     */
    private void handleGuessRequest(int clientID, View view) {
        int userGuess = extractUserGuess();
        boolean answer = gameModel.checkAnswer(userGuess);
        int numberOfGuesses = gameModel.getNumberOfGuesses();

        // If user guess is not an integer, calls method in view to send an error
        // response
        // Otherwise, handle the guess.
        if (userGuess == -1) {
            view.inputFailResponse(clientID, gameModel.getNumberOfGuesses());
        } else {

            // If the user guess is correct, restart the games numberOfGuesses
            if (answer) {
                gameModel.restart();
            }
            view.guessHTTPResponse(answer, numberOfGuesses, clientID, userGuess, gameModel.getStringDiff(userGuess));
        }
    }

  

    /**
     * Set the client's GameModel. If the client ID already exists, retrieve the
     * associated GameModel. Otherwise, create a new GameModel and add it to the
     * list.
     *
     * @param clientID , the clients unique identifier, extracted from cookie in the
     *                 HTTP Request
     * 
     * @return the clientID associated with the set GameModel
     */
    private int setClientModel(int clientID) {
        if (clientID != -1) {
            // Search for an existing GameModel associated with the clientID
            for (GameModel clientModel : clients) {
                if (clientModel.getClientID() == clientID) {
                    this.gameModel = clientModel;
                    // System.out.println("Found GameModel. ClientId: " +
                    // clientModel.getClientID());
                    return clientModel.getClientID();
                }
            }
        }
        // If the clientID is not found, create a new GameModel and add it to the
        // GameModel List
        this.gameModel = new GameModel(generateClientID);
        // System.out.println("Created a new GameModel. ClientID: " +
        // this.gameModel.getClientID());
        generateClientID++;
        clients.add(gameModel);
        return (this.gameModel.getClientID());

    }

    /**
     * Extracts the clientID from the set cookie in the HTTP Request
     *
     * @return the clientID or -1 if not found
     */
    private int getClientIDFromRequest() {
        String request;

        // Iterate through each line in the request until locating the
        // Set-Cookie line that includes the clientID.
        // Call the extractClientIDFromLine method to extract the clientID.
        try {
            while ((request = br.readLine()) != null && !request.isEmpty()) {
                if (request.contains("clientID")) {
                    return extractClientIDFromLine(request);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Extracts the clientID from a given line in the HTTP request.
     * 
     * @param request, the line from the HTTP request containing the clientID
     * 
     * @return the clientID, or -1 if not found
     */
    private int extractClientIDFromLine(String request) {

        // Define the pattern to match, "clientID=" followed by one or more digits,
        // and match it to the given request line
        Pattern pattern = Pattern.compile("clientID=(\\d+)");
        Matcher matcher = pattern.matcher(request);

        // If found, return the digit(s)
        // Otherwise, return -1
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            return -1;
        }
    }

    /**
     * 
     * Close resources associated with the client.
     * <Socket>, <BufferedReader>, <InputStreamReader>,<OutputStream>,
     * and remove the gameModel from the ArrayList.
     */
    private void closeAllResources() {
        clients.remove(this.gameModel);
        try {
            if (socket != null)
                socket.close();
            if (br != null)
                br.close();
            if (inputStreamReader != null)
                inputStreamReader.close();
          
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
