
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
        //GetMail GetMail = new GetMail();
        SendMail sendMail = new SendMail();
        
        try {
            sendMail.EHLO();
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
