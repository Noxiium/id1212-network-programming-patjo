package id1212.patjo.lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

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

    public SendMail() {
        try {
            this.socket = new Socket("smtp.kth.se", 587);
            this.outputStream = this.socket.getOutputStream();
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.outputStream, true);

            String response = reader.readLine();
            System.out.println("S: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean EHLO() throws IOException {

        writer.println("EHLO patlag@kth.se");
        writer.flush();
        System.out.println("C: EHLO patlag@kth.se");

        String response;

        while ((response = reader.readLine()) != null) {
            System.out.println("S: " + response);

            if (response.contains("250-STARTTLS")) {
                this.extendedSMTP = true;
            }
            if (response.contains("250 "))
                break;
        }

        return extendedSMTP;
    }

    // 220 Ready to start TLS
    // 501 Syntax error (no parameters allowed)
    // 454 TLS not available due to temporary reason
    public void startTLS() throws IOException {
        writer.println("STARTTLS");
        writer.flush();
        System.out.println("C: STARTTLS");

        System.out.println("S: " + reader.readLine());

        writer.println("AUTH LOGIN ");
        writer.flush();
        System.out.println("C: AUTH LOGIN");

        System.out.println("S: " + reader.readLine());
    }

}
