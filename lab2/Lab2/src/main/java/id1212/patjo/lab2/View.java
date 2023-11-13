package id1212.patjo.lab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author patricialagerhult
 */
public class View {
    BufferedReader request;
    InputStreamReader inputStreamReader;
    PrintStream response;

    public View(Socket socket) {
        
        try {
            
            this. inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.request = new BufferedReader(inputStreamReader);
            this.response = new PrintStream(socket.getOutputStream());
            String str = request.readLine();
            while ((str = request.readLine()) != null && str.length() > 0) {
            System.out.println(str);
        }
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        
    }
    
    public String generateHTTPResponse(Boolean answer, int numberOfGuessesLeft){
       return null; 
    }
}
