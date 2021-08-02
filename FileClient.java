
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;

public class FileClient {

    public final static int PORT = 1000; // you may change this
    public final static String SERVER = "127.0.0.1"; // localhost
    public final static String FILE_TO_RECEIVED = "./test2.txt"; // you may change this, I give a
                                                                 // different name because i don't
                                                                 // want to
                                                                 // overwrite the one used by
                                                                 // server...

    public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                                 // should bigger than the file to be downloaded

    public static void main(String[] args) throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Scanner in = new Scanner(System.in);
        Socket socket = null;
        // Create client socket
        // Socket cS = new Socket("localhost", 999);
        // to send data to the server
        // DataOutputStream dos = new DataOutputStream(cS.getOutputStream());

        try {
            socket = new Socket(SERVER, PORT);
            System.out.println("Connected");

            // receive file
            byte[] mybytearray = new byte[FILE_SIZE];
            // byte[] mybytearray = new byte[(int) myFile.length()];

            // Get socket's output stream
            // Used to send data to server
            OutputStream output = socket.getOutputStream();

            // Create a new PrintWriter from OutputStream
            // Used to write formatted data to the OutputStream
            // OutputStream writes them as a byte
            // PrintWriter formats them to a text format
            PrintWriter print = new PrintWriter(output, true);

            // Reads data from server via client socket
            InputStream input = socket.getInputStream();

            // InputStreamReader reads bytes and decodes them into characters
            // BufferedReader reads the characters as a string
            BufferedReader buff = new BufferedReader(new InputStreamReader(input));

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
            }

            socket.close();

        } catch (IOException e) {
            System.out.println("I/O error");
        }

        // // Get input from server
        // InputStream is = socket.getInputStream();

        // fos = new FileOutputStream(FILE_TO_RECEIVED);
        // bos = new BufferedOutputStream(fos);
        // bytesRead = is.read(mybytearray, 0, mybytearray.length);
        // current = bytesRead;

        // do {
        // bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
        // if (bytesRead >= 0)
        // current += bytesRead;
        // } while (bytesRead > -1);

        // bos.write(mybytearray, 0, current);
        // bos.flush();

        // System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + "
        // bytes read)");

        // /*
        // * System.out.println("Would you like to send file back?"); int ans =
        // * in.nextInt(); if (ans != 0) {
        // *
        // * }
        // */

        // } finally {
        // if (fos != null)
        // fos.close();
        // if (bos != null)
        // bos.close();
        // if (sock != null)
        // sock.close();
        // }
    }

}