package id1212.patjo.lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class GetMail {

    private String USERNAME;
    private String PASSWORD;
    private String LOGIN_COMMAND;
    private String LIST_ALL_FOLDERS_COMMAND = "A1 LIST " + "\"\"" + " *" + "\r\n";
    private String SELECT_FOLDER_COMMAND = "A1 SELECT " + "INBOX" + "\r\n";
    private String RETRIEVE_MESSAGE_1_COMMAND = "A1 FETCH 3243 BODY[TEXT]" + "\r\n";
    private BufferedReader reader;
    private OutputStream outputStream;
    private SSLSocket imapSocket;
    private SSLSocketFactory sslSocket;

    public GetMail() {
        readFromFile();
        LOGIN_COMMAND = "A1 LOGIN " + USERNAME + " " + PASSWORD + "\r\n";
        try {

            this.sslSocket = (SSLSocketFactory) SSLSocketFactory.getDefault();
            this.imapSocket = (SSLSocket) sslSocket.createSocket("webmail.kth.se", 993);

            reader = new BufferedReader(new InputStreamReader(imapSocket.getInputStream()));

            outputStream = imapSocket.getOutputStream();

            String connectionResponse = reader.readLine();
            System.out.println("S: " + connectionResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fetchMail() throws IOException {
        sendIMAPCommand(LOGIN_COMMAND);
        printIMAPResponse();

        sendIMAPCommand(LIST_ALL_FOLDERS_COMMAND);
        printIMAPResponse();

        sendIMAPCommand(SELECT_FOLDER_COMMAND);
        printIMAPResponse();

        fetchMailPrintOnlyText(RETRIEVE_MESSAGE_1_COMMAND);

        imapSocket.close();
        System.out.println("Program done");
    }

    private void printIMAPResponse() {
        String listAllResponse;
        try {
            while ((listAllResponse = reader.readLine()) != null) {
                System.out.println("S: " + listAllResponse);
                if (listAllResponse.contains("A1")) {
                    System.out.println("\n");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendIMAPCommand(String command) {
        try {
            if (command.contains("LOGIN")) {
                System.out.println("C: A1 LOGIN **** *******");
            } else {
                System.out.println("C: " + command);
            }
            outputStream.write(command.getBytes());
            outputStream.flush();
        } catch (Exception e) {

        }
    }

    private void readFromFile() {
        try {
            // String filePath =
            // "/Users/patricialagerhult/NetBeansProjects/networkprog/id1212-network-programming-patjo/Lab3/src/main/java/id1212/patjo/lab3/password.txt";
            String filePath = "C:\\Users\\PC\\git_projects\\id1212-network-programming-patjo\\Lab3\\src\\main\\java\\id1212\\patjo\\lab3\\password.txt";
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            this.USERNAME = bufferedReader.readLine();
            this.PASSWORD = bufferedReader.readLine();
            System.out.println("Username:" + USERNAME);
            System.out.println("Password:" + "*************************");
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchMailPrintOnlyText(String command) {
        sendIMAPCommand(command);
        String newLine;
        String emailContent = "";
        try {
            while ((newLine = reader.readLine()) != null) {
                emailContent += newLine;
                if (newLine.contains("A1")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pattern pattern = Pattern.compile("<p>(.*?)<br>");
        Matcher matcher = pattern.matcher(emailContent);

        while (matcher.find()) {
            String message = matcher.group(1); // Extract content within <p> tags
            System.out.println("S: The Message: " + message);
        }
    }
}
