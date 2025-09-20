package nfm.lit.server;

import java.net.*;
import java.io.*;
import java.util.*;

public class LobbyServer {
    private static List<Socket> clients = new ArrayList<>();
    private static Map<Socket, String> playerNames = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(6900);
        System.out.println("Lobby server started on port 6900");
        while (true) {
            Socket client = server.accept();
            clients.add(client);
            new Thread(() -> handleClient(client)).start();
        }
    }

    private static List<String> orderedNames = new ArrayList<>(); // Add this at the top

    private static void handleClient(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("JOIN ")) {
                    String name = line.substring(5).trim();
                    playerNames.put(client, name);
                    if (!orderedNames.contains(name)) orderedNames.add(name); // Maintain join order
                    broadcastPlayers();

                    int idx = orderedNames.indexOf(name);
                    try {
                        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                        out.println("YOURINDEX " + idx);
                    } catch (IOException ignored) {}
                } else {
                    broadcast("MESSAGE " + line);
                    if (line.equals("START")) broadcast("START");
                }
            }
        } catch (IOException e) {
            clients.remove(client);
            String name = playerNames.remove(client);
            if (name != null) orderedNames.remove(name); // Remove from join order list
            broadcastPlayers();
        }
    }

    private static void broadcastPlayers() {
        String players = String.join(",", orderedNames); // Use orderedNames for correct order
        broadcast("PLAYERS " + players);
        broadcast("PLAYERCOUNT " + orderedNames.size());
    }

    private static void broadcast(String msg) {
        for (Socket c : clients) {
            try {
                PrintWriter out = new PrintWriter(c.getOutputStream(), true);
                out.println(msg);
            } catch (IOException ignored) {}
        }
    }
}