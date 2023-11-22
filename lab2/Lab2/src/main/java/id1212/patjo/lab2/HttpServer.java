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

            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

            
            char[] keystorePassword = "patjo123".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream inputStream = null;
            inputStream = new FileInputStream(new File("C:\\Users\\Indiana Johan\\lab3key.pfx"));
            keyStore.load(inputStream, keystorePassword);

            keyManagerFactory.init(keyStore, keystorePassword);
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            sslServerSocketFactory = sslContext.getServerSocketFactory();

            for(int i = 0; i < sslServerSocketFactory.getSupportedCipherSuites().length; i++)
                System.out.println(sslServerSocketFactory.getSupportedCipherSuites()[i]);
            this.serverSocket = (SSLServerSocket)sslServerSocketFactory.createServerSocket(serverPort);
            //String[] cipher = {"SSL_DH_anon_WITH_RC4_128_MD5"};
            String[] cipher = {"TLS_RSA_WITH_AES_128_CBC_SHA"};
            //ss.setEnabledCipherSuites(cipher);
            System.out.println("Choosen:");
            for(int i = 0; i < serverSocket.getEnabledCipherSuites().length; i++)
                System.out.println(serverSocket.getEnabledCipherSuites()[i]);

            //SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            //serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(serverPort);

            
        } catch (Exception e) {
            serverSocket.close();
            e.printStackTrace();
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

                SSLSocket socket = (SSLSocket)serverSocket.accept();
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
