package workers;

import model.FactoryModel;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerPool {
    private final ThreadPoolExecutor executor;

    public WorkerPool(int count, FactoryModel factoryModel) {
        // Создаем кастомную фабрику, чтобы у потоков были правильные имена для UI
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Worker-" + counter.getAndIncrement());
            }
        };

        this.executor = new ThreadPoolExecutor(
                count,
                count,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                threadFactory // Передаем нашу фабрику имен сюда
        );
    }

    public void addTask(Runnable task) {
        executor.execute(task);
    }

    public int getQueueSize() {
        return executor.getQueue().size();
    }

    public int getActiveWorkerCount() {
        return executor.getActiveCount();
    }

    public void shutdown() {
        executor.shutdown();
    }
}