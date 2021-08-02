import java.io.*;
import java.net.*;

public class FileThread extends Thread {

    private Socket clientSocket;

    public FileThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {

            // Create input stream of bytes from socket
            // Reads data from the client
            InputStream input = clientSocket.getInputStream();

            // InputStreamReader reads bytes and decodes them into characters
            // BufferedReader reads text from the character input stream
            BufferedReader buff = new BufferedReader(new InputStreamReader(input));

            // Create output stream of bytes from socket
            // Used to send data to the client
            OutputStream output = clientSocket.getOutputStream();

            // PrintWriter formats the bytes into text
            PrintWriter print = new PrintWriter(output, true);

            String text = "";

            // Loop until client exits
            do {
                text = buff.readLine();
                print.println("Server: " + text);
            } while (!text.equals("bye"));

            clientSocket.close();

        } catch (IOException ex) {
            System.out.println("Server exception");
            ex.printStackTrace();
        }
    }
}
