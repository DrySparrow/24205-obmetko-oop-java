package model.workers;

import model.objects.*;
import model.storage.Storage;
import model.FactoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Worker implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Worker.class);

    private final Storage<Body> bodyStorage;
    private final Storage<Engine> engineStorage;
    private final List<Storage<Accessory>> accessoryStorages;
    private final Storage<Car> carStorage;
    private final int workerId;
    private final int assemblyDelay;
    private final FactoryModel model;
    public Worker(int id, Storage<Body> bs, Storage<Engine> es,
                  List<Storage<Accessory>> as, Storage<Car> cs,
                  int delay, FactoryModel model) { // Добавили delay
        this.workerId = id;
        this.bodyStorage = bs;
        this.engineStorage = es;
        this.accessoryStorages = as;
        this.carStorage = cs;
        this.assemblyDelay = delay;
        this.model = model;
    }

    int getWorkerId() {
        return workerId;
    }

    @Override
    public void run() {
        logger.info("Worker-{} started", workerId);

        try {
            while (!Thread.currentThread().isInterrupted()) {
                Body body = bodyStorage.get();
                Engine engine = engineStorage.get();

                List<Accessory> kit = new ArrayList<>();
                for (Storage<Accessory> s : accessoryStorages) {
                    kit.add(s.get()); // Ждем, пока на каждом складе появится деталь
                }

                Thread.sleep(assemblyDelay); // Сборка

                Car car = new Car(model.generateCarId(), body, engine, kit);
                logger.info("Worker-{} created Car ID: {} (Accessories: {})", workerId, car.getId(), kit.size());
                carStorage.put(car);
            }
        } catch (InterruptedException e) {
            logger.warn("Worker-{} was interrupted", workerId);
            Thread.currentThread().interrupt();
        }
    }
}
