package model.workers;

import model.objects.Car;
import model.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Dealer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Dealer.class);

    private final int dealerId;
    private final int assemblyDelay;
    private final Storage<Car> carStorage;

    public Dealer(int id, Storage<Car> carStorage, int assemblyDelay) {
        this.dealerId = id;
        this.carStorage = carStorage;
        this.assemblyDelay = assemblyDelay;
    }

    int getDealerId() {
        return dealerId;
    }

    @Override
    public void run() {
        logger.info("Dealer-{} started", dealerId);

        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(assemblyDelay);

                logger.info("Dealer-{} sold a car", dealerId);
                carStorage.get();
            }
        } catch (InterruptedException e) {
            logger.warn("Dealer-{} was interrupted", dealerId);
            Thread.currentThread().interrupt();
        }
    }
}
