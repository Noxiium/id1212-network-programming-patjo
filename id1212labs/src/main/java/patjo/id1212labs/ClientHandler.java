package patjo.id1212labs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author patricialagerhult & johansellerfredlund
 */
public class ClientHandler implements Runnable {

    Socket clientSocket;
    BufferedReader br;
    BufferedWriter bw;
    InputStreamReader inputStreamReader;
    OutputStreamWriter outputStreamWriter;
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public ClientHandler(Socket clientSocket) {
        try {
            System.out.println(clientSocket);
            this.clientSocket = clientSocket;
            this.inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            this.br = new BufferedReader(inputStreamReader);
            this.outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            this.bw = new BufferedWriter(outputStreamWriter);
            clientHandlers.add(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (clientSocket.isConnected()) {
            try {
                String msg = br.readLine();
                sendToAll(msg);
            } catch (Exception e) {
                closeAll(clientSocket, br, bw);
            }
        }
    }

    public void sendToAll(String msg) {
        for (ClientHandler client : clientHandlers) {
            try {
                client.bw.write(msg);
                client.bw.newLine();
                client.bw.flush();

            } catch (Exception e) {
                closeAll(clientSocket, br, bw);
            }
        }
    }

    public void removeClientFromClientHandlers() {
        clientHandlers.remove(this);

    }

    public void closeAll(Socket socket, BufferedReader br, BufferedWriter bw) {
        removeClientFromClientHandlers();
        try {
            clientSocket.close();
            br.close();
            bw.close();

        } catch (Exception e) {
            closeAll(clientSocket, br, bw);
        }
    }
}
