package serverClasses;

import utilities.MySQLConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

public class Main {
    public static Connection connection = MySQLConnection.connect();

    public static void main(String[] args) {
        ServerSocket serverSocket;  // Initializing server socket
        Socket socket;

        try {
            serverSocket = new ServerSocket(50009);
            System.out.println("SERVER STARTED...\nWAITING FOR CLIENTS...");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("CLIENT CONNECTED");
                Thread t = new Thread(new HandleClientRequest(socket));
                t.start();  //starting new thread to handle client request
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
