import java.util.ArrayList;
import java.util.List;

public class Monitor {
    private final List<ForceSensor> forceSensors = new ArrayList<>();
    private final List<TactileSensor> tactileSensors = new ArrayList<>();

    private static final double FORCE_DROP_THRESHOLD = 10.0;
    private static final double PRESSURE_DROP_THRESHOLD = 5.0;
    private static final long TIME_WINDOW_MS = 1000;

    public synchronized void addForceSensor(ForceSensor sensor) {
        forceSensors.add(sensor);
    }

    public synchronized void addTactileSensor(TactileSensor sensor) {
        tactileSensors.add(sensor);
    }

    public synchronized void checkForDrop() {
        long currentTime = System.currentTimeMillis();

        boolean allForceSensorsBelowThreshold = forceSensors.stream()
                .allMatch(sensor -> sensor.getCurrentForce() < FORCE_DROP_THRESHOLD
                        && (currentTime - sensor.getTimestamp() <= TIME_WINDOW_MS));

        boolean allTactileSensorsBelowThreshold = tactileSensors.stream()
                .allMatch(sensor -> sensor.getCurrentPressure() < PRESSURE_DROP_THRESHOLD
                        && (currentTime - sensor.getTimestamp() <= TIME_WINDOW_MS));

        if (allForceSensorsBelowThreshold && allTactileSensorsBelowThreshold) {
            System.out.println("Warning: Object dropped!");
        }
    }
}

