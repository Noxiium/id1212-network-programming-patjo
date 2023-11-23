package id1212.patjo.lab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 * HttpServer Class listens for incoming client on port 1234.
 * For each connected client, a new ClientHandlerController thread is created to
 * handle communication.
 * 
 * @author patricialagerhult && johansellerfredlund
 */

public class HttpServer {
    SSLServerSocket serverSocket;
    int serverPort = 1234;

    /**
     * Creates a new instance of HttpServer
     */
    public HttpServer() throws Exception {

        SSLServerSocketFactory sslServerSocketFactory;
        try {

            // Initialize an input stream to read the
            // content of the key .pfx file
            InputStream inputStream = new FileInputStream(new File("C:\\Users\\PC\\keyPC.pfx"));

            // Initializes a KeyStore object and load it using
            // the input stream and the keystore password.
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            char[] keystorePassword = "patjo123".toCharArray();
            keyStore.load(inputStream, keystorePassword);

            // Gets a KMF instance using default algorithm
            // Initializes the KMF with loaded keystore and password
            KeyManagerFactory keyManagerFactory = KeyManagerFactory
                    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keystorePassword);

            // Creates SSLContext using TLS protocol.
            // Initializes the SSL context with the key managers
            // obtained from the key manager factory.
            // Creates an SSL server socket using the SSL server socket factory and binds it
            // to the specified serverPort.
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
            sslServerSocketFactory = sslContext.getServerSocketFactory();
            this.serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(serverPort);

        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    /**
     * Listens for incoming client connections on port 1234 and,
     * creates a new ClientHandlerController thread for each connected client.
     */
    public void listenForClient() throws Exception {

        while (true) {
            try {
                // System.out.println("Server listen on port 1234...");

                SSLSocket socket = (SSLSocket) serverSocket.accept();
                // System.out.println("New client request " + socket + " ");

                ClientHandlerController clientHandlercontr = new ClientHandlerController(socket);
                Thread clientHandlercontrThread = new Thread(clientHandlercontr);
                clientHandlercontrThread.start();

            } catch (Exception e) {
                serverSocket.close();
                e.printStackTrace();
            }

        }
    }
}
