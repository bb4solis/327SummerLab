import java.io.*;
import java.net.*;

public class FileThread extends Thread {

    private Socket socket;

    public FileThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {

            // Create input stream of bytes from socket
            // Reads data from the socket or client
            InputStream input = socket.getInputStream();

            // InputStreamReader reads bytes and decodes them into characters
            // BufferedReader reads text from the character input stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // Create output stream of bytes from socket
            // Used to send data to the client
            OutputStream output = socket.getOutputStream();

            // PrintWriter formats the bytes into text
            PrintWriter writer = new PrintWriter(output, true);

            String text = "";

            // Loop until client exits
            while (!text.equals("bye")) {

                // Reads client inputs and outputs a reply
                text = reader.readLine();
                writer.println("Server: " + text);
            }

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
