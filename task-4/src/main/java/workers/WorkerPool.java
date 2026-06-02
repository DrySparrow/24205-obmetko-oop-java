package workers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WorkerPool {
    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private final List<WorkerThread> threads = new ArrayList<>();
    private boolean isShutdown = false;

    // принимает количество рабочих
    public WorkerPool(int count) {
        for (int i = 0; i < count; i++) {
            WorkerThread worker = new WorkerThread("Worker-" + i);
            threads.add(worker);
            worker.start();
        }
    }

    // Добавление задачи в очередь
    public synchronized void addTask(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("Пул потоков остановлен! Добавление задач невозможно.");
        }
        tasks.addLast(task);
        // Будим один из потоков, уснувших в методе getTask() на вызове wait()
        notifyAll();
    }

    // Получение размера очереди
    public synchronized int getQueueSize() {
        return tasks.size();
    }

    // Подсчет активных потоков, занятых сборкой в данный момент
    public int getActiveWorkerCount() {
        int count = 0;
        for (WorkerThread t : threads) {
            if (t.isWorkerBusy()) {
                count++;
            }
        }
        return count;
    }

    public synchronized void shutdown() {
        isShutdown = true;
        // Будим всех, кто завис в getTask() в ожидании задач, чтобы они увидели флаг остановки и вышли
        notifyAll();
        // Посылаем сигнал остановки каждому конкретному рабочему
        for (WorkerThread t : threads) {
            t.mstop();
        }
    }

    // Внутренний метод извлечения задачи рабочим потоком
    private synchronized Runnable getTask() throws InterruptedException {
        // Пока очередь пуста и пул работает — поток спит
        while (tasks.isEmpty() && !isShutdown) {
            wait();
        }
        // Если пул закрывается и задач больше нет — возвращаем null, сигнализируя потоку завершить работу
        if (isShutdown && tasks.isEmpty()) {
            return null;
        }
        return tasks.removeFirst();
    }

    private class WorkerThread extends Thread {
        private enum WorkerState { RUNNING, IDLE, STOPPED }

        private WorkerState state = WorkerState.IDLE;
        private boolean isBusy = false;

        public WorkerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                // Проверяем, не приказано ли нам остановиться
                synchronized (this) {
                    if (state == WorkerState.STOPPED) {
                        break;
                    }
                    state = WorkerState.IDLE;
                }

                Runnable task;
                try {
                    // Пытаемся взять задачу. Если очередь пуста, этот поток засыпает внутри getTask()
                    task = getTask();
                } catch (InterruptedException e) {
                    // Если поток прервали во время ожидания — выходим из цикла
                    break;
                }

                if (task == null) {
                    break;
                }

                // Выполнение задачи
                try {
                    synchronized (this) {
                        state = WorkerState.RUNNING;
                        isBusy = true;
                    }
                    task.run();
                } catch (Throwable t) {
                    System.err.println("Ошибка при выполнении задачи в " + getName() + ": " + t.getMessage());
                } finally {
                    synchronized (this) {
                        isBusy = false;
                        state = WorkerState.IDLE;
                    }
                }
            }

            // Финальная стадия жизненного цикла
            synchronized (this) {
                state = WorkerState.STOPPED;
            }
            System.out.println(getName() + " успешно завершил работу и остановлен.");
        }

        // метод остановки
        public synchronized void mstop() {
            state = WorkerState.STOPPED;
            this.interrupt(); // Прерываем поток, если он спал внутри wait() или Thread.sleep()
        }

        // Проверка занятости потока для метода getActiveWorkerCount()
        public synchronized boolean isWorkerBusy() {
            return isBusy;
        }
    }
}