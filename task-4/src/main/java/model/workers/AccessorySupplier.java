package model.workers;
import model.objects.Accessory;
import model.storage.Storage;

public class AccessorySupplier implements Runnable {
    private final Storage<Accessory> storage;
    private final String supplierName;
    private int itemsProduced = 0;

    public AccessorySupplier(Storage<Accessory> storage, String name) {
        this.storage = storage;
        this.supplierName = name;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Имитация времени производства
                Thread.sleep(4000);

                Accessory item = new Accessory(supplierName, ++itemsProduced);
                storage.put(item); // Склад заблокирует поток, если он полон

                System.out.println("produced accessory #" + supplierName);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
