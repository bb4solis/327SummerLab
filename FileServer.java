import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileServer {

    private final static int PORT = 1000; // Port Name
    private static ServerSocket server;
    private static Socket client = null;

    public static void main(String[] args) throws IOException {
        HashMap<Integer, Socket> clients = new HashMap<Integer, Socket>();
        ArrayList<FileThread> threads = new ArrayList<FileThread>();
        // TODO: Change to a hash map
        // TODO: Broadcast changes to every client

        try {
            // Makes server
            server = new ServerSocket(PORT);
            System.out.println("Listening on port " + PORT);
            System.out.println(server);
        } catch (IOException e) {
            System.out.println("Port already in use." + e.getMessage());
            return;
        }

        while (true) {
            try {
                // Waits for a connection with a client
                client = server.accept();
                System.out.println("New Client : " + client);
                clients.put(Integer.valueOf(client.getPort()), client);
                System.out.println(clients);
                FileThread fileThread = new FileThread(client);
                Thread t = new Thread(fileThread);

                t.start();

                for (FileThread thread : threads) {
                    thread.updateHashMap(clients);
                }

            } catch (IOException e) {
                System.out.println("Server exception");
            }
        }
    }
}
