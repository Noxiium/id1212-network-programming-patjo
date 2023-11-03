package patjo.id1212labs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author patricialagerhult & johansellerfredlund
 */
public class ChatClient extends Thread {
    int serverPort = 5555;
    Socket socket;
    BufferedReader br;
    BufferedWriter bw;
    InputStreamReader inputStreamReader;
    OutputStreamWriter outputStreamWriter;

    public ChatClient() {
        try {

            this.socket = new Socket("localhost", serverPort);
            this.inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.br = new BufferedReader(inputStreamReader);
            this.outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            this.bw = new BufferedWriter(outputStreamWriter);

        } catch (Exception e) {
            System.out.println("Host is down, try again later");
            System.exit(0);
        }

    }

    public void writeMessage() {
        try {
            System.out.println("Write a message:");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String msg = scanner.nextLine();
                bw.write(socket.getLocalPort() + ": " + msg);
                bw.newLine();
                bw.flush();
            }
        } catch (Exception e) {
            closeAllResources(this.socket, this.br, this.bw);
            System.out.println("Scanner or socket error.");
        }
    }

    public void listenForMessage() {

        try {
            while (true) {
                String receivedMsg = br.readLine();
                if (receivedMsg.equals(null)) {
                    throw new Exception();
                }
                System.out.println(receivedMsg);

            }
        } catch (Exception e) {
            closeAllResources(this.socket, this.br, this.bw);
            System.out.println("Server is down exiting patjo-chat");
            System.exit(0);
        }
    }

    public void closeAllResources(Socket socket, BufferedReader br, BufferedWriter bw) {
        try {
            if (socket != null)
                socket.close();
            if (br != null)
                br.close();
            if (bw != null)
                bw.close();

        } catch (Exception e) {
            System.out.println("Exception in closeAllResources(), stacktrace: ");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ChatClient chatClient = new ChatClient();

        Thread thread1 = new Thread(() -> chatClient.writeMessage());
        Thread thread2 = new Thread(() -> chatClient.listenForMessage());

        thread1.start();
        thread2.start();

    }

}
