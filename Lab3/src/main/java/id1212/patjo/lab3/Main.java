
package id1212.patjo.lab3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import java.util.Base64;

/**
 *
 * @author patricialagerhult
 */
public class Main {

    public static void main(String[] args) {
        String from = "patlag@kth.se";
        String to = "johansf@kth.se";
        String subject = "Your Subject";
        String date = "Tue, 22 Nov 2023 12:00:00 +0000"; // Replace with an actual date
        String body = "Hello Johan,\n\nThis is the content of the email message.\nYou can write multiple lines here.\n\nRegards,\n[Patlag]";

        // Construct the email message string
        String emailData = "From: " + from + "\r\n"
                + "To: " + to + "\r\n"
                + "Subject: " + subject + "\r\n"
                + "Date: " + date + "\r\n"
                + "\r\n" // Empty line separating headers from body
                + body + "\r\n."; // Message body and terminating period
        
        Scanner scanner = new Scanner(System.in);

        //GetMail GetMail = new GetMail();  //Part 1
        SendMail sendMail = new SendMail(); // Part 2 
        
        try {
            sendMail.establishConnection();
            String userUserName = scanner.nextLine();
            sendMail.sendEncodedUserInput(userUserName);
            String userUserPassword = scanner.nextLine();
            sendMail.sendEncodedUserInput(userUserPassword);
            
            /*while(true){
                String userInput = scanner.nextLine();
                if(userInput.equals("send"))
                    break;
                sendMail.sendUserInput(userInput);
            }*/
            sendMail.sendUserInput("MAIL FROM:<patlag@kth.se>");
            sendMail.sendUserInput("RCPT TO:<johansf@kth.se>");
            sendMail.sendUserInput("DATA");
            sendMail.sendUserInput(emailData);
            sendMail.sendUserInput("QUIT");
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
