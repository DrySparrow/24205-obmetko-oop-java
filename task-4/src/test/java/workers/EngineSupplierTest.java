package workers;

import model.FactoryModel;
import model.objects.Engine;
import model.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class EngineSupplierTest {

    @Test
    void testCreateItemReturnsNewEngine() {
        Storage<Engine> mockStorage = mock(Storage.class);
        FactoryModel mockModel = mock(FactoryModel.class);

        EngineSupplier supplier = new EngineSupplier(mockStorage, 10, mockModel);
        Engine engine = supplier.createItem();

        assertNotNull(engine, "Метод должен возвращать созданный объект Двигателя");
    }
}