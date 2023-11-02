package patjo.id1212labs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author patricialagerhult & johansellerfredlund
 */
public class ChatServer {
    ServerSocket serverSocket;
    
    public ChatServer(){
        try {
            serverSocket = new ServerSocket(5555);
            System.out.println("Server listen on port 5555 ...");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
     
    public void listenForClient(){
    
        while(true){
            try {
                Socket socket = serverSocket.accept(); 
                System.out.println("New client connected " + socket + " ");
                ClientHandler clientHandler = new ClientHandler(socket);
        
                Thread thread = new Thread(clientHandler);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        }
    }
    
}
