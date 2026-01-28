import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        int sensorTcpPort = 12345;
        int sensorUdpPort = 12346;

        Sensor sensor = new Sensor();
        sensor.start(sensorTcpPort, sensorUdpPort);

        new Thread(() -> {
            try {
                Client controller = new Client("localhost", sensorTcpPort, 5000);
                controller.start();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }).start();

        new Thread(() -> {
            try {
                Client client1 = new Client("localhost", sensorTcpPort, 5001);
                client1.start();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }).start();

        new Thread(() -> {
            try {
                Client client2 = new Client("localhost", sensorTcpPort, 5002);
                client2.start();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }).start();
    }
}
