package id1212.patjo.lab2;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Simulator {

    public static void main(String[] args) {

        try {
            // Creates a new HTTPClient
            HttpClient httpClient = HttpClient.newBuilder().build();

            // Creates an initial HTTP request
            HttpRequest initialRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:1234/"))
                    .build();

            // Send initial request and save the response
            HttpResponse<String> initialResponse = httpClient.send(initialRequest,
                    HttpResponse.BodyHandlers.ofString());

            // Save all headers in the initial response into Map headers.
            // Get the content of the header Set-Cookie
            Map<String, List<String>> headers = initialResponse.headers().map();
            List<String> cookies = headers.get("Set-Cookie");
            String cookieHeaderContent = cookies.get(0);

            // Simulate the Guessing Game 100 times.
            // Save the total number of guesses made.
            int totalNumberOfGuesses = 0;
            for (int i = 0; i < 100; i++) {
                int guesses = playOneGame(httpClient, cookieHeaderContent);
                totalNumberOfGuesses += guesses;
            }

            // Calculate the avarage number of guesses
            int average = totalNumberOfGuesses / 100;
            System.out.println("Average:" + average);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Simulates a game round by creating a HTTP request containing a URL based on
    // the user's guess.
    private static int playOneGame(HttpClient httpClient, String cookieHeaderContent) {
        boolean gameDone = false;

        try {
            int guess = 0;
            while (!gameDone) {

                // Create a HTTP guess request
                HttpRequest guessRequest = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:1234//submitguess?guess=" + (guess + 1)))
                        .header("Cookie", cookieHeaderContent)
                        .build();

                // Send the HTTP guess request and save the response
                HttpResponse<String> guessResponse = httpClient.send(guessRequest,
                        HttpResponse.BodyHandlers.ofString());

                // If the body of the response contains the word "Correct",
                // the user has won and the game is over.
                if (guessResponse.body().contains("Correct")) {
                    gameDone = true;
                }
                guess++;
            }
            return guess;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
}