package model.workers;

import model.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Supplier<T> implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Supplier.class);

    protected final Storage<T> storage;
    protected final int delay;
    protected final String itemName;

    public Supplier(Storage<T> storage, String itemName, int delay) {
        this.storage = storage;
        this.itemName = itemName;
        this.delay = delay;
    }

    protected abstract T createItem();

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(delay);

                T item = createItem();
                storage.put(item);

                logger.info("Produced: {}", itemName);
            }
        } catch (InterruptedException e) {
            logger.warn("Supplier of {} interrupted", itemName);
            Thread.currentThread().interrupt();
        }
    }
}