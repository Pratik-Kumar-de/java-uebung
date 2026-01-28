import java.io.*;
import java.net.*;
import java.util.*;

public class Sensor {
    private final List<String> clients = new ArrayList<>();
    private volatile boolean broadcasting = true;
    private String controller;

    public void start(int tcpPort, int udpPort) throws IOException {
        new Thread(() -> tcpServer(tcpPort)).start();
        new Thread(() -> udpBroadcast(udpPort)).start();
    }

    private void tcpServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClientRegistration(socket)).start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleClientRegistration(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String clientData = in.readLine(); // Format: "udpPort" oder "udpPort:Befehl"

            if (clientData.contains(":")) {

                String[] parts = clientData.split(":");
                String udpPort = parts[0];
                String command = parts[1];

                if (udpPort.equals(controller)) {
                    handleControllerCommand(command);
                } else {
                    System.out.println("Error: Only the controller can send commands.");
                }
            } else {

                clients.add(clientData);
                if (controller == null) {
                    controller = clientData;
                    out.println("controller");
                } else {
                    out.println("registered");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void udpBroadcast(int port) {
        try (DatagramSocket udpSocket = new DatagramSocket()) {
            InetAddress address = InetAddress.getLocalHost();

            while (broadcasting) {
                double data = Math.sin(System.currentTimeMillis() / 1000.0);
                String message = Double.toString(data);

                for (String client : clients) {
                    DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, Integer.parseInt(client));
                    udpSocket.send(packet);
                }

                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleControllerCommand(String command) {
        switch (command) {
            case "disable_broadcast":
                broadcasting = false;
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }
}