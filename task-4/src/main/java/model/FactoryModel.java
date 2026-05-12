package model;

import model.storage.Storage;
import model.objects.*;
import model.util.Config;
import model.workers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FactoryModel {
    private final AtomicInteger carIdCounter = new AtomicInteger(0);
    private final Storage<Body> bodyStorage;
    private final Storage<Engine> engineStorage;
    private final Storage<Car> carStorage;
    private final List<Storage<Accessory>> accessoryStorages = new ArrayList<>();

    public FactoryModel(Config config) {
        this.bodyStorage = new Storage<>(config.getBodyCapacity());
        this.engineStorage = new Storage<>(config.getEngineCapacity());
        this.carStorage = new Storage<>(config.getCarCapacity());

        for (int i = 0; i < config.getBodySuppliersCount(); i++) {
            new Thread(new BodySupplier(bodyStorage, config.getBodySuppliersDelay())).start();
        }

        for (int i = 0; i < config.getEngineSuppliersCount(); i++) {
            new Thread(new EngineSupplier(engineStorage, config.getEngineSuppliersDelay())).start();
        }

        for (int i = 0; i < config.getAccessorySuppliersCount(); i++) {
            // У каждого типа аксессуаров свой склад
            Storage<Accessory> s = new Storage<>(config.getAccessoryCapacity());
            accessoryStorages.add(s);

            // Запускаем поставщика для этого конкретного склада
            new Thread(new AccessorySupplier(s, "Type_" + i, config.getAccessorySuppliersDelay())).start();
        }

        for (int i = 0; i < config.getWorkersCount(); i++) {
            Worker worker = new Worker(i, bodyStorage, engineStorage, accessoryStorages, carStorage, config.getWorkersDelay(), this);
            new Thread(worker).start();
        }

        for (int i = 0; i < config.getDealersCount(); i++) {
            new Thread(new Dealer(i, carStorage, config.getDealersDelay())).start();
        }
    }

    public int generateCarId() {
        return carIdCounter.incrementAndGet(); // Безопасно для многих потоков
    }

    public Storage<Car> getCarStorage() { return carStorage; }
}