package id1212.patjo.lab2;

/**
 * Main Class for the Guessing Game.
 * Creates an instance of HttpServer.
 * 
 * @author patricialagerhult && johansellerfredlund
 */

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Starting http-server...");
            HttpServer httpServer = new HttpServer();
            httpServer.listenForClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
