import java.io.*;
import java.net.*;

public class client {
    public static final int SERVER_PORT = 4652; // Use the same port as the server

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Client <Server IP Address>");
            System.exit(1);
        }

        String serverAddress = args[0];

        try (Socket socket = new Socket(serverAddress, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server. Type commands (ADD, DELETE, LIST, SHUTDOWN, QUIT):");

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                String serverResponse = in.readLine();
                System.out.println("Server response: " + serverResponse);

                if (userInput.equals("QUIT") || userInput.equals("SHUTDOWN")) {
                    break;
                }

                // Handle multi-line responses (e.g., for LIST command)
                while (in.ready()) {
                    System.out.println(in.readLine());
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverAddress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverAddress);
            System.exit(1);
        }
    }
}