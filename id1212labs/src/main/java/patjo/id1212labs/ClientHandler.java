package patjo.id1212labs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * ClientHandler class handles all connected ChatClients in the chat
 * application.
 * Listens to messages from ChatClients and broadcasts them to all connected
 * ChatClients.
 * 
 * @author patricialagerhult and johansellerfredlund
 */
public class ClientHandler implements Runnable {
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    Socket socket;
    BufferedReader br;
    BufferedWriter bw;
    InputStreamReader inputStreamReader;
    OutputStreamWriter outputStreamWriter;
    int userID;

    // Creates an instance of ClientHandler and saves a new ChatClient in an
    // ArrayList.
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.br = new BufferedReader(inputStreamReader);
            this.outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            this.bw = new BufferedWriter(outputStreamWriter);
            this.userID = socket.getPort();
            clientHandlers.add(this);
            sendToAll("User " + socket.getPort() + " joined", true);
            bw.write("-- Welcome to the Chat --");
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listens to messages from a ChatClient and broadcasts them to all ChatClients
    // in the ArrayList.
    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                String msg = br.readLine();
                sendToAll(msg, false);
            }
        } catch (Exception e) {
            closeAll(socket, br, bw);
            sendToAll("User " + this.userID + " left the chat", true);
        }
    }

    public synchronized void sendToAll(String msg, boolean flag) {
        for (ClientHandler client : clientHandlers) {
            try {
                if (this.userID != client.userID || flag) {
                    client.bw.write(msg);
                    client.bw.newLine();
                    client.bw.flush();
                }
            } catch (Exception e) {
                closeAll(socket, br, bw);
            }
        }
    }

    // Close socket, BufferedReader,BufferedWriter and remove ChatClient from the
    // ArrayList.
    public void closeAll(Socket socket, BufferedReader br, BufferedWriter bw) {
        clientHandlers.remove(this);

        try {
            if (socket != null)
                socket.close();
            if (br != null)
                br.close();
            if (bw != null)
                bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}