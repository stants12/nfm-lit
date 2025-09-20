package nfm.lit.server;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestServer {

    public static int serverport = 6900;
    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // Create a server socket on the specified port
            serverSocket = new ServerSocket(serverport);
            System.out.println("Server is listening on port " + serverport);

            while (true) {
                // Wait for a connection from a client
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                logConnection(clientSocket);
                displayConnectedClients();

                // Handle the client in a separate thread
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void logConnection(Socket clientSocket) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("[" + timeStamp + "] Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
    }

    private static void displayConnectedClients() {
        System.out.println("Connected clients (" + clients.size() + "):");
        for (Socket client : clients) {
            System.out.println("Client: " + client.getInetAddress() + ":" + client.getPort());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            PrintWriter out = null;
            try {

                // get the output stream to send data to the client
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // send "Hello world!" to the client
                out.println("Hello world!");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) out.close();
                    if (clientSocket != null) clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clients.remove(clientSocket);
                displayConnectedClients();
            }
        }
    }
}
