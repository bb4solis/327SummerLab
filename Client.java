import java.net.*;
import java.io.*;

public class Client {

    static final int port = 1000;
    static final String hostname = "localhost";

    public static void main(String[] args) {

        try (Socket socket = new Socket(hostname, port)) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            Console console = System.console();
            String text = "";

            while (!text.equals("bye")) {
                text = console.readLine("Enter text: ");
                writer.println(text);

                InputStream input = socket.getInputStream();
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
