package model.workers;

import model.objects.Body;
import model.storage.Storage;

public class BodySupplier implements Runnable {
    private final Storage<Body> bodyStorage;
    public BodySupplier(Storage<Body> bodyStorage) {
        this.bodyStorage = bodyStorage;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Имитация времени производства
                Thread.sleep(4000);

                Body item = new Body();
                bodyStorage.put(item); // Склад заблокирует поток, если он полон

                System.out.println("produced body");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
