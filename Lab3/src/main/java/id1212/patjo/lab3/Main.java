package id1212.patjo.lab3;

import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author patricialagerhult
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        OUTER:
        while (true) {
            System.out.println("Select: \n 1.Get mail \n 2.Send mail");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> {

                    try {
                        GetMail getMail = new GetMail();//Part 1
                        getMail.fetchMail();
                        scanner.close();
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break OUTER;
                }
                case "2" -> {

                    try {
                        SendMail sendMail = new SendMail(); // Part 2
                        sendMail.establishConnection();

                        for (int i = 0; i < 2; i++) {
                            String userInput = scanner.nextLine();
                            sendMail.sendEncodedUserInput(userInput);
                        }
                        
                        System.out.println("Enter the sender's email:");
                        sendMail.setSenderAddress(scanner.nextLine());
                        
                        System.out.println("Enter the recipient's email:");
                        sendMail.setRecipientAddress(scanner.nextLine());
                        
                        System.out.println("Enter email subject:");
                        sendMail.setEmailSubject(scanner.nextLine());
                        
                        sendMail.sendUserInput("DATA");
                        
                        sendMail.setEmailContent(scanner.nextLine());
    
                        sendMail.sendUserInput("QUIT");
                        scanner.close();
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    break OUTER;
                }

                default ->
                    System.out.println("Please enter a valid number\n");
            }
        }

    }
}
