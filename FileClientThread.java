import java.net.Socket;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.io.*;

public class FileClientThread implements Runnable {
    private Socket clientSocket;
    private String path;

    public FileClientThread(Socket client, String path) {
        this.clientSocket = client;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("RUNNING");
                int bytes;
                InputStream in = clientSocket.getInputStream();
                DataInputStream dataInput = new DataInputStream(in);
                String fileName = dataInput.readUTF();

                OutputStream os = new FileOutputStream(path + "\\" + fileName);
                long size = dataInput.readLong();
                byte[] buffer = new byte[1024];
                while (size > 0 && (bytes = dataInput.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                    os.write(buffer, 0, bytes);
                    size -= bytes;
                }
                // os.close();
                // in.close();
                System.out.println("File " + fileName + " received from Server.");

            }

        } catch (IOException ex) {
            System.out.println("ERROR");
        }
    }

}
