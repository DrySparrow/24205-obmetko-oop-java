package model.workers;

import model.FactoryModel;

import java.util.LinkedList;
import java.util.List;

public class WorkerPool {
    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private final List<Thread> threads = new java.util.ArrayList<>();
    private final List<PoolObserver> observers = new java.util.ArrayList<>();
    private final FactoryModel model;

    public interface PoolObserver { void onQueueSizeChanged(int size); }

    public WorkerPool(int count, FactoryModel model) {
        this.model = model;
        for (int i = 0; i < count; i++) {
            Thread t = new Thread(() -> {
                try {
                    String name = Thread.currentThread().getName();
                    while (!Thread.currentThread().isInterrupted()) {
                        model.updateThreadStatus(name, false);
                        Runnable task = getTask();
                        model.updateThreadStatus(name, true);
                        task.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Worker-" + i);
            threads.add(t);
            t.start();
        }
    }

    public synchronized void addTask(Runnable task) {
        tasks.addLast(task);
        notifyAll(); // Будим свободного рабочего
        notifyObservers();
    }

    private synchronized Runnable getTask() throws InterruptedException {
        while (tasks.isEmpty()) wait();
        Runnable task = tasks.removeFirst();
        notifyObservers();
        return task;
    }

    public void addObserver(PoolObserver o) { observers.add(o); }
    private void notifyObservers() {
        int s = tasks.size();
        for (PoolObserver o : observers) o.onQueueSizeChanged(s);
    }
}