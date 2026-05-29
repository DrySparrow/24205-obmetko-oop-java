package workers;

import model.FactoryModel;
import model.objects.Car;
import model.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Dealer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Dealer.class);

    private final int dealerId;
    private final int assemblyDelay;
    private final Storage<Car> carStorage;
    private final FactoryModel model;

    public Dealer(int id, Storage<Car> carStorage, int assemblyDelay, FactoryModel model) {
        this.dealerId = id;
        this.carStorage = carStorage;
        this.assemblyDelay = assemblyDelay;
        this.model = model;
    }

    int getDealerId() {
        return dealerId;
    }

    @Override
    public void run() {
        logger.info("Dealer-{} started", dealerId);

        try {
            while (!Thread.currentThread().isInterrupted()) {
                model.updateThreadStatus("Dealer-" + dealerId, false);
                carStorage.get();
                logger.info("Dealer-{} sold a car", dealerId);

                model.updateThreadStatus("Dealer-" + dealerId, true);
                Thread.sleep(assemblyDelay);
            }
        } catch (InterruptedException e) {
            logger.warn("Dealer-{} was interrupted", dealerId);
            Thread.currentThread().interrupt();
        }
    }
}
