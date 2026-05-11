package model.workers;

import model.objects.Engine;
import model.storage.Storage;

public class EngineSupplier implements Runnable {
    private final Storage<Engine> engineStorage;
    public EngineSupplier(Storage<Engine> engineStorage) {
        this.engineStorage = engineStorage;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Имитация времени производства
                Thread.sleep(4000);

                Engine item = new Engine();
                engineStorage.put(item); // Склад заблокирует поток, если он полон

                System.out.println("produced engine");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
