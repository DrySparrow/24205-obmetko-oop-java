package model.workers;

import model.objects.Engine;
import model.storage.Storage;

public class EngineSupplier extends Supplier<Engine> {
    public EngineSupplier(Storage<Engine> storage, int delay) {
        super(storage, "Engine", delay);
    }

    @Override
    protected Engine createItem() {
        return new Engine();
    }
}
