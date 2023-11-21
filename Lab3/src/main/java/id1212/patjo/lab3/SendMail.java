package id1212.patjo.lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author patricialagerhult && johansellerfredlund
 */
public class SendMail {
    Socket socket;
    OutputStream outputStream;
    BufferedReader reader;
    PrintWriter writer;
    Boolean extendedSMTP;
    Pattern pattern;
    Matcher matcher;
    
    
    public SendMail() {
        try {
            this.socket = new Socket("smtp.kth.se", 587);
            this.outputStream = this.socket.getOutputStream();
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.outputStream, true);
            this.pattern = Pattern.compile("^250\\s");
           
            
            String response = reader.readLine();
            System.out.println(response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    

    public boolean EHLO() throws IOException{
        
        writer.println("EHLO patlag@kth.se");
        writer.flush();
        
        String response;
        
        while((response = reader.readLine()) != null){
            System.out.println(response);
        
            if(response.contains("250-STARTTLS")){
                this.extendedSMTP = true;
            }
            
            matcher = pattern.matcher(response);

                if (matcher.find()) {
                    break;
                }
            

        }
        
        return extendedSMTP;  
    }
    public void startTLS() throws IOException{
        writer.println("STARTTLS");
        writer.flush();
        
            System.out.println(reader.readLine());

    }
    
      public void authLogin() throws IOException{
          
        writer.println("AUTH LOGIN");
        writer.flush();
        
        String response;
        
        response = reader.readLine();
            System.out.println(response);
        
            //if(response.contains("250-STARTTLS"))
              //  break;
        }
        
    }
    
    

