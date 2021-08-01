package SampleServerCode;

import java.net.*;
import java.io.*;

public class Client {

    // Port that we'll be using
    static final int port = 1000;
    static final String hostname = "localhost";

    public static void main(String[] args) {

        // Creates a socket and connects it to the specified port
        try (Socket socket = new Socket(hostname, port)) {

            // Get socket's output stream
            // Used to send data to server
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

                // Reads data from server via client socket
                InputStream input = socket.getInputStream();

                // InputStreamReader reads bytes and decodes them into characters
                // BufferedReader reads the characters as a string
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                // Gets message from server and prints out a reply
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
    
        public void basicUI(){
        int choice;
        Scanner scan = new Scanner(System.in);
        do{
            choice = scan.nextInt();
            System.out.println("1. Send File \n2.Receive File \n3.Store File \n4.Sync File \n5.Delete File \n6.Exit");
            switch (choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Failed, try using numPad #1-6");
            }
        }while(choice !=6);
    }

}
