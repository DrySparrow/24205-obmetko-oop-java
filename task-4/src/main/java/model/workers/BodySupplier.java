package model.workers;

import model.objects.Body;
import model.storage.Storage;

public class BodySupplier extends Supplier<Body> {
    public BodySupplier(Storage<Body> storage, int delay) {
        super(storage, "Body", delay);
    }

    @Override
    protected Body createItem() {
        return new Body(); // Просто создаем объект
    }
}
