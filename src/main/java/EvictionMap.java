import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class EvictionMap<K, V> {

    private Timer timer;
    private final long expirationTime;
    private final ConcurrentHashMap<K, TimerTask> timerMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();

    /**
     * Constructor for the situation, when user did not define expiration time time.
     */
    public EvictionMap() {
        expirationTime = Long.MAX_VALUE;
    }

    /**
     * Constructor for the situation, when user define expiration time time.
     * @param maxTime in seconds
     */
    public EvictionMap(long maxTime) {
        if (maxTime < 0) {
            throw new IllegalArgumentException("Value has to be above or equal 0.");
        }
        this.expirationTime = maxTime * 1000;
    }

    /**
     * Put method to store key and value in map. This method controls, if there are any already running
     * timer tasks, that should be canceled, so no needed key would get removed.
     * @param key any
     * @param value any
     */
    public void put(K key, V value) {
        if (map.isEmpty()) {
            timer = new Timer();
        }
        if (timerMap.containsKey(key)) {
            timerMap.get(key).cancel();
            timerMap.remove(key);
        }
        MyTimerTask<K, V> task = new MyTimerTask<>(map, key, timer);
        timerMap.put(key, task);
        timer.schedule(task, this.expirationTime);
        map.put(key, value);
    }

    /**
     * Get value from map.
     * @param k @any
     * @return value from map
     */
    public V get(K k) {
        return map.getOrDefault(k, null);
    }
}
