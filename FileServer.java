import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileServer {

    private final static int PORT = 1000; // Port Name
    private static ServerSocket server;
    private static Socket client = null;

    public static void main(String[] args) throws IOException {
        HashMap<Thread, Socket> clients = new HashMap<Thread, Socket>();
        HashMap<Socket, FileThread> fileThreads = new HashMap<Socket, FileThread>();

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

                FileThread fileThread = new FileThread(client);
                Thread t = new Thread(fileThread);
                t.start();

                clients.put(t, client);

                // Removes disconnected clients
                for (Thread thread : clients.keySet()) {
                    if (!thread.isAlive()) {
                        clients.remove(thread);
                        fileThreads.remove(clients.get(thread));
                    }
                }

                System.out.println(clients.size());

                // Update clients
                for (Socket socket : fileThreads.keySet()) {
                    fileThreads.get(socket).updateHashMap(clients);
                }

            } catch (IOException e) {
                System.out.println("Server exception");
            }
        }
    }
}
