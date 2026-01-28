import java.util.Random;

public class ForceSensor implements Runnable {
    private double currentForce;
    private long timestamp;
    private final Monitor monitor;

    public ForceSensor(Monitor monitor) {
        this.monitor = monitor;
        monitor.addForceSensor(this); // Sensor beim Monitor registrieren
    }

    public double getCurrentForce() {
        return currentForce;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                currentForce = random.nextDouble() * 20.0; // Wert zwischen 0 und 20.0
                timestamp = System.currentTimeMillis();
                monitor.checkForDrop(); // Überprüfung durch den Monitor
                Thread.sleep(500); // Abfragerate simulieren
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

