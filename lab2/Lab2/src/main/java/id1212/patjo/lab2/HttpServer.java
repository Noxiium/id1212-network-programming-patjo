package id1212.patjo.lab2;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author patricialagerhult
 */
public class HttpServer {
    ServerSocket serverSocket;
    int serverPort = 1234;

    // Creates a new instance of ChatServer
    public HttpServer() throws Exception {
        try {
            this.serverSocket = new ServerSocket(serverPort);

        } catch (Exception e) {
            serverSocket.close();
            e.printStackTrace();
        }
    }

    // Listens for incoming client connections on port 5555
    // Creates a new ClientHandler thread for each connected client.

    public void listenForClient() throws Exception {

        while (true) {
            try {
                System.out.println("Server listen on port 1234...");

                Socket socket = serverSocket.accept();
                System.out.println("New client connected " + socket + " ");

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
