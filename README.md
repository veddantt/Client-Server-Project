Address Book Application

This project is a simple client-server address book application implemented in Java. It allows users to manage an address book remotely through a command-line interface.

Authors

- Vedant Patel


Overview

The application consists of two main components:

Server (server.java): Manages the address book and handles client requests.
Client (client.java): Connects to the server and allows users to send commands to manage the address book.


Features

Add Records: Create new entries in the address book.
Delete Records: Remove entries by record ID.
List Records: View all records in the address book.
Shutdown Server: Shut down the server (available to clients).
Quit Client: Disconnect the client from the server.

Server (server.java)

The server listens on port 4652 and handles incoming client connections. It supports the following commands:

ADD <firstName> <lastName> <phoneNumber>: Adds a new record with the provided first name, last name, and phone number.
DELETE <recordID>: Deletes the record with the specified ID.
LIST: Displays all records currently in the address book.
SHUTDOWN: Shuts down the server. This command is issued by the client.
QUIT: Disconnects the client from the server.

Client (client.java)

The client connects to the server and allows users to send commands through a command-line interface.

How to Run

Compile the Java files

javac server.java
javac client.java

Start the Server

java server

Start the Client


java client <server_host>

Replace `<server_host>` with the hostname or IP address of the server.
In this project replace <server_host> with localhost.

Usage

Once connected, enter commands at the client prompt. The server will respond with the results of each command.

Example session:

Enter command: ADD Jinhua Guo 313-583-6439
Server: 200 OK
The new Record ID is 1001

Enter command: LIST
Server: 200 OK
The list of records in the book:
1001 Jinhua Guo 313-583-6439

Enter command: QUIT
Server: 200 OK

Responsibilities
Vedant Patel: Implemented the ADD, LIST, and QUIT functions on the server side , also implemented the DELETE and SHUTDOWN functions, along with the client-side communication handling
