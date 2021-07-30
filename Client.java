import java.net.*;
import java.io.*;

public class Client {

    static final int port = 1000;
    static final String hostname = "localhost";

    public static void main(String[] args) {

        // Creates a socket and connects it to the specified port
        try (Socket socket = new Socket(hostname, port)) {

            // Get socket's output stream
            // Used to send primitive Java data types
            OutputStream output = socket.getOutputStream();

            // Create a new PrintWriter from OutputStream
            // Used to write formatted data to the OutputStream
            // OutputStream writes them as a byte
            // PrintWriter formats them to a text format
            PrintWriter writer = new PrintWriter(output, true);

            // Console is used to read from and write to the console
            Console console = System.console();
            String text = "";

            // Loops until client exits
            while (!text.equals("bye")) {

                // Get client inputs from console
                text = console.readLine("Enter text: ");

                // Send message to server
                writer.println(text);

                // ! No idea what these things do

                // Get socket's input stream
                // Reads data from client via client socket
                InputStream input = socket.getInputStream();

                // InputStreamReader reads bytes and decodes them into characters
                // BufferedReader reads text from the character input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String time = reader.readLine();
                System.out.println(time);
            }

            socket.close();

        } catch (UnknownHostException e) {
            System.out.println("Server not found");
        } catch (IOException e) {
            System.out.println("I/O error");
        }
    }

}
