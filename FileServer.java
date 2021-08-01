import java.io.*;
import java.util.Scanner;
import java.net.*;

public class FileServer {

    public final static int SOCKET_PORT = 13267; // you may change this
    // TODO: Make an array list for storing clients, broadcast new or updated files
    // to all clients

    public static void main(String[] args) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;
        String filepath; // TODO: Ask clients for file name
        Scanner in = new Scanner(System.in);

        // Ask user to input desired file Scanner temp = new Scanner(System.in);
        System.out.print("Please enter filepath of file you would like to send: "); // TODO: Move to FileThread

        filepath = in.nextLine();

        try {

            // Makes server
            servsock = new ServerSocket(SOCKET_PORT);
            System.out.println("Waiting...");

            try {

                // Waits for a connection with a client
                sock = servsock.accept();
                System.out.println("Accepted connection : " + sock);

                // send file
                File myFile = new File(filepath);
                byte[] mybytearray = new byte[(int) myFile.length()];

                // A FileInputStream obtains input bytes from a file in a file system
                fis = new FileInputStream(myFile);

                // Used to buffer the input and to support the mark and reset methods
                bis = new BufferedInputStream(fis);

                // Reads bytes from this byte-input stream into the specified byte array,
                // starting at the given offset
                bis.read(mybytearray, 0, mybytearray.length);

                // Get socket output to send data to client
                os = sock.getOutputStream();
                System.out.println("Sending " + filepath + "(" + mybytearray.length + " bytes)");

                // Writes b.length bytes from the specified byte array to this output stream.
                os.write(mybytearray, 0, mybytearray.length);

                // Flushes this output stream and forces any buffered output bytes to be written
                // out.
                os.flush();

                System.out.println("Done.");
            } finally {
                if (bis != null)
                    bis.close();
                if (os != null)
                    os.close();
                if (sock != null)
                    sock.close();
            }

        } finally {
            if (servsock != null)
                servsock.close();
        }
    }
}
