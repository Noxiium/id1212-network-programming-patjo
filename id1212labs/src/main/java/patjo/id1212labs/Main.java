package patjo.id1212labs;

/**
 *
 * @author PC
 */
public class Main {

    public static void main(String[] args) {
        try {
            ChatServer chatServer = new ChatServer();
            chatServer.listenForClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}