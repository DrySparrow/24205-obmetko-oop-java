package model;

import model.storage.Storage;
import model.objects.*;
import model.util.Config;
import model.workers.*;

import java.util.concurrent.atomic.AtomicInteger;

public class FactoryModel {
    private final AtomicInteger carIdCounter = new AtomicInteger(0);
    private final Storage<Car> carStorage;

    public FactoryModel(Config config) {
        Storage<Body> bodyStorage = new Storage<>(config.getBodyCapacity());
        Storage<Engine> engineStorage = new Storage<>(config.getEngineCapacity());
        Storage<Accessory> accessoryStorage = new Storage<>(config.getAccessoryCapacity());
        carStorage = new Storage<>(config.getCarCapacity());

        for (int i = 0; i < config.getBodySuppliersCount(); i++) {
            new Thread(new BodySupplier(bodyStorage)).start();
        }

        for (int i = 0; i < config.getEngineSuppliersCount(); i++) {
            new Thread(new EngineSupplier(engineStorage)).start();
        }

        for (int i = 0; i < config.getAccessorySuppliersCount(); i++) {
            new Thread(new AccessorySupplier(accessoryStorage, "Supplier_" + i)).start();
        }

        for (int i = 0; i < config.getWorkersCount(); i++) {
            new Thread(new Worker(i, bodyStorage, engineStorage, accessoryStorage, carStorage, this)).start();
        }

        for (int i = 0; i < config.getDealersCount(); i++) {
            new Thread(new Dealer(i, carStorage)).start();
        }
    }

    public int generateCarId() {
        return carIdCounter.incrementAndGet(); // Безопасно для многих потоков
    }

    public Storage<Car> getCarStorage() { return carStorage; }
}