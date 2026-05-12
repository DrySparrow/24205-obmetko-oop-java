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
    private final List<Storage<Accessory>> accessoryStorages;
    private final Storage<Car> carStorage;
    private final int workerId;
    private final FactoryModel model;
    public Worker(int id, Storage<Body> bs, Storage<Engine> es,
                  List<Storage<Accessory>> as, Storage<Car> cs, FactoryModel model) {
        this.workerId = id;
        this.bodyStorage = bs;
        this.engineStorage = es;
        this.accessoryStorages = as;
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

                List<Accessory> kit = new ArrayList<>();
                for (Storage<Accessory> s : accessoryStorages) {
                    kit.add(s.get()); // Ждем, пока на каждом складе появится деталь
                }

                Thread.sleep(3000); // Сборка

                Car car = new Car(model.generateCarId(), body, engine, kit);
                System.out.println("worker <" + workerId + "> created a car № " + car.getId());
                carStorage.put(car);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
