package workers;

import model.FactoryModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WorkerPoolTest {

    private WorkerPool workerPool;
    private FactoryModel mockModel;

    @BeforeEach
    void setUp() {
        mockModel = mock(FactoryModel.class);
        // Создаем пул с 2 рабочими потоками
        workerPool = new WorkerPool(2, mockModel);
    }

    @AfterEach
    void tearDown() {
        workerPool.shutdown();
    }

    @Test
    void testAddTaskAndGetActiveWorkerCount() throws InterruptedException {
        // Используем защелку, чтобы подвесить поток внутри пула для замера активности
        CountDownLatch latch = new CountDownLatch(1);

        workerPool.addTask(() -> {
            try {
                latch.await(1, TimeUnit.SECONDS); // Поток зависнет тут
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Небольшая пауза, чтобы поток пула успел подхватить задачу
        Thread.sleep(50);

        assertEquals(1, workerPool.getActiveWorkerCount(), "Один поток должен быть активен");
        latch.countDown(); // Освобождаем поток
    }

    @Test
    void testGetQueueSize() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // Занимаем оба потока в пуле (размер пула = 2)
        workerPool.addTask(() -> { try { latch.await(); } catch (InterruptedException ignored) {} });
        workerPool.addTask(() -> { try { latch.await(); } catch (InterruptedException ignored) {} });

        // Эта задача уже не поместится в свободные потоки и упадет в очередь
        workerPool.addTask(() -> {});

        Thread.sleep(50); // Ждем распределения задач

        assertEquals(1, workerPool.getQueueSize(), "В очереди должна остаться 1 задача");
        latch.countDown(); // Чистим за собой
    }

    @Test
    void testShutdown() throws InterruptedException {
        workerPool.shutdown();

        // Проверяем, что после shutdown новые задачи больше не принимаются или пул останавливается
        // В стандартном ThreadPoolExecutor после shutdown метод execute() кидает RejectedExecutionException
        assertThrows(Exception.class, () -> {
            workerPool.addTask(() -> {});
        }, "После вызова shutdown пул должен отклонять новые задачи");
    }
}