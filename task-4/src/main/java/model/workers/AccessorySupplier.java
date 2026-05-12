package model.workers;

import model.objects.Accessory;
import model.storage.Storage;

public class AccessorySupplier extends Supplier<Accessory> {
    private final String type;
    private int counter = 0;

    public AccessorySupplier(Storage<Accessory> storage, String type, int delay) {
        super(storage, "Accessory-" + type, delay);
        this.type = type;
    }

    @Override
    protected Accessory createItem() {
        return new Accessory(type, ++counter);
    }
}