
package id1212.patjo.lab3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patricialagerhult
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        //GetMail GetMail = new GetMail();
        SendMail sendMail = new SendMail();
        try {
            boolean extendedSMTP = sendMail.EHLO();
            sendMail.startTLS();
            sendMail.authLogin();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
