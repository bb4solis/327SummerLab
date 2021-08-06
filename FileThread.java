import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileThread implements Runnable {

    private Socket clientSocket;
    private BufferedReader buff;
    HashMap<Thread, Socket> clients;
    private static final String path = System.getProperty("user.dir") + "\\SharedFolder\\";
    
    public FileThread(Socket client) {
    	try {
    		File folder = new File(path);
    		if (!folder.exists()) {
    			folder.mkdirs();
    		}
    	}catch (Exception e) {
            System.out.println("Shared Folder Check Failed, " + e.getMessage());
        }
        this.clientSocket = client;
    }

    @Override
    public void run() {
        try {
            // BufferedReader reads text from the InputStreamReader from character input
            // stream
            buff = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String choice = buff.readLine();
            while (choice != null) {
                switch (choice) {
                    case "1" -> receiveFile();
                    case "2" -> {
                        String fileName;
                        while ((fileName = buff.readLine()) != null) {
                            sendFile(fileName);
                        }
                    }
                    case "4" -> {
                        System.exit(1);
                    }
                    default -> System.out.println("Enter number 1-4 to continue");
                }
                buff.close();
                break;
            }

        } catch (IOException ex) {
            System.err.println("Client Disconnected" + ex.getMessage());
        }
    }

    public void sendFile(String name) {
        try {
            File f = new File(path, name);
            byte[] byteArray = new byte[(int) f.length()];
            FileInputStream fileInput = new FileInputStream(f);
            BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
            DataInputStream dataInput = new DataInputStream(bufferedInput);
            dataInput.readFully(byteArray, 0, byteArray.length);

            OutputStream os = clientSocket.getOutputStream();
            // Sending information of the file name and size to the server
            DataOutputStream buffOutput = new DataOutputStream(os);
            buffOutput.writeUTF(f.getName());
            buffOutput.writeLong(byteArray.length);
            buffOutput.write(byteArray, 0, byteArray.length);
            buffOutput.flush();
            System.out.println("File " + name + " sent to Client\n");

            System.out.println("Do you wish to send another file to the server? \n1.Yes \n2.No");
            String answer = buff.readLine().toLowerCase(Locale.ROOT);
            if (answer.equals("yes") || (Integer.parseInt(answer) == 1))
                sendFile(name);
            else
                System.exit(1);
        } catch (Exception e) {
            System.out.println("Failed sending file, specified file does not exist in the directory");
        }
    }

    public void receiveFile() {
        try {
            int bytes;
            DataInputStream dataInput = new DataInputStream(clientSocket.getInputStream());
            String fileName = dataInput.readUTF();
            OutputStream output = new FileOutputStream(path + fileName);

            long size = dataInput.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytes = dataInput.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytes);
                size -= bytes;
            }
            output.close();
            dataInput.close();

            System.out.println("File " + fileName + " received from client");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void syncFile() {
    	
    }

    public void updateHashMap(HashMap<Thread, Socket> newClients) {
        clients = newClients;
        System.out.println(clients);
    }
}
