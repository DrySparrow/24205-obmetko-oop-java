package workers;

import model.FactoryModel;
import model.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Supplier<T> implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Supplier.class);

    protected final Storage<T> storage;
    protected final int delay;
    protected final String itemName;
    protected final FactoryModel model;

    public Supplier(Storage<T> storage, String itemName, int delay, FactoryModel model) {
        this.storage = storage;
        this.itemName = itemName;
        this.delay = delay;
        this.model = model;
    }

    protected abstract T createItem();

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                model.updateThreadStatus("Supplier-" + itemName, true);
                Thread.sleep(delay);
                T item = createItem();

                model.updateThreadStatus("Supplier-" + itemName, false);
                storage.put(item);

                logger.info("Produced: {}", itemName);
            }
        } catch (InterruptedException e) {
            logger.warn("Supplier of {} interrupted", itemName);
            Thread.currentThread().interrupt();
        }
    }
}