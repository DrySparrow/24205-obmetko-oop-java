package workers;

import model.FactoryModel;
import model.objects.Body;
import model.storage.Storage;

public class BodySupplier extends Supplier<Body> {
    public BodySupplier(Storage<Body> storage, int delay, FactoryModel model) {
        super(storage, "Body", delay, model);
    }

    @Override
    protected Body createItem() {
        return new Body(); // Просто создаем объект
    }
}
