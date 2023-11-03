package patjo.id1212labs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * ChatServer class handles the server-side logic of the chat application. 
 * 
 * @author patricialagerhult & johansellerfredlund
 */
public class ChatServer {

    ServerSocket serverSocket;
    int serverPort = 5555;
    

    //Creates a new instance of ChatServer 
    public ChatServer() throws Exception {
        try {
            this.serverSocket = new ServerSocket(serverPort);

        } catch (Exception e) {
            serverSocket.close();
            e.printStackTrace();
        }
    }

    //Listens for incoming client connections on port 5555 
    //Creates a new ClientHandler thread for each connected client.
    public void listenForClient() throws Exception {

        while (true) {
            try {
                System.out.println("Server listen on port 5555 ...");
                Socket socket = serverSocket.accept();

                System.out.println("New client connected " + socket + " ");

                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

            } catch (Exception e) {
                serverSocket.close();
                e.printStackTrace();
            }

        }
    }
}

