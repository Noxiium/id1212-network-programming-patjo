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

            System.out.println("-- Welcome to the chat --");
        } catch (Exception e) {
            System.out.println("Exception in Chatclient(), stacktrace: ");
            e.printStackTrace();
        }

    }

    public void writeMessage() {
        try {
            System.out.println("Write a message:");
            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()) {
                String msg = scanner.nextLine();

                bw.write(socket.getLocalPort() + ":" + msg);
                bw.newLine();
                bw.flush();
            }
        } catch (Exception e) {

            closeAllResources(this.socket, this.br, this.bw);
            System.out.println("Exception in run(), stacktrace: ");
            e.printStackTrace();
        }
    }

    public void listenForMessage() {

        try {
            while (socket.isConnected()) {
                String receivedMsg = br.readLine();
                if(receivedMsg.equals(null)){
                    closeAllResources(this.socket, this.br, this.bw);
                    System.out.println("Server is down exiting patjo-chat");
                    System.exit(0);
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

    public void printExitMessage() {
        System.out.println("Bye bye!");
    }
public static void main(String[] args) {

        ChatClient chatClient = new ChatClient();

        Thread thread1 = new Thread(() -> chatClient.writeMessage());
        Thread thread2 = new Thread(() -> chatClient.listenForMessage());

        thread1.start();
        thread2.start();

    }    

}

