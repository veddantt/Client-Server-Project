import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

class AddressBookRecord {
    int recordID;
    String firstName;
    String lastName;
    String phoneNumber;

    public AddressBookRecord(int recordID, String firstName, String lastName, String phoneNumber) {
        this.recordID = recordID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return recordID + " " + firstName + " " + lastName + " " + phoneNumber;
    }
}

public class server {
    public static final int SERVER_PORT = 4652; // Use your UM ID's last 4 digits
    private static List<AddressBookRecord> addressBook = new ArrayList<>(); 
    private static int nextRecordID = 1001;
    private static final int MAX_RECORDS = 20;
    private static final String DATA_FILE = "address_book.txt";

    public static void main(String[] args) {
        ServerSocket myService = null;
        Socket serviceSocket = null;
        BufferedReader is;
        PrintStream os;

        // Try to open a server socket
        try {
            myService = new ServerSocket(SERVER_PORT);
            System.out.println("Server started on port " + SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Could not start server: " + e);
            System.exit(1);
        }

        // Listen for incoming client connections
        while (true) {
            try {
                serviceSocket = myService.accept(); // Wait for a connection
                is = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
                os = new PrintStream(serviceSocket.getOutputStream());

                String line;
                while ((line = is.readLine()) != null) {
                    System.out.println("Received: " + line);
                    String response = handleCommand(line);
                    os.println(response);
                }

                // Close input and output streams and socket
                is.close();
                os.close();
                serviceSocket.close();
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    private static String handleCommand(String command) {
        String[] tokens = command.split(" ");
        String action = tokens[0].toUpperCase();

        switch (action) {
            case "ADD":
                return handleAdd(tokens);
            case "DELETE":
                return handleDelete(tokens);
            case "LIST":
                return handleList();
            case "SHUTDOWN":
                return handleShutdown();
            case "QUIT":
                return "200 OK";
            default:
                return "300 Invalid command";
        }
    }

    private static String handleAdd(String[] tokens) {
        if (tokens.length != 4) {
            return "301 Message format error";
        }
        String firstName = tokens[1];
        String lastName = tokens[2];
        String phoneNumber = tokens[3];

        if (firstName.length() > 8 || lastName.length() > 8 || phoneNumber.length() != 12) {
            return "301 Message format error";
        }

        AddressBookRecord record = new AddressBookRecord(nextRecordID++, firstName, lastName, phoneNumber);
        addressBook.add(record);
        return "200 OK\nThe new Record ID is " + record.recordID;
    }

    private static String handleDelete(String[] tokens) {
        if (tokens.length != 2) {
            return "301 Message format error";
        }

        try {
            int recordID = Integer.parseInt(tokens[1]);
            for (AddressBookRecord record : addressBook) {
                if (record.recordID == recordID) {
                    addressBook.remove(record);
                    return "200 OK";
                }
            }
            return "403 The Record ID does not exist";
        } catch (NumberFormatException e) {
            return "301 Message format error";
        }
    }

    private static String handleList() {
        if (addressBook.isEmpty()) {
            return "200 OK\nThe address book is empty.";
        }

        StringBuilder response = new StringBuilder("200 OK\nThe list of records in the book:\n");
        for (AddressBookRecord record : addressBook) {
            response.append(record).append("\n");
        }
        return response.toString();
    }

    private static String handleShutdown() {
        System.out.println("Server is shutting down...");
        System.exit(0);
        return "200 OK"; // This line will never be reached
    }
}