import java.io.*;
import java.net.*;

public class Server {

    static final int port = 1000;

    public static void main(String[] args) {

        // Creates a socket and bounds it to the specified port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                // waits for a connection to be made and accepts it
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // .start() executes this instance and runs its run method
                new ServerThread(socket).start();
            }

        } catch (IOException e) {
            System.out.println("Server exception");
        }
    }

}