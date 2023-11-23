package id1212.patjo.lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.util.Base64;

/**
 *SendMail Class for sending email using SMTP upgraded with TLS.
 * 
 * @author patricialagerhult && johansellerfredlund
 */
public class SendMail {

    private Socket socket;
    private OutputStream outputStream;
    private BufferedReader reader;
    private PrintWriter writer;
    private Boolean extendedSMTP;
    private Pattern pattern;
    private Matcher matcher;
    private String senderAddress;
    private String recipiantAddress;
    private String emailSubject;

    /**
     *Creates an instance of SendMail.
     */
    public SendMail() {
        try {
            this.socket = new Socket("smtp.kth.se", 587);
            this.outputStream = this.socket.getOutputStream();
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.outputStream, true);
            this.pattern = Pattern.compile("^250\\s");
            this.extendedSMTP = false;

            //Print connection information
            System.out.println("\nC: <Connects to smtp.kth.se on port 587>");
            String response = reader.readLine();
            System.out.println("\nS: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Establishes a connection to the SMTP server.
     */
    public void establishConnection() {
        try {
            EHLO();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
    *Sends user input in plain text to the server and displays the server's response.
    * 
    * @param input , the input from the user
    */
    public void sendUserInput(String input) throws IOException {
        System.out.println("\nC: " + input);
        writer.println(input);
        writer.flush();

        System.out.println("S: " + reader.readLine());
    }

    /**
    *Sends user input in Base64 encoding to the server and decode and displays the server's response.
    * 
    * @param input , the input from the user
    */
    public void sendEncodedUserInput(String input) throws IOException {
        String encodedInput = encodeString(input);
        
        System.out.println("\nC: " + encodedInput);
        writer.println(encodedInput);

        String response = reader.readLine();
        try {
            String decodedResponse = decodeResponse(response);
            System.out.println("S: " + decodedResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /**
     *Initiates the handshake with the SMTP server by sending command EHLO,
     * and print out the server's response.
     * If the server supports the STARTTLS extension, 
     * call the method startTLS() to upgrade to an encrypted connection. 
     */
    private void EHLO() throws IOException {
        System.out.println("\nC: EHLO webmail.kth.se");
        writer.println("EHLO webmail.kth.se");
        writer.flush();

        String response;

        while ((response = reader.readLine()) != null) {
            System.out.println("S: " + response);

            if (response.contains("250-STARTTLS")) {
                this.extendedSMTP = true;
            }

            matcher = pattern.matcher(response);

            if (this.extendedSMTP && matcher.find()) {
                startTLS();
                break;
            }

        }

    }

    /**
     *Send command STARTTLS to server to upgrade to a secure TLS connection.
     * Read the server's response and call method upgradeSocket()
     * to upgrades the socket if successful.
     */
    private void startTLS() throws IOException {
        System.out.println("\nC: STARTTLS");
        writer.println("STARTTLS");
        writer.flush();

        String response = reader.readLine();
        if (response.contains("220")) {
            System.out.println("S: " + response);
            upgradeSocket();
        }

    }

    /**
     *Upgrades the socket to a secure TLS connection.
     * If upgrading was successful, send command EHLO to server again.
     */
    private void upgradeSocket() throws IOException {

        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(this.socket,
                this.socket.getInetAddress().getHostAddress(), socket.getPort(), true);

        this.reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        this.writer = new PrintWriter(sslSocket.getOutputStream(), true);

        Boolean TLSisActive = sslSocket.getSession().isValid();
        System.out.println("\n<TLS active: " + TLSisActive + ">");

        if (TLSisActive) {

            System.out.println("\nC: EHLO webmail.kth.se");
            writer.println("EHLO webmail.kth.se");
            writer.flush();

            String response;

            while ((response = reader.readLine()) != null) {
                System.out.println("S: " + response);

                matcher = pattern.matcher(response);

                if (matcher.find()) {
                    authLogin();
                    break;
                }

            }

        }

    }

    /**
     *Initiates the authentication process by sending the AUTH LOGIN command to the server.
     * Decodes a Base64 encoded response from the server, and prints it.
     */
    private void authLogin() throws IOException {
        System.out.println("\nC: AUTH LOGIN");
        writer.println("AUTH LOGIN");
        writer.flush();

        String response = reader.readLine();
        String decodedResponse = decodeResponse(response);
        System.out.println("S: " + decodedResponse);
    }

    /**
     * Decodes a Base64 encoded response from the server.
     * 
     * @param response, The Base64 encoded response from the server.
     * @return The decoded string.
     */
    private String decodeResponse(String response) throws IllegalArgumentException {
        String[] responsSplit = response.split(" ");
        byte[] decodedBytes = Base64.getDecoder().decode(responsSplit[1]);
        String decodedStringResponse = new String(decodedBytes);
        return decodedStringResponse;

    }

    /**
     * Encodes a string using Base64 encoding.
     *
     * @param str The string to be encoded.
     * @return The encoded string.
     */
    private String encodeString(String str) {
        String encodedString = Base64.getEncoder().encodeToString(str.getBytes());
        return encodedString;
    }

    /**
     * Sets the sender's email address,
     * and sends the MAIL FROM command to the server.
     * 
     * @param address The sender's email address
     */
    public void setSenderAddress(String address) throws IOException {
        this.senderAddress = address;
        String command = "MAIL FROM:<" + address + ">";
        sendUserInput(command);

    }
    
     /**
     * Sets the recipient's email address,
     * and sends the RCPT TO command to the server.
     * 
     * @param address The sender's email address
     */
    public void setRecipientAddress(String address) throws IOException {
        this.recipiantAddress = address;
        String command = "RCPT TO:<" + address + ">";
        sendUserInput(command);

    }
    
    /**
      * Sets the subject of the email.
     * 
     * @param emailSubject The subject of the email.
     */
    public void setEmailSubject(String emailSubject){
        this.emailSubject = emailSubject;
    }
    
    /**
     * Sets the content of the outgoing email,
     * and sends it to the server.
     * 
     * @param data The content of the email.
     */
    public void setEmailContent(String data) throws IOException{
         // Construct the email message string
        String emailData = "From: " + this.senderAddress + "\r\n"
                + "To: " + this.recipiantAddress + "\r\n"
                + "Subject: " + this.emailSubject + "\r\n"
                + "Date: " + LocalDateTime.now() + "\r\n"
                + "\r\n" // Empty line separating headers from body
                + data + "\r\n."; // Message body and terminating period

        sendUserInput(emailData);

    }
}
