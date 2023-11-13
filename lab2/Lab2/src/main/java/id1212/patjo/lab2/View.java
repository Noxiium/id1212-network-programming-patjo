package id1212.patjo.lab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author patricialagerhult
 */
public class View {
    BufferedReader request;
    InputStreamReader inputStreamReader;
    PrintStream response;
    String folder = "C:\\Users\\PC\\git_projects\\id1212-network-programming-patjo\\lab2\\Lab2\\src\\main\\java\\id1212\\patjo\\lab2\\";

    public View(Socket socket) {

        try {
            this.inputStreamReader = new InputStreamReader(socket.getInputStream());
            this.request = new BufferedReader(inputStreamReader);
            initialReponse(socket);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initialReponse(Socket socket) {
        try {
            this.response = new PrintStream(socket.getOutputStream());
            response.println("HTTP/1.1 200 OK");
            response.println("Server: Patjo 0.1 Beta");
            response.println("Content-Type: text/html");
            response.println("Set-Cookie: clientId=1; expires=Wednesday,31-Dec-23 21:00:00 GMT");
            response.println();
            String requestedDocument = folder + "index.html";

            File f = new File("" + requestedDocument);
            FileInputStream infil = new FileInputStream(f);
            byte[] b = new byte[1024];
            while (infil.available() > 0) {
                response.write(b, 0, infil.read(b));
            }
            response.flush();
            socket.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateHTTPResponse(Boolean answer, int numberOfGuessesLeft) {
        return null;
    }
}
