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
    private final WorkerPool workerPool;

    public FactoryModel(Config config) {
        this.bodyStorage = new Storage<>(config.getBodyCapacity());
        this.engineStorage = new Storage<>(config.getEngineCapacity());
        this.carStorage = new Storage<>(config.getCarCapacity());
        this.workerPool = new WorkerPool(config.getWorkersCount(), this);

        for (int i = 0; i < config.getBodySuppliersCount(); i++) {
            new Thread(new BodySupplier(bodyStorage, config.getBodySuppliersDelay(), this)).start();
        }

        for (int i = 0; i < config.getEngineSuppliersCount(); i++) {
            new Thread(new EngineSupplier(engineStorage, config.getEngineSuppliersDelay(), this)).start();
        }

        for (int i = 0; i < config.getAccessorySuppliersCount(); i++) {
            Storage<Accessory> s = new Storage<>(config.getAccessoryCapacity());
            accessoryStorages.add(s);
            new Thread(new AccessorySupplier(s, "Type_" + i, config.getAccessorySuppliersDelay(), this)).start();
        }

        for (int i = 0; i < config.getDealersCount(); i++) {
            new Thread(new Dealer(i, carStorage, config.getDealersDelay(), this)).start();
        }
    }

    private final List<StatusListener> statusListeners = new ArrayList<>();

    public void updateThreadStatus(String name, boolean isBusy) {
        for (StatusListener listener : statusListeners) {
            listener.onStatusUpdate(name, isBusy);
        }
    }

    public void setStatusListener(StatusListener listener) {
        this.statusListeners.add(listener);
    }

    public void sendTaskToWorkers(Config config) {
        workerPool.addTask(() -> {
            try {
                String name = Thread.currentThread().getName();
                updateThreadStatus(name, false);

                Body body = bodyStorage.get();
                Engine engine = engineStorage.get();
                List<Accessory> kit = new ArrayList<>();
                for (Storage<Accessory> s : accessoryStorages) {
                    kit.add(s.get());
                }
                updateThreadStatus(name, true);
                Thread.sleep(config.getWorkersDelay());

                Car car = new Car(generateCarId(), body, engine, kit);
                updateThreadStatus(name, false);
                carStorage.put(car);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public int generateCarId() {
        return carIdCounter.incrementAndGet();
    }

    public Storage<Body> getBodyStorage() { return bodyStorage; }
    public Storage<Engine> getEngineStorage() { return engineStorage; }
    public List<Storage<Accessory>> getAccessoryStorages() { return accessoryStorages; }
    public Storage<Car> getCarStorage() { return carStorage; }
    public WorkerPool getWorkerPool() { return workerPool; }
}