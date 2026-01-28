import java.util.Random;

public class TactileSensor implements Runnable {
    private double currentPressure;
    private long timestamp;
    private final Monitor monitor;

    public TactileSensor(Monitor monitor) {
        this.monitor = monitor;
        monitor.addTactileSensor(this); // Sensor beim Monitor registrieren
    }

    public double getCurrentPressure() {
        return currentPressure;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                currentPressure = random.nextDouble() * 10.0; // Wert zwischen 0 und 10.0
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

