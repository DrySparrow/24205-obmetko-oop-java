package model.workers;

import model.objects.*;
import model.storage.Storage;
import model.FactoryModel;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Worker implements Runnable {
    private final Storage<Body> bodyStorage;
    private final Storage<Engine> engineStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Car> carStorage;
    private final int workerId;
    private final FactoryModel model;
    public Worker(int id, Storage<Body> bs, Storage<Engine> es,
                  Storage<Accessory> as, Storage<Car> cs, FactoryModel model) {
        this.workerId = id;
        this.bodyStorage = bs;
        this.engineStorage = es;
        this.accessoryStorage = as;
        this.carStorage = cs;
        this.model = model;
    }

    int getWorkerId() {
        return workerId;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Body body = bodyStorage.get();
                Engine engine = engineStorage.get();

                List<Accessory> carAccessories = new ArrayList<>();
                for (int i = 0; i < accessoriesPerCar; i++) {
                    carAccessories.add(accessoryStorage.get());
                }

                Thread.sleep(3000); // Сборка

                int carId = model.generateCarId();
                Car car = new Car(carId, body, engine, carAccessories);
                carStorage.put(car);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
