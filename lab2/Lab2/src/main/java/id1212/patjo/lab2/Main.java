package id1212.patjo.lab2;

/**
 *
 * @author patricialagerhult
 */
public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Starting https-server...");
            HttpServer httpServer = new HttpServer();
            httpServer.listenForClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
