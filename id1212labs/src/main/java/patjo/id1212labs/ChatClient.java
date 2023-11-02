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
    Socket socket;
    BufferedReader br;
    BufferedWriter bw;
    InputStreamReader inputStreamReader;
    OutputStreamWriter outputStreamWriter;
    
    public ChatClient(){
        try {
            this.socket = new Socket("localhost",5555);
            this.inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.br = new BufferedReader(inputStreamReader);
            this.outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            this.bw = new BufferedWriter(outputStreamWriter);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    //sendMessage
    @Override
      public void run(){
          try {
              System.out.println("Write a message:");
              Scanner scanner = new Scanner(System.in);
              
              
              while(socket.isConnected()){
                String msg = scanner.nextLine();
                bw.write(msg);
                bw.newLine();
                bw.flush();
                System.out.println("jjjj");
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
        
        
    }
      
      public void listenForMessage(){
            
          try {    
              while(socket.isConnected()){
                  System.out.println("Listening..");
                 String receivedMsg = br.readLine();
                  System.out.println(receivedMsg);
                   System.out.println("DONE");
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
    }
      
   
    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
        chatClient.listenForMessage();
        
        
}
  
}


