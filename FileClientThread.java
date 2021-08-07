import java.net.Socket;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.io.*;

public class FileClientThread implements Runnable {
    private Socket clientSocket;

    public FileClientThread(Socket client) {
        this.clientSocket = client;
    }

    @Override
    public void run() {

        try {

            // Reads user path from server
            InputStream in = clientSocket.getInputStream();
            DataInputStream dataInput = new DataInputStream(in);
            String path = dataInput.readUTF();

            while (true) {
                int bytes;

                // Reads file name from server
                String fileName = dataInput.readUTF();

                // Reads file and save in corresponding directory
                OutputStream os = new FileOutputStream(path + "\\" + fileName);
                long size = dataInput.readLong();
                byte[] buffer = new byte[1024];
                while (size > 0 && (bytes = dataInput.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                    os.write(buffer, 0, bytes);
                    size -= bytes;
                }

                System.out.println("File " + fileName + " received from Server.");
            }

        } catch (IOException ex) {
            System.out.println("");
        }
    }

}
