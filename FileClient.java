
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileClient {

    public final static int PORT = 1000;
    public final static String SERVER = "127.0.0.1";

    // Files
    private static String name;
    private static Socket clientSocket;
    private static BufferedReader buff ;
    private static PrintStream file;
    //change the path to whatever path you have
    private static final String path = "E:\\CECS\\327SummerLab\\Clients\\Client1\\";


    public static void main(String[] args) throws IOException {

        try {
            clientSocket = new Socket(SERVER, PORT);
            System.out.println("Connected");

            // InputStreamReader reads bytes and decodes them into characters
            // BufferedReader reads the characters as a string
            buff  = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("Connect Failed, " + e.getMessage());
        }


        // Used to send data to server
        file = new PrintStream(clientSocket.getOutputStream());

        try {
            System.out.println("1.Send File \n2.Receive File \n3.Exit");
            String choice;
            choice = buff.readLine();
            switch (Integer.parseInt(choice)) {
                case 1:
                    file.println("1");
                    sendFile();
                    break;
                case 2 :
                    file.println("2");
                    System.err.print("Enter file name: ");
                    name = buff.readLine();
                    file.println(name);
                    receiveFile(name);
                    break;
                case 3 :System.exit(1);
                    break;
                default :System.err.println("Enter number 1-3 to continue");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void sendFile() {
        try {
            // Get client inputs from buff and prints out a reply
            System.err.println("Enter name of file: ");
            name = buff.readLine();
            System.out.println("Sending " + name + " to server now...");

            File f1 = new File(path);
            boolean bool = f1.mkdir();
            File f = new File(path,name);

            byte[] byteArray = new byte[(int) f.length()];
            FileInputStream fileInput = new FileInputStream(f);
            BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
            DataInputStream dataInput = new DataInputStream(bufferedInput);
            dataInput.readFully(byteArray, 0, byteArray.length);
            OutputStream os = clientSocket.getOutputStream();
            //Sending information of the file name and size to the server
            DataOutputStream buffOutput = new DataOutputStream(os);
            buffOutput.writeUTF(f.getName());
            buffOutput.writeLong(byteArray.length);
            buffOutput.write(byteArray, 0, byteArray.length);
            buffOutput.flush();
            System.out.println("File " + name + " sent\n");

                /*System.out.println("Do you wish to send another file to the server? \n1.Yes \n2.No");
                String answer = buff.readLine().toLowerCase(Locale.ROOT);
                if(answer.equals("yes") || (Integer.parseInt(answer) == 1)){
                    sendFile();
                }
                else System.exit(1);*/


        } catch (Exception e) {
            System.out.println("Failed sending file, specified file does not exist in the directory");
        }
    }

    public static void receiveFile(String fileName) {
        try {
            int bytes;
            InputStream in = clientSocket.getInputStream();
            DataInputStream dataInput = new DataInputStream(in);
            fileName = dataInput.readUTF();
            OutputStream os = new FileOutputStream(path+fileName);
            long size = dataInput.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytes = dataInput.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                os.write(buffer, 0, bytes);
                size -= bytes;
            }
            os.close();
            in.close();
            System.out.println("File "+fileName+" received from Server.");

        } catch (IOException ex) {
            Logger.getLogger(FileThread.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    private static void storeFile() {

    }

}


