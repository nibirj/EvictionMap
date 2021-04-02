import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

class MyTimerTask<K, V> extends TimerTask {
    private final ConcurrentHashMap<K, V> map;
    private final K key;
    private final Timer timer;

    /**
     * Constructor
     * @param map ConcurrentHashMap
     * @param key any
     * @param timer Timer
     */
    MyTimerTask(ConcurrentHashMap<K, V> map, K key, Timer timer) {
        this.map = map;
        this.key = key;
        this.timer = timer;
    }

    /**
     * This method removes key after a certain time, and cancels the timer, if no keys are in the map, to
     * not use unneeded recourses
     */
    @Override
    public void run() {
        map.remove(key);
        if (map.isEmpty()) {
            timer.cancel();
        }
    }
}
