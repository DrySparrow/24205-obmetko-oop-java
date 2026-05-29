package model.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    private Storage<String> storage;
    private TestObserver testObserver;

    @BeforeEach
    void setUp() {
        storage = new Storage<>(3);
        testObserver = new TestObserver();
        storage.addObserver(testObserver);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(3, storage.getCapacity());
        assertEquals(0, storage.getCurrentSize());
    }

    @Test
    void testPutAndGet() throws InterruptedException {
        storage.put("Item1");
        assertEquals(1, storage.getCurrentSize());

        storage.put("Item2");
        assertEquals(2, storage.getCurrentSize());

        String item = storage.get();
        assertEquals("Item1", item);
        assertEquals(1, storage.getCurrentSize());

        String item2 = storage.get();
        assertEquals("Item2", item2);
        assertEquals(0, storage.getCurrentSize());
    }

    @Test
    void testPutWhenFull() throws InterruptedException {
        storage.put("Item1");
        storage.put("Item2");
        storage.put("Item3");

        assertEquals(3, storage.getCurrentSize());

        Thread putThread = new Thread(() -> {
            try {
                storage.put("Item4");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        putThread.start();
        Thread.sleep(100);
        assertEquals(3, storage.getCurrentSize());

        storage.get();
        Thread.sleep(100);
        assertEquals(3, storage.getCurrentSize());

        putThread.join(1000);
        assertFalse(putThread.isAlive());
    }

    @Test
    void testGetWhenEmpty() throws InterruptedException {
        Thread getThread = new Thread(() -> {
            try {
                storage.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        getThread.start();
        Thread.sleep(100);
        assertTrue(getThread.isAlive());

        storage.put("Item1");
        Thread.sleep(100);
        assertFalse(getThread.isAlive());
        assertEquals(0, storage.getCurrentSize());
    }

    @Test
    void testObserverNotificationOnPut() throws InterruptedException {
        storage.put("Item1");
        assertEquals(1, testObserver.getLastSize());

        storage.put("Item2");
        assertEquals(2, testObserver.getLastSize());
    }

    @Test
    void testObserverNotificationOnGet() throws InterruptedException {
        storage.put("Item1");
        storage.put("Item2");

        testObserver.reset();
        storage.get();
        assertEquals(1, testObserver.getLastSize());

        storage.get();
        assertEquals(0, testObserver.getLastSize());
    }

    @Test
    void testMultipleObservers() {
        TestObserver observer2 = new TestObserver();
        storage.addObserver(observer2);

        try {
            storage.put("Item1");
        } catch (InterruptedException e) {
            fail("Unexpected interruption");
        }

        assertEquals(1, testObserver.getLastSize());
        assertEquals(1, observer2.getLastSize());
    }

    @Test
    void testConcurrentPutAndGet() throws InterruptedException {
        Thread producer1 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    storage.put("P1-" + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread producer2 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    storage.put("P2-" + i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    storage.get();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer1.start();
        producer2.start();
        consumer.start();

        producer1.join();
        producer2.join();
        consumer.join();

        assertEquals(0, storage.getCurrentSize());
    }

    @Test
    void testAddObserver() {
        TestObserver newObserver = new TestObserver();
        storage.addObserver(newObserver);

        try {
            storage.put("Test");
        } catch (InterruptedException e) {
            fail("Unexpected interruption");
        }

        assertEquals(1, newObserver.getLastSize());
        assertEquals(1, testObserver.getLastSize());
    }

    // Вспомогательный класс для тестирования Observer
    private static class TestObserver implements StorageObserver {
        private int lastSize = -1;

        @Override
        public void onStorageSizeChanged(int currentSize) {
            this.lastSize = currentSize;
        }

        public int getLastSize() {
            return lastSize;
        }

        public void reset() {
            lastSize = -1;
        }
    }
}