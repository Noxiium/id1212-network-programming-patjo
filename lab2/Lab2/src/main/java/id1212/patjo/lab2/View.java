package id1212.patjo.lab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

    public View(Socket socket) {
        request = new BufferedReader(inputStreamReader(socket.getInputStream()));
        response = new PrintStream(socket.getOutputStream());
        String str = request.readLine();
        while ((str = request.readLine()) != null && str.length() > 0) {
            System.out.println(str);
        }
    }
}
