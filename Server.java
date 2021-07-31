import java.io.*;
import java.net.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
public class Server {

    // Port that we'll be using
    static final int port = 1000;
    static final static String fileName;
    public static void main(String[] args) {

// setting up file handling
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;




        // Creates a socket and bounds it to the specified port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on port " + port);

            while (true) {
                // Waits for a connection to be made and accepts it
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Create new thread with the new socket
                // .start() executes this instance and runs its run method
                new ServerThread(socket).start();
            }

        } catch (IOException e) {
            System.out.println("Server exception");
        }
    }

}