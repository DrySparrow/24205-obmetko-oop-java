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

    public synchronized void put(T item) throws InterruptedException {
        while (data.size() >= capacity) {
            wait();
        }
        data.add(item);
        notifyAll();

        notifyObservers();
    }

    public synchronized T get() throws InterruptedException {
        while (data.isEmpty()) {
            wait(); // Ждем, пока что-то появится
        }
        T item = data.remove();
        notifyAll(); // Будим тех, кто ждал места (put)
        notifyObservers();
        return item;
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

    public void addObserver(StorageObserver observer) { observers.add(observer); }
}