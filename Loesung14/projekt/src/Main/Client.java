import java.io.*;
import java.net.*;

public class Client {
    private final String serverAddress;
    private final int serverTcpPort;
    private final int clientUdpPort;

    public Client(String serverAddress, int serverTcpPort, int clientUdpPort) {
        this.serverAddress = serverAddress;
        this.serverTcpPort = serverTcpPort;
        this.clientUdpPort = clientUdpPort;
    }

    public void start() throws IOException {
        registerWithSensor();
        receiveUdpData();
    }

    private void registerWithSensor() throws IOException {
        try (Socket socket = new Socket(serverAddress, serverTcpPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {


            out.println(clientUdpPort);


            String response = in.readLine();
            if (response != null && response.equals("controller")) {
                simulateControllerCommands();
            }
        }
    }

    private void receiveUdpData() {
        try (DatagramSocket udpSocket = new DatagramSocket(clientUdpPort)) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                udpSocket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received data: " + received);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void simulateControllerCommands() {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                sendCommandToSensor("disable_broadcast");
            } catch (InterruptedException | IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }).start();
    }

    private void sendCommandToSensor(String command) throws IOException {
        try (Socket socket = new Socket(serverAddress, serverTcpPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(clientUdpPort + ":" + command);
        }
    }
}