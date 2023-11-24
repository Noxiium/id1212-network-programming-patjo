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

/**
 * GetMail Class fetches a selected email from an IMAP server using SSL/TLS.
 *
 */
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

    /*
     * Creates an instance of GetMail and establishes a connection to the IMAP
     * server
     * using SSL/TLS.
     */
    public GetMail() {

        // Get the username and password from file and store them in private variables.
        setUserNameAndPasswordFromFile();
        LOGIN_COMMAND = "A1 LOGIN " + USERNAME + " " + PASSWORD + "\r\n";

        try {

            this.sslSocket = (SSLSocketFactory) SSLSocketFactory.getDefault();
            this.imapSocket = (SSLSocket) sslSocket.createSocket("webmail.kth.se", 993);
            this.reader = new BufferedReader(new InputStreamReader(imapSocket.getInputStream()));
            this.outputStream = imapSocket.getOutputStream();

            // Print connection response from the server.
            String connectionResponse = reader.readLine();
            System.out.println("S: " + connectionResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Fetches the specified mail from the IMAP server. Sends command for Login,
     * List all folders, Select the inbox folder, and fetches the content of a
     * specified email in the inbox.
     */
    public void fetchMail() throws IOException {
        sendIMAPCommand(LOGIN_COMMAND);
        printIMAPResponse();

        sendIMAPCommand(LIST_ALL_FOLDERS_COMMAND);
        printIMAPResponse();

        sendIMAPCommand(SELECT_FOLDER_COMMAND);
        printIMAPResponse();

        fetchMailPrintOnlyText(RETRIEVE_MESSAGE_1_COMMAND);

        // Close the socket
        imapSocket.close();
        System.out.println("< Program done >");
    }

    /**
     * Prints the server's response to the console.
     */
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

    /**
     * Sends an IMAP command to the server.
     *
     * @param command, the IMAP command to send.
     */
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

    /**
     * Reads the username and password from a separate file.
     */
    private void setUserNameAndPasswordFromFile() {
        try {
            // String filePath =
            // "/Users/patricialagerhult/NetBeansProjects/networkprog/id1212-network-programming-patjo/Lab3/src/main/java/id1212/patjo/lab3/password.txt";
            String filePath = "C:\\Users\\PC\\git_projects\\id1212-network-programming-patjo\\Lab3\\src\\main\\java\\id1212\\patjo\\lab3\\password.txt";
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Store username & password in private variables.
            this.USERNAME = bufferedReader.readLine();
            this.PASSWORD = bufferedReader.readLine();

            // Prints to the console.
            System.out.println("Username:" + USERNAME);
            System.out.println("Password:" + "*************************");

            // Close the BufferedReader.
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches and prints the text content of the specified email.
     * Sends the FETCH command to the server to retrieve the email content,
     * and then extracts the text from the email.
     * 
     * @param command, the FETCH command to retrieve the email.
     */
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

        // Use pattern to extract text content within <p> tags from email.
        Pattern pattern = Pattern.compile("<p>(.*?)<br>");
        Matcher matcher = pattern.matcher(emailContent);

        // Prints email to the console.
        while (matcher.find()) {
            String message = matcher.group(1); // Extract content within <p> tags
            System.out.println("S: Mail contains: " + message);
        }
    }
}
