package id1212.patjo.lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class GetMail {
    String USERNAME;
    String PASSWORD;
    String LOGIN_COMMAND;
    String LIST_ALL_FOLDERS_COMMAND = "A1 LIST " + "\"\"" + " *" + "\r\n";
    String SELECT_FOLDER_COMMAND = "A1 SELECT " + "INBOX" + "\r\n";
    String RETRIEVE_MESSAGE_1_COMMAND = "A1 FETCH 3243 BODY[TEXT]" + "\r\n";

    public GetMail() {
        readFromFile();
        LOGIN_COMMAND = "A1 LOGIN " + USERNAME + " " + PASSWORD + "\r\n";
        try {

            SSLSocketFactory sslSocket = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket imapSocket = (SSLSocket) sslSocket.createSocket("webmail.kth.se", 993);

            BufferedReader reader = new BufferedReader(new InputStreamReader(imapSocket.getInputStream()));
            OutputStream outputStream = imapSocket.getOutputStream();
            String connectionResponse = reader.readLine();
            System.out.println(connectionResponse);

            sendIMAPCommand(LOGIN_COMMAND, outputStream);
            printIMAPResponse(reader);

            sendIMAPCommand(LIST_ALL_FOLDERS_COMMAND, outputStream);
            printIMAPResponse(reader);

            sendIMAPCommand(SELECT_FOLDER_COMMAND, outputStream);
            printIMAPResponse(reader);

            sendIMAPCommand(RETRIEVE_MESSAGE_1_COMMAND, outputStream);
            printIMAPResponse(reader);

            imapSocket.close();
            System.out.println("Program done");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void printIMAPResponse(BufferedReader reader) {
        String listAllResponse;
        try {
            while ((listAllResponse = reader.readLine()) != null) {
                System.out.println(listAllResponse);
                if (listAllResponse.contains("A1"))
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendIMAPCommand(String command, OutputStream outputStream) {
        try {
            outputStream.write(command.getBytes());
            outputStream.flush();
        } catch (Exception e) {

        }
    }

    private void readFromFile() {
        try {
            String filePath = "C:\\Users\\PC\\git_projects\\id1212-network-programming-patjo\\Lab3\\src\\main\\java\\id1212\\patjo\\lab3\\password.txt";
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            this.USERNAME = bufferedReader.readLine();
            this.PASSWORD = bufferedReader.readLine();
            System.out.println("Username:" + USERNAME);
            System.out.println("Password:" + "*************************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
