import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;

public class FileServer {

    private final static int PORT = 1000; // Port Name
    private static ServerSocket server;
    private static Socket client = null;

    public static void main(String[] args) throws IOException {

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

                Thread t = new Thread(new FileThread(client));

                t.start();

            } catch (IOException e) {
                System.out.println("Server exception");
            }
        }
    }
}
