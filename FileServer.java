import java.io.*;
import java.net.*;
import java.util.HashMap;

public class FileServer {

    private final static int PORT = 1000; // Port Name
    private static ServerSocket server;
    private static Socket client = null;
    private static final String path = System.getProperty("user.dir");

    public static void main(String[] args) throws IOException {
        HashMap<Thread, Socket> clients = new HashMap<Thread, Socket>();
        HashMap<Socket, FileThread> fileThreads = new HashMap<Socket, FileThread>();
        HashMap<Socket, String> paths = new HashMap<Socket, String>();

        // TODO: Broadcast changes to every client

        try {
            // Makes server
            server = new ServerSocket(PORT);
            System.out.println("Listening on port " + PORT);

        } catch (IOException e) {
            System.out.println("Port already in use." + e.getMessage());
            return;
        }

        int counter = 0;

        while (true) {
            try {
                // Waits for a connection with a client
                client = server.accept();
                System.out.println("New Client : " + client);
                counter += 1;

                FileThread fileThread = new FileThread(client);
                Thread t = new Thread(fileThread);
                t.start();

                String pathName = path + "\\Clients\\Client" + String.valueOf(counter);
                new File(pathName).mkdirs();

                clients.put(t, client);
                fileThreads.put(client, fileThread);
                paths.put(client, pathName);

                // Removes disconnected clients
                for (Thread thread : clients.keySet()) {
                    try {
                        if (!thread.isAlive()) {
                            clients.remove(thread);
                            fileThreads.remove(clients.get(thread));
                            paths.remove(clients.get(thread));
                        }
                    } catch (Error e) {
                        clients.remove(thread);
                        fileThreads.remove(clients.get(thread));
                        paths.remove(clients.get(thread));
                    }
                }

                System.out.println(clients.size());

                // Update clients
                for (Socket socket : fileThreads.keySet()) {
                    fileThreads.get(socket).updateHashMap(paths);
                }

            } catch (IOException e) {
                System.out.println("Server exception");
            }
        }
    }
}
