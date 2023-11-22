package id1212.patjo.lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

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
            this.extendedSMTP = false;

            System.out.println("C: <Connects to smtp.kth.se on port 587>");
            String response = reader.readLine();
            System.out.println("S: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void EHLO() throws IOException {
        System.out.println("C: EHLO patlag@kth.se");
        writer.println("EHLO patlag@kth.se");
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

    public void startTLS() throws IOException {
        System.out.println("C: STARTTLS");
        writer.println("STARTTLS");
        writer.flush();

        String response = reader.readLine();
        if (response.contains("220")) {
            System.out.println("S: " + response);
            upgradeSocket();
        }

    }

    public void upgradeSocket() throws IOException {

        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(this.socket, this.socket.getInetAddress().getHostAddress(), socket.getPort(), true);

        this.reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        this.writer = new PrintWriter(sslSocket.getOutputStream(), true);

        Boolean TLSisActive = sslSocket.getSession().isValid();
        System.out.println("<TLS active: " + TLSisActive + ">");

        if (TLSisActive) {

            System.out.println("C: EHLO patlag@kth.se");
            writer.println("EHLO patlag@kth.se");
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

    public void authLogin() throws IOException {
        System.out.println("C: AUTH LOGIN");
        writer.println("AUTH LOGIN");
        writer.flush();

        String response;

        response = reader.readLine();
        System.out.println("S: " + response);


    }

}
