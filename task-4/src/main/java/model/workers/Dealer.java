package model.workers;

import model.objects.Car;
import model.storage.Storage;


public class Dealer implements Runnable {
    private final int dealerId;
    private final Storage<Car> carStorage;

    public Dealer(int id, Storage<Car> carStorage) {
        this.dealerId = id;
        this.carStorage = carStorage;
    }

    int getDealerId() {
        return dealerId;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(5000);

                System.out.println("car sold by dealer №: " + getDealerId());
                carStorage.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
