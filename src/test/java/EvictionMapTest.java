import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class EvictionMapTest {
    EvictionMap<String, Integer> testingMap;
    EvictionMap<Integer, String> testingMapWithAnotherValues;

    @BeforeEach
    void setUp() {
        testingMap = new EvictionMap<>(10);
        testingMapWithAnotherValues = new EvictionMap<>(5);
    }

    @Test
    void put() throws InterruptedException {
        testingMap.put("K1", 1);
        testingMap.put("K2", 2);
        assertEquals(1, testingMap.get("K1"));
        assertEquals(2, testingMap.get("K2"));
        Thread.sleep(6000);
        assertEquals(1, testingMap.get("K1"));
        assertEquals(2, testingMap.get("K2"));
        testingMap.put("K3", 3);
        testingMap.put("K4", 4);
        assertEquals(1, testingMap.get("K1"));
        assertEquals(2, testingMap.get("K2"));
        assertEquals(3, testingMap.get("K3"));
        assertEquals(4, testingMap.get("K4"));
        Thread.sleep(6000);
        assertNull(testingMap.get("K1"));
        assertNull(testingMap.get("K2"));
        assertEquals(3, testingMap.get("K3"));
        assertEquals(4, testingMap.get("K4"));
        Thread.sleep(4000);
        assertNull(testingMap.get("K3"));
        assertNull(testingMap.get("K4"));
        testingMap.put("K3", 3);
        testingMap.put("K3", 4);
        assertEquals(4, testingMap.get("K3"));
    }

    @Test
    void get() {
        testingMap.put("K1", 1);
        testingMap.put("K2", 2);
        testingMap.put("K3", 3);
        assertEquals(1, testingMap.get("K1"));
        assertEquals(2, testingMap.get("K2"));
        assertEquals(3, testingMap.get("K3"));
        assertNull(testingMap.get("K4"));
    }

    @Test
    void testingWithUpdatedValue() throws InterruptedException {
        testingMapWithAnotherValues.put(1, "V1");
        testingMapWithAnotherValues.put(2, "V2");
        Thread.sleep(4000);
        assertEquals("V1", testingMapWithAnotherValues.get(1));
        testingMapWithAnotherValues.put(1, "V1");
        Thread.sleep(2000);
        assertEquals("V1", testingMapWithAnotherValues.get(1));
        testingMapWithAnotherValues.put(1, "V2");
        Thread.sleep(4000);
        assertEquals("V2", testingMapWithAnotherValues.get(1));
        Thread.sleep(1500);
        assertNull(testingMapWithAnotherValues.get(1));
    }

    @Test
    void exceptionTest() {
        boolean isException = false;
        try {
            EvictionMap<Object, Object> exceptionMap = new EvictionMap<>(-100);
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertTrue(isException);
    }

    @Test
    void testingConstructorWithoutMaxTime() {
        boolean isException = false;
        try {
            EvictionMap<Object, Object> exceptionMap = new EvictionMap<>();
        } catch (IllegalArgumentException e) {
            isException = true;
        }
        assertFalse(isException);
    }

    @Test
    void manyPutAndGet() {
        EvictionMap<Integer, Integer> map = new EvictionMap<>(10L);
        for (int i = 0; i < 1000000; i++) {
            map.put(i, i + 1);
        }
        for (int i = 0; i < 1000000; i++) {
            assertEquals(i + 1, map.get(i));
        }
    }
}