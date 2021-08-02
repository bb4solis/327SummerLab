import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;

public class FileServer {

    private final static int PORT = 1000; // Port Name

    public static void main(String[] args) throws IOException {
        BufferedInputStream buff;
        OutputStream output;
        FileInputStream fileInput;
        ServerSocket server;
        Socket client;
        ArrayList<ServerSocket> clients = new ArrayList<ServerSocket>();

        // TODO: Change to a hash map
        // TODO: Broadcast changes to every client

        try {

            // Makes server
            server = new ServerSocket(PORT);
            System.out.println("Listening on port " + PORT);

            System.out.println(server);

            while (true) {
                // Waits for a connection with a client
                client = server.accept();
                System.out.println("New Client : " + client);

                System.out.println(client);
                clients.add(server);

                // Create new thread with the new client
                // .start() executes this instance and runs its run method
                new FileThread(client).start();

            }

        } catch (IOException e) {
            System.out.println("Server exception");
        }
    }
}
