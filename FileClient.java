
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.io.*;

public class FileClient {

    public final static int PORT = 1000;
    public final static String SERVER = "127.0.0.1";
    public final static String FILE_TO_RECEIVED = "./test2.txt";
    public final static int FILE_SIZE = 6022386;

    public static void main(String[] args) throws IOException {
        // Files
        FileOutputStream file = null;
        BufferedOutputStream buffOutput = null;
        Socket clientSocket = null;
        byte[] byteArray = new byte[FILE_SIZE];
        int bytes;
        int total;

        try {
            clientSocket = new Socket(SERVER, PORT);
            System.out.println("Connected");

            // Get socket's output stream
            // Used to send data to server
            OutputStream output = clientSocket.getOutputStream();

            // Create a new PrintWriter from OutputStream
            // Used to write formatted data to the OutputStream
            // OutputStream writes them as a byte
            // PrintWriter formats them to a text format
            PrintWriter print = new PrintWriter(output, true);

            // Reads data from server via client socket
            InputStream inputStream = clientSocket.getInputStream();

            // InputStreamReader reads bytes and decodes them into characters
            // BufferedReader reads the characters as a string
            BufferedReader buff = new BufferedReader(new InputStreamReader(inputStream));

            // Console is used to read from and write to the console
            Console console = System.console();

            String reply = "";
            String text = "";

            // Loops until client exits
            while (!text.equals("bye")) {

                // Get client inputs from console
                text = console.readLine("Enter path name for file: ");

                // Send message to server
                print.println(text);

                // Gets message from server and prints out a reply
                reply = buff.readLine();
                System.out.println(reply);

                file = new FileOutputStream(FILE_TO_RECEIVED);
                buffOutput = new BufferedOutputStream(file);
                bytes = inputStream.read(byteArray, 0, byteArray.length);
                total = bytes;

                do {
                    bytes = inputStream.read(byteArray, total, (byteArray.length - total));
                    if (bytes >= 0)
                        total += bytes;
                } while (bytes > -1);

                buffOutput.write(byteArray, 0, total);
                buffOutput.flush();

                System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + total + "bytes read)");
            }

            clientSocket.close();

        } catch (IOException e) {
            System.out.println("I/O error");
        }
    }

}