package workers;

import model.FactoryModel;
import model.objects.Engine;
import model.storage.Storage;

public class EngineSupplier extends Supplier<Engine> {
    public EngineSupplier(Storage<Engine> storage, int delay, FactoryModel model) {
        super(storage, "Engine", delay, model);
    }

    @Override
    protected Engine createItem() {
        return new Engine();
    }
}
