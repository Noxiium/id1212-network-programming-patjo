package id1212.patjo.lab2;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * HttpServer Class listens for incoming client on port 1234.
 * For each connected client, a new ClientHandlerController thread is created to
 * handle communication.
 * 
 * @author patricialagerhult && johansellerfredlund
 */

public class HttpServer {
    ServerSocket serverSocket;
    int serverPort = 1234;

    /**
     * Creates a new instance of HttpServer
     */
    public HttpServer() throws Exception {
        try {
            this.serverSocket = new ServerSocket(serverPort);

        } catch (Exception e) {
            serverSocket.close();
            e.printStackTrace();
        }
    }

    /**
     * Listens for incoming client connections on port 1234 and,
     * creates a new ClientHandlerController thread for each connected client.
     */
    public void listenForClient() throws Exception {

        while (true) {
            try {
                // System.out.println("Server listen on port 1234...");

                Socket socket = serverSocket.accept();
                // System.out.println("New client request " + socket + " ");

                ClientHandlerController clientHandlercontr = new ClientHandlerController(socket);
                Thread clientHandlercontrThread = new Thread(clientHandlercontr);
                clientHandlercontrThread.start();

            } catch (Exception e) {
                serverSocket.close();
                e.printStackTrace();
            }

        }
    }
}
