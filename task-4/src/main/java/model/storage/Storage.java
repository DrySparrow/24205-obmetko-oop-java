package model.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Storage<T> {
    private final BlockingQueue<T> data;
    private final int capacity;
    private final List<StorageObserver> observers;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.data = new LinkedBlockingQueue<>(capacity);
        this.observers = new ArrayList<>();
    }

    public void put(T item) throws InterruptedException {
        // put() сам подождет (заблокирует поток), если очередь полна
        data.put(item);
        notifyObservers();
    }

    public T get() throws InterruptedException {
        // take() сам подождет, если склад пуст
        T taken = data.take();
        notifyObservers();
        return taken;
    }

    public int getCurrentSize() {
        return data.size();
    }

    public int getCapacity() {
        return capacity;
    }

    private void notifyObservers() {
        int currentSize = data.size();
        for (StorageObserver observer : observers) {
            observer.onStorageSizeChanged(currentSize);
        }
    }

    public void addObserver(StorageObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }
}