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

    public SendMail() {
        try {
            this.socket = new Socket("smtp.kth.se", 587);
            this.outputStream = this.socket.getOutputStream();
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.outputStream, true);
            this.pattern = Pattern.compile("^250\\s");
            this.extendedSMTP = false;

            System.out.println("\nC: <Connects to smtp.kth.se on port 587>");
            String response = reader.readLine();
            System.out.println("\nS: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void establishConnection() {
        try {
            EHLO();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void sendUserInput(String input) throws IOException {
        System.out.println("\nC: " + input);
        writer.println(input);
        writer.flush();

        System.out.println("S: " + reader.readLine());
    }

    public void sendEncodedUserInput(String input) throws IOException {
        String encodedInput = encodeString(input);
        System.out.println("\nC: " + encodedInput);
        writer.println(encodedInput);

        String response = reader.readLine();
        try {
            String decodedResponse = decodeResponse(response);
            System.out.println("S: " + decodedResponse);
        } catch (Exception e) {
            System.out.println("S: " + response);
        }

    }

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

    private void upgradeSocket() throws IOException {

        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(this.socket, this.socket.getInetAddress().getHostAddress(), socket.getPort(), true);

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

    private void authLogin() throws IOException {
        System.out.println("\nC: AUTH LOGIN");
        writer.println("AUTH LOGIN");
        writer.flush();

        String response = reader.readLine();
        String decodedResponse = decodeResponse(response);
        System.out.println("S: " + decodedResponse);
    }

    private String decodeResponse(String response) throws IllegalArgumentException {
        String[] responsSplit = response.split(" ");
        byte[] decodedBytes = Base64.getDecoder().decode(responsSplit[1]);
        String decodedStringResponse = new String(decodedBytes);
        return decodedStringResponse;

    }

    private String encodeString(String str) {
        String encodedString = Base64.getEncoder().encodeToString(str.getBytes());
        return encodedString;
    }

    public void setSenderAddress(String address) throws IOException {
        this.senderAddress = address;
        String command = "MAIL FROM:<" + address + ">";
        sendUserInput(command);

    }

    public void setRecipientAddress(String address) throws IOException {
        this.recipiantAddress = address;
        String command = "RCPT TO:<" + address + ">";
        sendUserInput(command);

    }
    
    public void setEmailSubject(String emailSubject){
        this.emailSubject = emailSubject;
    }
    
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
